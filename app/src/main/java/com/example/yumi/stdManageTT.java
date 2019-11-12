package com.example.yumi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class stdManageTT extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    String mJsonString;
    ArrayList<HashMap<String, String>> mArrayList;
    private static final String TAG_SID = "s_id";
    private static final String TAG_TID = "t_id";
    private static final String TAG_NICK = "nickname";
    private static final String TAG_SCH = "university";
    String table_adr  = "stdGetMatching";
    TextView mTextViewResult;
    Switch aSwitch;
    phpConnect task;
    String arr_tid[]; // t_id 저장 배열
    String sid= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_tutor);

        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        sid = pref.getString("id", "default");
        mlistView = (ListView)findViewById(R.id.listView_tutor_list) ;
        task = new phpConnect();
        task.execute();


        aSwitch = (Switch) findViewById(R.id.ttSwitch);

        //스위치 클릭
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    TextView titleLine = (TextView) findViewById(R.id.myTutorText);
                    titleLine.setText("익선생님 관리");
                    table_adr = "stdGetMatching";
                    task = new phpConnect();
                    task.execute();

                } else {
                    TextView titleLine = (TextView) findViewById(R.id.myTutorText);
                    titleLine.setText("나의 선생님 관리");
                    table_adr = "stdGetMatching";
                    task = new phpConnect();
                    task.execute();
                }
            }
        });


        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreStd(position);
            }
        });

    }

    void getMoreStd(int position) {
        final int index = position;
        new AlertDialog.Builder(stdManageTT.this)
                .setTitle("선생님 정보창" )
                .setMessage("\n선생님 정보 : " + arr_tid[position])
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(stdManageTT.this, "확인 클릭", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("대화하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),ChattingActivity.class);
                        intent.putExtra("oppositeID",arr_tid[index]); //대화할 상대 선생 아이디 전송
                        startActivity(intent);
                    }
                })
                .show();
    }

    class phpConnect extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String link = "http://1.234.38.211/"+table_adr+".php?id="+sid;
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

            arr_tid = new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String st_id = item.getString(TAG_SID);
                String tt_id = item.getString(TAG_TID);
                String ttNick = item.getString(TAG_NICK);
                String schType = item.getString(TAG_SCH);

                HashMap<String,String> hashMap = new HashMap<>();
                arr_tid[i]=tt_id;

                hashMap.put(TAG_SID , st_id);
                hashMap.put(TAG_TID, tt_id);
                hashMap.put(TAG_NICK , ttNick);
                hashMap.put(TAG_SCH, schType);

                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    stdManageTT.this, mArrayList, R.layout.mng_tt_list,
                    new String[]{TAG_TID,TAG_SCH},
                    new int[]{R.id.listTTNick, R.id.listUniv}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_TID, "");
            hashMap.put(TAG_SCH, "");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    stdManageTT.this, mArrayList, R.layout.mng_tt_list,
                    new String[]{TAG_TID,TAG_SCH},
                    new int[]{R.id.listTTNick, R.id.listUniv}
            );
            mlistView.setAdapter(adapter);
            Log.d(TAG, "showResult : ", e);
        }
    }

    public void AddTeacher(View v){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.AddTeacherPopup.class);
        startActivity(intent);
    }
}
