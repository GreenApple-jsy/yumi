package com.example.yumi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddTeacherPopup extends Activity {
    String JsonResultString;
    EditText TeacherIDField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_teacher_popup);
        TeacherIDField = findViewById(R.id.idField);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        String TeacherID = TeacherIDField.getText().toString();

        SharedPreferences sf = getSharedPreferences("yumi",MODE_PRIVATE);
        final String s_id= sf.getString("id","null");

        GetData task = new GetData();
        task.execute( "http://1.234.38.211/addMatching.php?s_id=" + s_id + "&t_id=" + TeacherID ,"");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){ //바깥레이어 클릭시 안닫히게
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return; //안드로이드 백버튼 막기
    }

    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                JsonResultString = result;
                Initialize();
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

    public void Initialize() {
        String TAG_JSON = "webnautes";
        String TAG_CHECK = "check";
        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            String check = item.getString(TAG_CHECK);

            if (check.equals("0")){ //존재하지 않는 선생 아이디
                Toast.makeText(getApplicationContext(),"존재하지 않는 선생님 아이디입니다" ,Toast.LENGTH_LONG).show();
            }
            else if (check.equals("1")){ //이미 자신과 매칭된 선생님
                Toast.makeText(getApplicationContext(),"이미 매칭된 선생님입니다" ,Toast.LENGTH_SHORT).show();
            }
            else if (check.equals("2")){ //잠시 후 다시 시도해주세요
                Toast.makeText(getApplicationContext(),"잠시 후 다시 시도해주세요" ,Toast.LENGTH_SHORT).show();
            }
            else if (check.equals("3")){ //성공
                Toast.makeText(getApplicationContext(),"선생님 매칭 성공!" ,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"알 수 없는 오류가 발생하였습니다" ,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {

        }
        finish(); //액티비티(팝업) 닫기
    }
}
