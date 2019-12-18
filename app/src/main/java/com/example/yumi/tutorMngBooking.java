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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class tutorMngBooking extends AppCompatActivity {


    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    String mJsonString, uJsonString;
    ArrayList<HashMap<String, String>> mArrayList;
    private static final String ID = "id";
    private static final String TAG_SID = "s_id";
    private static final String TAG_BOOK = "book";
    private static final String TAG_STime = "start_time";
    private static final String TAG_PAGE = "page";
    private static final String TAG_QN = "q_number";
    private static final String TAG_CHP = "chapter";
    private static final String TAG_DT = "dates";
    private static final String TAG_NICK  = "nickname";
    private static final String TAG_PLY = "playtime";


    String table_adr= "alreadyDoneBooking";
    TextView mTextViewResult, uTextViewResult;
    Switch aSwitch;
    phpConnect task;
    phpUpdate uptTask;
    phpCancel ccTask;
    int arr_id[];
    String arr_sid[]; // s_id 저장 배열
    String arr_nick[];
    String st_time[];
    String end_time[];
    String tid = "";
    int index_num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_mng_booking);
        mlistView = (ListView)findViewById(R.id.listView_booking_list) ;

        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        tid = pref.getString("id", "default");

        task = new phpConnect();
        task.execute();
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreBooking(position);
            }
        });



    }

    void editBooking(int position) {
        index_num = position;

        new AlertDialog.Builder(tutorMngBooking.this)
                .setTitle("예약 정보" )
                .setMessage("학생 정보 : " + arr_sid[position] +"\n"+"예약시간 : " + st_time[position])
                .setPositiveButton("대화하기",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),ChattingActivity.class);
                        intent.putExtra("oppositeID",arr_sid[index_num]); //대화할 상대 학생 아이디 전송
                        startActivity(intent);
                    }
                })
                .setNeutralButton("예약 취소하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ccTask = new phpCancel();
                        ccTask.execute();
                        Toast.makeText(tutorMngBooking.this, "예약을 취소하셨습니다.", Toast.LENGTH_SHORT).show();

                    }
                })
                .show();
    }

    void getMoreBooking(int position) {
        index_num = position;
        try {
            new AlertDialog.Builder(tutorMngBooking.this)
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

        @Override
        protected String doInBackground(String... arg0) {
            try {
                System.out.println("hhh" + table_adr);
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
                Log.d(TAG, "result is null");
                mTextViewResult.setText("error occurred");showResult();
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
            System.out.println("~~~~~~````" +postParameters);

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

    class phpCancel extends AsyncTask<String, Void, String>{

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
                URL url = new URL("http://1.234.38.211/cancelBooking.php");
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
            arr_nick = new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                int id_num = item.getInt(ID);

                String bookName = item.getString(TAG_BOOK);
                String startTime = item.getString(TAG_STime);
                String s_id = item.getString(TAG_SID);
                String page_num = item.getString(TAG_PAGE);
                String q_num = item.getString(TAG_QN);
                String chapter = item.getString(TAG_CHP);
                String getDate = item.getString(TAG_DT);
                String s_nickname = item.getString(TAG_NICK);
                String playtime = item.getString(TAG_PLY);


                HashMap<String,String> hashMap = new HashMap<>();

                arr_id[i]=id_num;
                arr_sid[i]=s_id;
                st_time[i]=playtime;
                arr_nick[i]=s_nickname;

                hashMap.put(TAG_SID, s_id);
                hashMap.put(TAG_BOOK , "교재 : " +bookName);
                hashMap.put(TAG_QN, q_num+" 번");
                hashMap.put(TAG_PAGE, page_num+" 페이지");
                hashMap.put(TAG_CHP,"단원 : " +chapter);
                hashMap.put(TAG_STime, startTime);
                hashMap.put(TAG_DT, getDate);
                hashMap.put(TAG_NICK, s_nickname + " 학생");
                hashMap.put(TAG_PLY, playtime);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    tutorMngBooking.this, mArrayList, R.layout.std_booking_list,
                    new String[]{TAG_NICK, TAG_BOOK, TAG_CHP, TAG_PAGE, TAG_QN, TAG_PLY, TAG_DT},
                    new int[]{R.id.bookStdNick, R.id.booking_book, R.id.chapter,R.id.booking_page, R.id.booking_qn, R.id.booking_start_time, R.id.booking_date}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_SID, "");
            hashMap.put(TAG_BOOK , "");
            hashMap.put(TAG_QN, "");
            hashMap.put(TAG_PAGE, "");
            hashMap.put(TAG_CHP,"");
            hashMap.put(TAG_STime,"");
            hashMap.put(TAG_DT, "");
            hashMap.put(TAG_PLY, "");
            hashMap.put(TAG_NICK, "수업이 없습니다");

            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    tutorMngBooking.this, mArrayList, R.layout.std_booking_list,
                    new String[]{TAG_NICK, TAG_BOOK, TAG_CHP, TAG_PAGE, TAG_QN, TAG_PLY, TAG_DT},
                    new int[]{R.id.bookStdNick, R.id.booking_book, R.id.chapter,R.id.booking_page, R.id.booking_qn, R.id.booking_start_time, R.id.booking_date}
            );

            mlistView.setAdapter(adapter);
            Log.d(TAG, "showResult : ", e);
        }

    }
}
