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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


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
    private static final String TAG_TID = "t_id";
    private static final String TAG_BOOK = "book";
    private static final String TAG_sTime = "start_time";
    private static final String TAG_DT = "dates";
    private static final String TAG_CHP = "chapter";
    private static final String TAG_PAGES = "page";
    private static final String TAG_QN = "q_number";
    private static final String TAG_NICK = "nickname";
    private static final String TAG_PLY = "playtime";
    int index_num=0;
    phpConnect task;
    int arr_id[];
    String arr_nick[];
    String arr_sid[]; // s_id 저장 배열
    String st_time[]; // 강의 시작 시작 배열
    String tid="";
    String yyyy="", mm="", dd="";

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
        mlistView = (ListView)findViewById(R.id.listView_today_list) ;
        // date 데이터 완성되면 추후 주석 풀 것
        task = new tutorMyPage.phpConnect();
        task.execute();

        System.out.println("sid " + tid + " yyyy " + yyyy + " " + mm + " " +dd);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getMoreBooking(position);
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
                            Intent intent = new Intent(getApplicationContext(),TutorQuestionlist.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_search_log){
                            Intent intent = new Intent(getApplicationContext(), tutorSelect.class);
                            startActivity(intent);
                            //Toast.makeText(tutorMyPage.this, "화면 연결 전입니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (tabId == R.id.tab_chatting_log){
                            Intent intent = new Intent(getApplicationContext(), tutorPreferences.class);
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
        try {
            new AlertDialog.Builder(tutorMyPage.this)
                    .setTitle("예약 정보")
                    .setMessage("학생 정보 : " + arr_nick[position] + "\n" + "강의시간은 " + st_time[position] + "입니다.")
                    .setPositiveButton("문제 상세 보기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), TutorQuestionDetailActivity.class);
                            intent.putExtra("question_id", arr_id[index_num]);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("대화하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), ChattingActivity.class);
                            intent.putExtra("oppositeID", arr_sid[index_num]); //대화할 상대 선생 아이디 전송
                            startActivity(intent);
                        }
                    })
                    .show();
        }
        catch (Exception e){

        }
    }


    class phpConnect extends AsyncTask<String,Void,String> {
        String stringParameter = "&yyyy="+yyyy+"년&mm="+mm+"월&dd="+dd;
        @Override
        protected String doInBackground(String... arg0) {
            try {
                // 날짜 추가
                String link = "http://1.234.38.211/todayLecture.php?id="+tid+stringParameter;
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

    private void showResult(){

        mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            arr_id = new int[jsonArray.length()];
            arr_sid = new String[jsonArray.length()];
            st_time = new String[jsonArray.length()];
            arr_nick = new String[jsonArray.length()];


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                int id_num = item.getInt(ID);
                String bookName = item.getString(TAG_BOOK);
                String pages = item.getString(TAG_PAGES); pages+=" 페이지";
                String q_num = item.getString(TAG_QN) +" 번";
                String startTime = item.getString(TAG_sTime);
                String dates = item.getString(TAG_DT);
                String chapter = item.getString(TAG_CHP);
                String st_id = item.getString(TAG_SID);
                String tt_id = item.getString(TAG_TID);
                String s_nick = item.getString(TAG_NICK);
                String playtime = item.getString(TAG_PLY);



                arr_id[i]=id_num;
                arr_sid[i]=st_id;
                st_time[i]=playtime;
                arr_nick[i]=s_nick;


                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_SID, st_id);
                hashMap.put(TAG_TID, tt_id);
                hashMap.put(TAG_BOOK , bookName);
                hashMap.put(TAG_sTime, startTime);
                hashMap.put(TAG_CHP , chapter);
                hashMap.put(TAG_DT, dates);
                hashMap.put(TAG_BOOK , bookName);
                hashMap.put(TAG_PAGES , pages);
                hashMap.put(TAG_QN , q_num);
                hashMap.put(TAG_PLY, playtime);
                hashMap.put(TAG_NICK, s_nick+" 학생" );
                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    tutorMyPage.this, mArrayList, R.layout.tutor_today_list,
                    new String[]{TAG_NICK, TAG_BOOK, TAG_CHP, TAG_PAGES, TAG_QN , TAG_DT, TAG_PLY},
                    new int[]{R.id.stdNick,R.id.bookName, R.id.chapter, R.id.pages, R.id.qNum , R.id.TodayDate, R.id.startTime}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_SID, "");
            hashMap.put(TAG_TID, "");
            hashMap.put(TAG_BOOK , "");
            hashMap.put(TAG_sTime, "");
            hashMap.put(TAG_CHP , "오늘 강의는 없습니다.");
            hashMap.put(TAG_DT, "");
            hashMap.put(TAG_BOOK , "");
            hashMap.put(TAG_PAGES ,"");
            hashMap.put(TAG_QN , "");
            hashMap.put(TAG_NICK , "");
            hashMap.put(TAG_PLY, "");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    tutorMyPage.this, mArrayList, R.layout.tutor_today_list,
                    new String[]{TAG_NICK, TAG_BOOK, TAG_CHP, TAG_PAGES, TAG_QN , TAG_DT, TAG_PLY},
                    new int[]{R.id.stdNick,R.id.bookName, R.id.chapter, R.id.pages, R.id.qNum , R.id.TodayDate, R.id.startTime}
            );
            mlistView.setAdapter(adapter);
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



}
