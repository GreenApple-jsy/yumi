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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


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
    private static final String TAG_TID = "t_id";
    private static final String TAG_BOOK = "book";
    private static final String TAG_sTime = "start_time";
    private static final String TAG_DT = "dates";
    private static final String TAG_CHP = "chapter";
    private static final String TAG_PAGES = "page";
    private static final String TAG_QN = "q_number";
    private static final String TAG_NICK = "nickname";
    private static final String TAG_PLY = "playtime";

    int listNum =0;
    int index_num=0;
    phpConnect task;
    phpUpdate upTask;
    int arr_id[];
    String arr_sid[]; // s_id 저장 배열
    String arr_nick[];
    String st_time[];
    String end_time[];
    String yyyy="", mm="", dd="";
    String sid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.std_my_page);

        // 학생 프로필 정보
        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        String nickName = pref.getString("nickName", "default");
        String school = pref.getString("school", "default");
        String grade = pref.getString("grade", "default");
        sid = pref.getString("id", "default");

        nickText = (TextView)findViewById(R.id.stdNick);
        nickText.setText(nickName);

        schoolText = (TextView)findViewById(R.id.schoolName);
        schoolText.setText(school);

        gradeText = (TextView)findViewById(R.id.stdGrade);
        gradeText.setText(grade);

        mlistView = (ListView)findViewById(R.id.std_class_list) ;


        Date currentTime = Calendar.getInstance().getTime();
        //SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        //String weekDay = weekdayFormat.format(currentTime);
        yyyy = yearFormat.format(currentTime);
        mm = monthFormat.format(currentTime);
        dd = dayFormat.format(currentTime);
        if (dd.substring(0,1).equals("0")){
            dd = dd.substring(1,2);
        }

        task = new stdMyPage.phpConnect();
        task.execute();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(getApplicationContext(), StudentQuestionDetailActivity.class);
                    intent.putExtra("question_id", arr_id[position]);
                    startActivity(intent); //문제 상세 정보 띄어주기 액티비티
                    //getMoreBooking(position);
                }
                catch (Exception e){

                }
            }
        });

        new Thread(new Runnable() {
            @Override public void run() {
                BottomBar bottomBar = findViewById(R.id.bottomBar);
                bottomBar.setDefaultTab(R.id.tab_person_log);
                bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(int tabId) {
                        if (tabId == R.id.tab_home_log){
                            Intent intent = new Intent(getApplicationContext(),StudentQuestionlist.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_search_log){
                            Intent intent = new Intent(getApplicationContext(), stdSelect.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_chatting_log){
                            Intent intent = new Intent(getApplicationContext(), stdPreferences.class);
                            startActivity(intent);
                        }
                    }
                });
            } }).start();


    }

    @Override public void onBackPressed() { //super.onBackPressed();
    }// 뒤로 가기 막기

    void getMoreBooking(int position) {
        index_num = position;

        new AlertDialog.Builder(stdMyPage.this)
                .setTitle("예약 정보" )
                .setMessage("선생님 정보 : " + arr_nick[position] +"\n"+"예약시간 : " + st_time[position]+"입니다.")
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
                        intent.putExtra("oppositeID",arr_nick[index_num]); //대화할 상대 선생 아이디 전송
                        startActivity(intent);
                    }
                })
                .show();
    }


    class phpConnect extends AsyncTask<String,Void,String> {
        String stringParameter = "&yyyy="+yyyy+"년&mm="+mm+"월&dd="+dd;

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String link = "http://1.234.38.211/todayClass.php?id="+sid+stringParameter;

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
            listNum = jsonArray.length();
            arr_id = new int[jsonArray.length()];
            arr_sid = new String[jsonArray.length()];
            arr_nick = new String[jsonArray.length()];
            st_time = new String[jsonArray.length()];
            end_time = new String[jsonArray.length()];


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                int id_num = item.getInt(ID);
                String bookName = item.getString(TAG_BOOK);
                String pages = item.getString(TAG_PAGES);
                String q_num  = item.getString(TAG_QN);
                String startTime = item.getString(TAG_sTime);
                String dates = item.getString(TAG_DT);
                String chapter = item.getString(TAG_CHP);
                String st_id = item.getString(TAG_SID);
                String tt_id = item.getString(TAG_TID);
                String tt_nick = item.getString(TAG_NICK);
                String playtime = item.getString(TAG_PLY);

                HashMap<String,String> hashMap = new HashMap<>();

                arr_id[i]=id_num;
                arr_sid[i]=st_id;
                arr_nick[i]=tt_nick;
                st_time[i]=startTime;

                hashMap.put(TAG_SID, st_id);
                hashMap.put(TAG_TID, tt_id);
                hashMap.put(TAG_BOOK , bookName);
                hashMap.put(TAG_sTime, startTime);
                hashMap.put(TAG_CHP , chapter);
                hashMap.put(TAG_DT, dates);
                hashMap.put(TAG_PAGES , pages +  " 페이지");
                hashMap.put(TAG_QN , q_num+ " 번");
                hashMap.put(TAG_NICK, tt_nick + " 선생님");
                hashMap.put(TAG_PLY, playtime);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    stdMyPage.this, mArrayList, R.layout.std_today_list,
                    new String[]{TAG_NICK, TAG_BOOK, TAG_CHP, TAG_PAGES, TAG_QN, TAG_DT, TAG_PLY },
                    new int[]{R.id.ttNick,R.id.bookName, R.id.chapter, R.id.pages, R.id.qNum, R.id.TodayDate, R.id.startTime}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_SID, "");
            hashMap.put(TAG_TID, "");
            hashMap.put(TAG_sTime, "");
            hashMap.put(TAG_CHP , "오늘 수업은 없습니다.");
            hashMap.put(TAG_DT, "");
            hashMap.put(TAG_BOOK , "");
            hashMap.put(TAG_PAGES , "");
            hashMap.put(TAG_NICK, "");
            hashMap.put(TAG_PLY,"");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    stdMyPage.this, mArrayList, R.layout.std_today_list,
                    new String[]{TAG_NICK, TAG_BOOK, TAG_CHP, TAG_PAGES, TAG_QN, TAG_DT, TAG_PLY },
                    new int[]{R.id.ttNick,R.id.bookName, R.id.chapter, R.id.pages, R.id.qNum, R.id.TodayDate, R.id.startTime}
            );
            listNum =0;
            mlistView.setAdapter(adapter);
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
