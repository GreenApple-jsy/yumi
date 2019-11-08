package com.example.yumi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Studentsignup extends AppCompatActivity {

    EditText et_id, et_pw, et_pw_chk;
    String sId, sPw, sPw_chk;
    String JsonResultString;
    Studentsignup.GetData task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("학생 회원가입");
        setContentView(R.layout.activity_studentsignup);

        et_id = (EditText) findViewById(R.id.id_text);
        et_pw = (EditText) findViewById(R.id.password_text);
        et_pw_chk = (EditText) findViewById(R.id.password_check2);
    }

    public void goBack(View view){
        //취소 버튼을 누른 경우
        Intent intent2 = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent2);
    }

    //계속하기 버튼을 누른 경우
    public void goNext(View view){

        /* 버튼을 눌렀을 때 동작하는 소스 */
        sId = et_id.getText().toString();
        sPw = et_pw.getText().toString();
        sPw_chk = et_pw_chk.getText().toString();

        /*아이디>=1 비밀번호>=6 비밀번호와 비밀번호 확인이 일치하여야 함*/
        if((sId.getBytes().length>=1 && sPw.getBytes().length>=6 && sPw_chk.getBytes().length>=6)){

            if(sPw.equals(sPw_chk))
            {
                Toast.makeText(Studentsignup.this, String.format("\n비밀번호가 일치합니다"), Toast.LENGTH_SHORT).show();
                /* 패스워드 확인이 정상적으로 됨 */
                //다음 학생 정보 입력하는 화면으로 넘어감.
                Intent intent = new Intent(getApplicationContext(), Privateinformation2.class);

                intent.putExtra("id",sId); /*송신  id와 pw를 다음 개인정보 activity에 넘긴 후 한 번에 db에 insert*/
                intent.putExtra("pw",sPw);
                startActivity(intent);

            }
            else {
                /* 패스워드 확인이 불일치 함 */
                Toast.makeText(Studentsignup.this, "\n" +
                        "비밀번호가 불일치합니다", Toast.LENGTH_SHORT).show();
            }
        }
        else{  Toast.makeText(Studentsignup.this, "\n" +
                "입력 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            //Toast.makeText(Studentsignup.this, String.format("\n%d %d", sPw.getBytes().length, sId.getBytes().length), Toast.LENGTH_SHORT).show();
        }

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

            if (check.equals("1")) {
                Toast.makeText(getApplicationContext(),"이 아이디는 안됩니다.", Toast.LENGTH_SHORT).show();
            }
            if (check.equals("0")) {
                Toast.makeText(getApplicationContext(),"이 아이디 가능합니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
        }
    }
}