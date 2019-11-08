package com.example.yumi;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class tutorManageStudent extends AppCompatActivity {


    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    String mJsonString;
    ArrayList<HashMap<String, String>> mArrayList;
    private static final String TAG_SID = "s_id";
    private static final String TAG_TID = "t_id";
    String table_adr  = "getMatching";
    String tid= "";
    TextView mTextViewResult;
    Switch aSwitch;
    phpConnect task;
    String arr_sid[]; // s_id 저장 배열


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_student);
        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        tid = pref.getString("id", "default");

        mlistView = (ListView)findViewById(R.id.listView_std_list) ;
        task = new phpConnect();
        task.execute();



        aSwitch = (Switch) findViewById(R.id.stdSwitch);

        //스위치 클릭
        //2019.11.08 기준 익명 학생 추후 수정 예정
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    TextView titleLine = (TextView) findViewById(R.id.mystudentText);
                    titleLine.setText("익명 학생 관리");
                    table_adr = "getMatching";
                    task = new phpConnect();
                    task.execute();

                } else {
                    TextView titleLine = (TextView) findViewById(R.id.mystudentText);
                    titleLine.setText("내 학생 관리");
                    table_adr = "getMatching";
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

        new AlertDialog.Builder(tutorManageStudent.this)
                .setTitle("학생 정보창" )
                .setMessage("\n학생 정보 : " + arr_sid[position])
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(tutorManageStudent.this, "확인 클릭", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("대화하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(tutorManageStudent.this, "대화창으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
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

                HashMap<String,String> hashMap = new HashMap<>();
                arr_sid[i]=st_id;

                hashMap.put(TAG_SID , st_id);
                hashMap.put(TAG_TID, tt_id);

                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    tutorManageStudent.this, mArrayList, R.layout.mng_std_list,
                    new String[]{TAG_SID,TAG_TID},
                    new int[]{R.id.textView_list_std, R.id.textView_list_tutor}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_SID, "");
            hashMap.put(TAG_TID, "");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    tutorManageStudent.this, mArrayList, R.layout.mng_std_list,
                    new String[]{TAG_SID,TAG_TID},
                    new int[]{R.id.textView_list_std, R.id.textView_list_tutor}
            );
            mlistView.setAdapter(adapter);
            Log.d(TAG, "showResult : ", e);
        }

    }
}
