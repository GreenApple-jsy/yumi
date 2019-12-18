package com.example.yumi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class tutorManageStudent extends AppCompatActivity {


    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    String mJsonString;
    ArrayList<HashMap<String, String>> mArrayList;
    private static final String TAG_SID = "s_id";
    private static final String TAG_TID = "t_id";
    private static final String TAG_NICK = "nickname";
    private static final String TAG_SCH = "school_type";
    private static final String TAG_GD = "grade";
    String table_adr  = "getMatching";
    TextView mTextViewResult;
    Switch aSwitch;
    phpConnect task;
    String arr_sid[]; // s_id 저장 배열
    String tid ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_student);
        mlistView = (ListView)findViewById(R.id.listView_std_list) ;
        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        tid = pref.getString("id", "default");
        task = new phpConnect();
        task.execute();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreStd(position);
            }
        });
        
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent intent = new Intent(getApplicationContext(),TutorQuestionlist.class);  //메인 화면으로 돌아오기
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onBackPressed() { //super.onBackPressed();
    }// 뒤로 가기 막기

    void getMoreStd(int position) {
        final int index = position;
        new AlertDialog.Builder(tutorManageStudent.this)
                .setTitle("학생 정보창" )
                .setMessage("\n학생 정보 : " + arr_sid[position])
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .setNeutralButton("대화하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),ChattingActivity.class);
                        intent.putExtra("oppositeID",arr_sid[index]); //대화할 상대 학생 아이디 전송
                        startActivity(intent);
                    }
                })
                .show();
    }

    class phpConnect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String link = "http://1.234.38.211/"+table_adr+".php?id="+tid;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();
                return sb.toString();

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText("error occurred");
            }
            else {

                mJsonString = result;
                showResult();
            }
            //txtview.setText(result);
        }
    }

    private void showResult(){

        mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            arr_sid = new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String st_id = item.getString(TAG_SID);
                String tt_id = item.getString(TAG_TID);
                String stdNick = item.getString(TAG_NICK);
                String schType = item.getString(TAG_SCH);
                String grade = item.getString(TAG_GD);

                HashMap<String,String> hashMap = new HashMap<>();
                arr_sid[i]=st_id;

                hashMap.put(TAG_NICK , stdNick+" 학생");
                hashMap.put(TAG_SCH, schType);
                hashMap.put(TAG_GD , grade);


                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    tutorManageStudent.this, mArrayList, R.layout.mng_std_list,
                    new String[]{TAG_NICK,TAG_SCH, TAG_GD},
                    new int[]{R.id.listNick, R.id.listSchool, R.id.listGrade}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_NICK , "아직 매칭된 학생이 없습니다.");
            hashMap.put(TAG_SCH, "");
            hashMap.put(TAG_GD , "");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    tutorManageStudent.this, mArrayList, R.layout.mng_std_list,
                    new String[]{TAG_NICK,TAG_SCH, TAG_GD},
                    new int[]{R.id.listNick, R.id.listSchool, R.id.listGrade}
            );


            mlistView.setAdapter(adapter);
            Log.d(TAG, "showResult : ", e);
        }

    }
}
