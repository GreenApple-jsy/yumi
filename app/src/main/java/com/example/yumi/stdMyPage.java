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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class stdMyPage extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    String mJsonString;
    TextView mTextViewResult;
    TextView nickText, schoolText, gradeText;

    ArrayList<HashMap<String, String>> mArrayList;
    private static final String ID = "id";
    private static final String TAG_SID = "s_id";
    private static final String BOOK = "book";
    private static final String sTime = "start_time";
    private static final String eTime = "end_time";
    int index_num=0;
    phpConnect task;
    phpUpdate upTask;
    int arr_id[];
    String arr_sid[]; // s_id 저장 배열
    String st_time[];
    String end_time[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.std_my_page);

        mlistView = (ListView)findViewById(R.id.std_lecture_list) ;


        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        String nickName = pref.getString("nickName", "default");
        String school = pref.getString("school", "default");
        String grade = pref.getString("grade", "default");

        nickText = (TextView)findViewById(R.id.stdNick);
        nickText.setText(nickName);

        schoolText = (TextView)findViewById(R.id.schoolName);
        schoolText.setText(school);


        gradeText = (TextView)findViewById(R.id.stdGrade);
        gradeText.setText(grade);

        task = new stdMyPage.phpConnect();
        task.execute();
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreBooking(position);
            }
        });

    }

    void getMoreBooking(int position) {
        index_num = position;

        new AlertDialog.Builder(stdMyPage.this)
                .setTitle("예약하기" )
                .setMessage("\n선생님 정보 : " + arr_sid[position])
                .setPositiveButton("대화하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(stdMyPage.this, "대화 창으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("예약하기 ("+ st_time[position]+" ~ "+end_time[position]+")", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        upTask = new phpUpdate();
                        upTask.execute();
                        Toast.makeText(stdMyPage.this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                })
                .show();
    }


    class phpConnect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String link = "http://1.234.38.211/readyForBooking.php";
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
        }
    }

    class phpUpdate extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... params) {

            String postParameters = "id=" + arr_id[index_num];

            try {
                URL url = new URL("http://1.234.38.211/ttUpdateBooking.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }

        }
    }


    private void showResult(){

        mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            arr_id = new int[jsonArray.length()];
            arr_sid = new String[jsonArray.length()];
            st_time = new String[jsonArray.length()];
            end_time = new String[jsonArray.length()];


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                int id_num = item.getInt(ID);
                String bookName = item.getString(BOOK);
                String startTime = item.getString(sTime);
                String endTime = item.getString(eTime);
                String st_id = item.getString(TAG_SID);


                HashMap<String,String> hashMap = new HashMap<>();

                arr_id[i]=id_num;
                arr_sid[i]=st_id;
                st_time[i]=startTime;
                end_time[i]=endTime;


                hashMap.put(TAG_SID, st_id);
                hashMap.put(BOOK , bookName);
                hashMap.put(sTime, startTime);
                hashMap.put(eTime, endTime);


                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    stdMyPage.this, mArrayList, R.layout.tutor_today_list,
                    new String[]{TAG_SID,BOOK},
                    new int[]{R.id.tt_list_book,R.id.tt_list_sid}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    public void myTutor(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.stdManageTT.class);
        startActivity(intent);

    }


    public void ReplayVideo(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.ReplayVideo.class);
        startActivity(intent);
    }


    public void StdMyBooking(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.stdMngBooking.class);
        startActivity(intent);
    }

}
