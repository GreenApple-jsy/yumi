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

public class stdMngBooking extends AppCompatActivity {


    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ListView mlistView;
    String mJsonString, uJsonString;
    ArrayList<HashMap<String, String>> mArrayList;
    private static final String ID = "id";
    private static final String TAG_TID = "t_id";
    private static final String TAG_BOOK = "book";
    private static final String TAG_STime = "start_time";
    private static final String TAG_PAGE = "page";
    private static final String TAG_QN = "start_time";
    private static final String TAG_CHP = "chapter";
    private static final String TAG_DT = "dates";
    String table_adr= "stdReadyForBooking";
    TextView mTextViewResult, uTextViewResult;
    Switch aSwitch;
    phpConnect task;
    int arr_id[];
    String arr_tid[]; // s_id 저장 배열
    String st_time[];

    String sid = "";
    int index_num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.std_mng_booking);
        mlistView = (ListView)findViewById(R.id.std_booking_list) ;

        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        sid = pref.getString("id", "default");

        task = new phpConnect();
        task.execute();
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreBooking(position);
            }
        });


        aSwitch = (Switch) findViewById(R.id.stdBookSwitch);

        //스위치 클릭
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    table_adr = "stdAlreadyDoneBooking";
                    task = new phpConnect();
                    task.execute();

                    mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            getMoreBooking(position);
                        }
                    });

                } else {
                    table_adr= "stdReadyForBooking";
                    task = new phpConnect();
                    task.execute();

                    mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            getMoreBooking(position);
                        }
                    });
                }
            }
        });


    }


    void getMoreBooking(int position) {
        index_num = position;

        new AlertDialog.Builder(stdMngBooking.this)
                .setTitle("예약 정보" )
                .setMessage("선생님 정보 : " + arr_tid[position] +"\n"+"예약시간 : " + st_time[position]+"입니다.")
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
                        intent.putExtra("oppositeID",arr_tid[index_num]); //대화할 상대 선생 아이디 전송
                        startActivity(intent);
                    }
                })
                .show();
    }


    class phpConnect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                System.out.println("hhh" + table_adr);
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
                Log.d(TAG, "result is null");
                mTextViewResult.setText("error occurred");showResult();
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
            arr_tid = new String[jsonArray.length()];
            st_time = new String[jsonArray.length()];


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                int id_num = item.getInt(ID);
                String bookName = item.getString(TAG_BOOK);
                String startTime = item.getString(TAG_STime);
                String t_id = item.getString(TAG_TID);
                String page_num = item.getString(TAG_PAGE);
                String q_num = item.getString(TAG_QN);
                String chapter = item.getString(TAG_CHP);
                String getDate = item.getString(TAG_DT);
                HashMap<String,String> hashMap = new HashMap<>();

                arr_id[i]=id_num;
                arr_tid[i]=t_id;
                st_time[i]=startTime;


                hashMap.put(TAG_TID, t_id);
                hashMap.put(TAG_BOOK , bookName);
                hashMap.put(TAG_QN, q_num);
                hashMap.put(TAG_PAGE, page_num);
                hashMap.put(TAG_CHP,chapter);
                hashMap.put(TAG_DT, getDate);

                mArrayList.add(hashMap);
            }


            ListAdapter adapter = new SimpleAdapter(
                    stdMngBooking.this, mArrayList, R.layout.std_booking_list,
                    new String[]{TAG_TID, TAG_BOOK, TAG_CHP, TAG_PAGE,TAG_STime, TAG_DT},
                    new int[]{R.id.bookStdNick, R.id.booking_book, R.id.chapter,R.id.booking_page, R.id.booking_start_time, R.id.booking_date}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_TID, "");
            hashMap.put(TAG_BOOK,"");
            hashMap.put(TAG_QN, "");
            hashMap.put(TAG_PAGE, "");
            hashMap.put(TAG_CHP,"");
            hashMap.put(TAG_DT, "");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    stdMngBooking.this, mArrayList, R.layout.std_booking_list,
                    new String[]{TAG_TID, TAG_BOOK, TAG_CHP, TAG_PAGE,TAG_STime, TAG_DT},
                    new int[]{R.id.bookStdNick, R.id.booking_book, R.id.chapter,R.id.booking_page, R.id.booking_start_time, R.id.booking_date}
            );

            mlistView.setAdapter(adapter);
            Log.d(TAG, "showResult : ", e);
        }

    }
}
