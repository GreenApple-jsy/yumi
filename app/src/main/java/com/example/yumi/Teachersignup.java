package com.example.yumi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Teachersignup extends AppCompatActivity {

    EditText et_id, et_pw, et_pw_chk, et_nick;
    String sId, sPw, sPw_chk, sNickname, sUniversity;
    int univIdx = 0;
    String JsonResultString;
    String JsonResultString1;
    Teachersignup.GetData task;
    Teachersignup.GetData1 task2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("선생님 회원가입");
        setContentView(R.layout.activity_teachersignup);

        //대학교 값 받아오기
        final Spinner dropdown = (Spinner) findViewById(R.id.university);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                univIdx = position;
                if (position != 0) {
                    sUniversity = (String) parent.getItemAtPosition(position).toString(); //String 입력받음.
                    Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + "을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        Button id_check = findViewById(R.id.id_check);
        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디 중복 체크
                //0-> ok,1->not ok
                EditText id_text = (EditText) findViewById(R.id.id_text);
                String id = id_text.getText().toString();
                task = new Teachersignup.GetData();
                task.execute("http://1.234.38.211/id_check.php?id=" + id, "");
            }
        });

        Button nickname_check = (Button) findViewById(R.id.id_check2);
        nickname_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //닉네임 중복 체크
                //0-> ok,1->not ok
                EditText et_nicknames = (EditText) findViewById(R.id.nickname);
                String nickname = et_nicknames.getText().toString();
                task2 = new Teachersignup.GetData1();
                task2.execute("http://1.234.38.211/nickname_check.php?nickname=" + nickname, "");
            }
        });

    }

    public void goNext(View view) {
        //텍스트 입력 창에 입력된 스트링 받아오기
        et_id = (EditText) findViewById(R.id.id_text);
        et_pw = (EditText) findViewById(R.id.password_text);
        et_pw_chk = (EditText) findViewById(R.id.password_check2);
        et_nick = (EditText) findViewById(R.id.nickname);

        if (view.getId() == R.id.s_continues2) {

            sId = et_id.getText().toString();  //텍스트 값 받아오기
            sPw = et_pw.getText().toString();
            sPw_chk = et_pw_chk.getText().toString();
            sNickname = et_nick.getText().toString();

            /*닉값 중복체크 해줘야 함. 아직 구현 전 */


            //null값이 아니어야 함. /비밀번호와 번호 확인이 일치하여야 함./
            if ((sId.getBytes().length >= 1 && sPw.getBytes().length >= 6 && sPw_chk.getBytes().length >= 6) && (sPw.equals(sPw_chk))) {
                //계속하기 버튼을 누른 경우
                Intent intent = new Intent(getApplicationContext(), Mail_verify.class);
                intent.putExtra("id", sId); /*송신  id와 pw를 다음 개인정보 activity에 넘긴 후 한 번에 db에 insert*/
                intent.putExtra("pw", sPw);
                intent.putExtra("nickname", sNickname);
                intent.putExtra("university", sUniversity);
                intent.putExtra("univ", univIdx);


                startActivity(intent);
            } else {
                Toast.makeText(Teachersignup.this, "\n" +
                        "입력 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void goBack(View view) {
        if (view.getId() == R.id.cancel2) {
            //뒤로 가기 버튼을 누른 경우
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }
    }

    class GetData extends AsyncTask<String, Void, String> {

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


        public void InitializeQuestionData() {
            String TAG_JSON = "webnautes";
            String TAG_CHECK = "check";

            try {
                JSONObject jsonObject = new JSONObject(JsonResultString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                JSONObject item = jsonArray.getJSONObject(0);
                String check = item.getString(TAG_CHECK);

                if (check.equals("1")) {
                    Toast.makeText(getApplicationContext(), "이 아이디는 안됩니다.", Toast.LENGTH_SHORT).show();
                }
                if (check.equals("0")) {
                    Toast.makeText(getApplicationContext(), "이 아이디 가능합니다.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
            }
        }
    }

    class GetData1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                JsonResultString1 = result;
                InitializeQuestionData1();
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

        public void InitializeQuestionData1() {
            String TAG_JSON = "webnautes";
            String TAG_CHECK = "check";

            try {
                JSONObject jsonObject = new JSONObject(JsonResultString1);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                JSONObject item = jsonArray.getJSONObject(0);
                String check = item.getString(TAG_CHECK);

                if (check.equals("1")) {
                    Toast.makeText(getApplicationContext(), "이 닉네임는 안됩니다.", Toast.LENGTH_SHORT).show();
                }
                if (check.equals("0")) {
                    Toast.makeText(getApplicationContext(), "이 닉네임은 가능합니다.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {

            }
        }
    }
}