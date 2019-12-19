package com.example.yumi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

public class MyVideoList extends AppCompatActivity {
    ArrayList<QuestionData> QuestionDataList;
    String JsonResultString;
    StudentQuestionAdapter questionAdapter;
    ListView listView;
    MyVideoList.GetData task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_video_list);
        SharedPreferences pref = getSharedPreferences("yumi", MODE_PRIVATE);
        listView = findViewById(R.id.listView);
        task = new MyVideoList.GetData();
        task.execute( "http://1.234.38.211/getMyCompleteQdata.php?id=" + pref.getString("id", "default"), "");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Intent intent = new Intent(getApplicationContext(), watchVideo.class);
                intent.putExtra("question_id",QuestionDataList.get(position).getid());
                startActivity(intent); //문제 풀이 다시보기 액티비티 실행
            }
        });
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
        String TAG_QLINK ="q_link";
        String TAG_AGE ="age";
        String TAG_SEMESTER ="semester";
        String TAG_RESERV ="reservation";
        String TAG_SCHOOL="school_type";
        String TAG_CHP = "chapter";
        String TAG_DATES = "dates";
        String TAG_NICK  = "nickname";
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
                        ,item.getString(TAG_AGE),item.getString(TAG_SEMESTER), parseInt(item.getString(TAG_RESERV)),
                        item.getString(TAG_SCHOOL),item.getString(TAG_CHP), item.getString(TAG_DATES), item.getString(TAG_NICK)
                ));
            }
            questionAdapter = new StudentQuestionAdapter(this,QuestionDataList);
            listView.setAdapter(questionAdapter);

        } catch (JSONException e) {

        }

    }

    }
