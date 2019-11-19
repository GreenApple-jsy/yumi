package com.example.yumi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Tutor_reservation extends AppCompatActivity {
    String JsonResultString;
    GetData task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_question_reserve);
        Intent intent = getIntent(); /*데이터 수신*/
        final int question_id = intent.getExtras().getInt("question_id");
        final String dates = intent.getExtras().getString("datepick");
        System.out.println(dates);

        //풀이 시간 선택하기
        Button timepick = findViewById(R.id.timepick);
        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Tutor_timetable.class);
                intent.putExtra("question_id", question_id);
                startActivity(intent);
            }
        });
        SharedPreferences auto = getSharedPreferences("yumi", Activity.MODE_PRIVATE);
        final String nickname = auto.getString("nickName",null);
        //예약하기
        Button reserve = findViewById(R.id.reserve);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new GetData();
                task.execute("http://1.234.38.211/Tutor_reservation.php?id=" + question_id + "&playtime=" + dates + "&t_id=" + nickname, "");
                Intent intent3 = new Intent(getApplicationContext(), TutorQuestionlist.class);
                startActivity(intent3);
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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                return null;
            }
        }
    }

    public void InitializeQuestionData() {
        String TAG_JSON = "webnautes";
        String TAG_CHECK = "check";

        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            String check = item.getString(TAG_CHECK);


        } catch (JSONException e) {
        }
    }
}