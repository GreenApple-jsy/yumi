package com.example.yumi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class TutorQuestionDetailActivity extends AppCompatActivity {
    GetImage task;
    Bitmap img;
    ImageView qimage;
    LayoutInflater mLayoutInflater = null;
    String JsonResultString;
    QuestionData q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail_tutor);

        System.out.print("W");

        Intent intent = getIntent(); /*데이터 수신*/
        final int question_id = intent.getExtras().getInt("question_id"); //상세 정보를 볼 문제 번호 받아옴

        GetData task = new GetData();
        task.execute( "http://1.234.38.211/getOneQuestionDetail.php?id=" + Integer.toString(question_id), "");

        Button MyPageButton = findViewById(R.id.reserve);
        MyPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Tutor_reservation.class);
                intent.putExtra("question_id",question_id);
                startActivity(intent);
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
        String TAG_NICK = "nickname";
        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            q = new QuestionData(parseInt(item.getString(TAG_ID)),item.getString(TAG_BOOK),item.getString(TAG_PAGE),
                    item.getString(TAG_QNUM),item.getString(TAG_STIME),
                    item.getString(TAG_IMAGE) ,item.getString(TAG_TID),item.getString(TAG_SID)
                    ,parseInt(item.getString(TAG_COMPLETE)),item.getString(TAG_QLINK)
                    ,item.getString(TAG_AGE),item.getString(TAG_SEMESTER), parseInt(item.getString(TAG_RESERV)),
                    item.getString(TAG_SCHOOL),item.getString(TAG_CHP), item.getString(TAG_DATES),item.getString(TAG_NICK)
            );
            setQInfo();
        } catch (JSONException e) {

        }

    }

    public void setQInfo() {

        qimage = findViewById(R.id.q_image);
        task = new GetImage();
        if(task != null){
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,q.getimage());
        }
        else{
            task = new GetImage();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,q.getimage());
        }

        TextView tv_nick = findViewById(R.id.nickname);
        TextView tv_age = findViewById(R.id.age);
        TextView tv_book= findViewById(R.id.book);
        TextView tv_page= findViewById(R.id.page);
        TextView tv_qnum= findViewById(R.id.q_number);
        TextView tv_chp = findViewById(R.id.chapter);
        TextView tv_school = findViewById(R.id.school_type);
        TextView tv_dates = findViewById(R.id.dates);


        tv_dates.setText("풀이 요청일 : " + q.getDates());
        tv_school.setText(q.getSchool());
        tv_age.setText(q.getage());
        tv_chp.setText(q.getCHP());
        tv_nick.setText(q.getNickname()+ " 학생");
        tv_book.setText("교재 : " + q.getbook());
        tv_page.setText(q.getpage() + " 페이지");
        tv_qnum.setText(q.getqnumber() + "번");


    }


    private class GetImage extends AsyncTask<String, Integer,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                img = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return img;
        }
        protected void onPostExecute(Bitmap img){
            qimage.setImageBitmap(img);
        }
    }
}