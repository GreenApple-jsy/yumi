package com.example.yumi;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.core.app.NotificationCompat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class tutorMyPage extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    TextView nickText, univText ;
    String mJsonString;
    TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    private static final String ID = "id";
    private static final String TAG_SID = "s_id";
    private static final String TAG_BOOK = "book";
    private static final String TAG_STime = "start_time";
    private static final String TAG_PAGE = "book";
    private static final String TAG_QN = "start_time";


    int index_num=0;
    phpConnect task;
    phpUpdate upTask;
    int arr_id[];
    String arr_sid[]; // s_id 저장 배열
    String st_time[];
    String end_time[];
    String tid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_my_page);

        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        tid = pref.getString("id", "default");
        String nickName = pref.getString("nickName", "default");
        String univ = pref.getString("university", "default");
        nickText = (TextView)findViewById(R.id.tt_nick);
        nickText.setText(nickName);

        univText = (TextView)findViewById(R.id.tt_univ);
        univText.setText(univ);

        mlistView = (ListView)findViewById(R.id.listView_today_list) ;


        // date 데이터 완성되면 추후 주석 풀 것
        /*
        task = new tutorMyPage.phpConnect();
        task.execute();
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreBooking(position);
            }
        });
        */
    }

    class phpConnect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                // 날짜 추가
                String link = "http://1.234.38.211/readyForBooking.php?"+tid;
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



            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                int id_num = item.getInt(ID);
                String bookName = item.getString(TAG_BOOK);
                String startTime = item.getString(TAG_STime);
                String st_id = item.getString(TAG_SID);
                String page_num = item.getString(TAG_PAGE);
                String q_num = item.getString(TAG_QN);

                HashMap<String,String> hashMap = new HashMap<>();

                arr_id[i]=id_num;
                arr_sid[i]=st_id;
                st_time[i]=startTime;


                hashMap.put(TAG_SID, st_id);
                hashMap.put(TAG_BOOK , bookName);
                hashMap.put(TAG_QN, q_num);
                hashMap.put(TAG_PAGE, page_num);


                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    tutorMyPage.this, mArrayList, R.layout.tutor_today_list,
                    new String[]{TAG_SID,TAG_BOOK},
                    new int[]{R.id.tt_list_book,R.id.tt_list_sid}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    public void mngStudent(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.tutorManageStudent.class);
        startActivity(intent);

    }


    public void myRcdVideo(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.getTutorVideo.class);
        startActivity(intent);
    }


    public void mngTutorBooking(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.tutorMngBooking.class);
        startActivity(intent);
    }



    public void tutorPrf(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.yumi.tutorPreferences.class);
        startActivity(intent);
    }


    public void testPUSH(View view){

        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        String date = mFormat.format(mDate);
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);
        String hour = date.substring(11,13);
        String minute = date.substring(14,16);
        String mn = date.substring(date.length()-2,date.length());
        if (mn == "오후"){
            int editHour = Integer.parseInt(hour);
            editHour += 12;
            year =  Integer.toString(editHour);
        }



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(tutorMyPage.this)
                        .setSmallIcon(R.drawable.icsunsang)
                        .setContentTitle("제목")
                        .setContentText("내용")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}