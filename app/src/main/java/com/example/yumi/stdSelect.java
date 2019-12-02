package com.example.yumi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.annotation.ColorRes;
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

import static com.example.yumi.R.layout.search_list_detail;
import android.content.Context;
import android.content.Context;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class stdSelect extends AppCompatActivity {
    ArrayAdapter<CharSequence>midHighAdapter, subAdapter;
    int mOh = 0;
    int sub = 0;
    int cat = 0;
    phpConnect task;
    String grade= "";
    String subj = "";
    String categ = "";
    String[] array;
    String[] subArray;
    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    private static final String ID = "id";
    private static final String TAG_SID = "s_id";
    private static final String TAG_TID = "t_id";
    private static final String TAG_BOOK = "book";
    private static final String TAG_sTime = "start_time";
    private static final String TAG_DT = "dates";
    private static final String TAG_CHP = "chapter";
    private static final String TAG_PAGES = "page";
    private static final String TAG_QN = "q_number";
    Button more;
    View header;
    ListView mlistView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.std_category);

        final Spinner dropdown = (Spinner) findViewById(R.id.midHigh);
        final Spinner subject = (Spinner)findViewById(R.id.subject);
        final Spinner category = (Spinner)findViewById(R.id.category);



        mlistView = (ListView)findViewById(R.id.search_list) ;

        new Thread(new Runnable() {
            @Override public void run() {
                BottomBar bottomBar = findViewById(R.id.bottomBar);
                bottomBar.setDefaultTab(R.id.tab_search_log);
                bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(int tabId) {
                        if (tabId == R.id.tab_home_log){
                            Intent intent = new Intent(getApplicationContext(),StudentQuestionlist.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_person_log){
                            Intent intent = new Intent(getApplicationContext(), stdMyPage.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_setting_log){
                            Intent intent = new Intent(getApplicationContext(), stdPreferences.class);
                            startActivity(intent);
                        }
                    }
                });
            } }).start();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mOh = position;
                // 중학교
                if (position == 1 ) {
                    grade = "중학생";
                    midHighAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_subject, android.R.layout.simple_spinner_dropdown_item);
                    midHighAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subject.setAdapter( midHighAdapter);

                    subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sub = i ;
                            subArray= getResources().getStringArray(R.array.mid_subject);
                            subj = subArray[i];
                            if (sub == 1){
                                array = getResources().getStringArray(R.array.mid_position1);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position1, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 2){
                                array = getResources().getStringArray(R.array.mid_position2);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position2, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 3){
                                array = getResources().getStringArray(R.array.mid_position3);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position3, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 4){
                                array = getResources().getStringArray(R.array.mid_position4);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position4, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 5){
                                array = getResources().getStringArray(R.array.mid_position5);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position5, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub== 6){
                                array = getResources().getStringArray(R.array.mid_position6);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position6, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                       categ = array[i];
                                       cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                // 고등학교
                else if (position == 2){
                    grade= "고등학생";
                    midHighAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_subject, android.R.layout.simple_spinner_dropdown_item);
                    midHighAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subject.setAdapter( midHighAdapter);

                    subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sub = i ;
                            subArray= getResources().getStringArray(R.array.high_subject);
                            subj = subArray[i];
                            if (sub == 1){
                                array = getResources().getStringArray(R.array.high_position1);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position1, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 2){
                                array = getResources().getStringArray(R.array.high_position2);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position2, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 3){
                                array = getResources().getStringArray(R.array.high_position3);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position3, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 4){
                                array = getResources().getStringArray(R.array.high_position4);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position4, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 5){
                                array = getResources().getStringArray(R.array.high_position5);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position5, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub== 6){
                                array = getResources().getStringArray(R.array.high_position6);
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position6, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    public void search(View view ){
        if (mOh == 0 || sub == 0 || cat == 0){
            Toast.makeText(getApplicationContext(), "전체 항목을 선택해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            // category로만 검색
            categ = categ.replace(" ", "%20");
            task = new stdSelect.phpConnect();
            task.execute();
        }
    }


    class phpConnect extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                // 날짜 추가
                String link = "http://1.234.38.211/searchingQuestion.php?chapter="+categ;
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

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                int id_num = item.getInt(ID);
                String bookName = item.getString(TAG_BOOK);
                String pages = item.getString(TAG_PAGES);
                String q_num = item.getString(TAG_QN);
                String startTime = item.getString(TAG_sTime);
                String dates = item.getString(TAG_DT);
                String chapter = item.getString(TAG_CHP);
                String st_id = item.getString(TAG_SID);
                String tt_id = item.getString(TAG_TID);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_SID, st_id);
                hashMap.put(TAG_TID, tt_id);
                hashMap.put(TAG_BOOK , bookName);
                hashMap.put(TAG_sTime, startTime);
                hashMap.put(TAG_CHP , chapter);
                hashMap.put(TAG_DT, dates);
                hashMap.put(TAG_PAGES , pages+" 페이지");
                hashMap.put(TAG_QN , q_num + " 번");
                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    stdSelect.this, mArrayList, search_list_detail,
                    new String[]{TAG_TID, TAG_BOOK, TAG_CHP, TAG_PAGES, TAG_QN, TAG_DT },
                    new int[]{R.id.ttNick,R.id.bookName, R.id.chapter, R.id.bookPage, R.id.bookNum, R.id.date}
            ) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    Button b = (Button) v.findViewById(R.id.moreView);
                    b.setVisibility(View.VISIBLE);
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Toast.makeText(stdSelect.this, "향후 영상 다시보기 페이지로 넘어감", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return v;
                }
            };
            mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(TAG_SID, "");
            hashMap.put(TAG_TID, "");
            hashMap.put(TAG_BOOK , "");
            hashMap.put(TAG_sTime, "");
            hashMap.put(TAG_CHP , "해당 결과가 없습니다.");
            hashMap.put(TAG_DT, "");
            hashMap.put(TAG_BOOK , "");
            hashMap.put(TAG_PAGES , "");
            mArrayList.add(hashMap);
            ListAdapter adapter = new SimpleAdapter(
                    stdSelect.this, mArrayList, search_list_detail,
                    new String[]{TAG_TID, TAG_BOOK, TAG_CHP, TAG_PAGES, TAG_QN, TAG_DT },
                    new int[]{R.id.ttNick,R.id.bookName, R.id.chapter, R.id.bookPage, R.id.bookNum, R.id.date}
            ){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    Button b = (Button) v.findViewById(R.id.moreView);
                    b.setVisibility(View.INVISIBLE);
                    return v;
                }
            };
            mlistView.setAdapter(adapter);
            Log.d(TAG, "showResult : ", e);
        }

    }

}
