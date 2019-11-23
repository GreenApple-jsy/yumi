package com.example.yumi;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class StudentQuestionlist extends AppCompatActivity implements HomeLogFragment.OnFragmentInteractionListener, PersonLogFragment.OnFragmentInteractionListener, SettingLogFragment.OnFragmentInteractionListener,SearchLogFragment.OnFragmentInteractionListener{
    ArrayList<QuestionData> QuestionDataList;
    String JsonResultString;
    StudentQuestionAdapter questionAdapter;
    ListView listView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    GetData task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_questionlist);
        listView = findViewById(R.id.listView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);

        task = new GetData();
        task.execute( "http://1.234.38.211/getCompleteQdata.php", "");



        FloatingActionButton uploadQuestion = findViewById(R.id.uploadButton);
        uploadQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), uploadq.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Intent intent = new Intent(getApplicationContext(), StudentQuestionDetailActivity.class);
                intent.putExtra("question_id",QuestionDataList.get(position).getid());
                startActivity(intent); //문제 상세 정보 띄어주기 액티비티
            }
        });



        new Thread(new Runnable() {
            @Override public void run() {
                BottomBar bottomBar = findViewById(R.id.bottomBar);

                bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(int tabId) {
                        if (tabId == R.id.tab_person_log){
                            Intent intent = new Intent(getApplicationContext(),stdMyPage.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_search_log){
                            Intent intent = new Intent(getApplicationContext(), stdSelect.class);
                            startActivity(intent);
                        }
                        else if (tabId == R.id.tab_setting_log){
                            Intent intent = new Intent(getApplicationContext(), stdPreferences.class);
                            startActivity(intent);
                        }
                    }
                });

            } }).start();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로고침 작업 실행...
                task.execute("http://1.234.38.211/getCompleteQdata.php", "");

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        // Scheme colors for animation
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_blue_bright)
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_actions,menu);
        return true;
    }


    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                JsonResultString = result;
                InitializeQuestionData();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                return null;
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) { case KeyEvent.KEYCODE_BACK: return true;
        } return super.onKeyDown(keyCode, event);
    }


    public void InitializeQuestionData()
    {
        String TAG_JSON="webnautes";
        String TAG_ID = "id";
        String TAG_BOOK = "book";
        String TAG_PAGE ="page";
        String TAG_QNUM ="q_number";
        String TAG_STIME ="start_time";
        String TAG_ETIME ="end_time";
        String TAG_IMAGE ="q_image";
        String TAG_TID ="t_id";
        String TAG_SID ="s_id";
        String TAG_COMPLETE ="complete";
        String TAG_GOOD ="good";
        String TAG_QLINK ="q_link";
        String TAG_AGE ="age";
        String TAG_SEMESTER ="semester";
        String TAG_RESERV ="reservation";

        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            QuestionDataList = new ArrayList<QuestionData>();

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                QuestionDataList.add(new QuestionData(parseInt(item.getString(TAG_ID)),item.getString(TAG_BOOK),item.getString(TAG_PAGE),
                        item.getString(TAG_QNUM),item.getString(TAG_STIME),
                        item.getString(TAG_IMAGE) ,item.getString(TAG_TID),item.getString(TAG_SID)
                        ,parseInt(item.getString(TAG_COMPLETE)),item.getString(TAG_QLINK)
                        ,item.getString(TAG_AGE),item.getString(TAG_SEMESTER), parseInt(item.getString(TAG_RESERV))
                ));
            }
            questionAdapter = new StudentQuestionAdapter(this,QuestionDataList);
            listView.setAdapter(questionAdapter);

        } catch (JSONException e) {

        }

    }
}
