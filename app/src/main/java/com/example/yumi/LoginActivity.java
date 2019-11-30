package com.example.yumi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.collection.LLRBNode;

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

public class LoginActivity extends AppCompatActivity {
    EditText et_id, et_pw, et_pw_chk;
    TextView message ;
    String sId, sPw;

    String JsonResultString;
    LoginActivity.GetData task;
    ArrayList<QuestionData> QuestionDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log in");
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.username);  //id
        et_pw = (EditText) findViewById(R.id.password);  //password

    }

    public void login_btn(View view) {
        /* 버튼을 눌렀을 때 동작하는 소스 */
        sId = et_id.getText().toString();
        sPw = et_pw.getText().toString();   //id, password 텍스트 가져오기
        task = new LoginActivity.GetData();

        // 로그인 실패시 꺼지는 문제 해결 2019.11.07
        if(sId.equals("") || sPw.equals("")){
            message = (TextView)findViewById(R.id.ifFail);
            message.setText("ID 혹은 Password가 입력되지 않았습니다.....");
            message.setTextColor(Color.RED);
        }
        else {
            task.execute("http://1.234.38.211/login.php?id=" + sId + "&pw=" + sPw, "");
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
        String TAG_TYPE = "type";
        String TAG_CORRECTNESS = "correctness";
        String TAG_UNIV = "university";
        String TAG_EMAIL = "email";
        String TAG_NICK = "nickname";
        String TAG_SCHOOL = "school_type";
        String TAG_GRD = "grade";

        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            String type = item.getString(TAG_TYPE);
            String correctness = item.getString(TAG_CORRECTNESS);

            if(correctness.equals("true")){
                //학생인 경우
                if (type.equals("student")) {
                    String nickName = item.getString(TAG_NICK);
                    String email = item.getString(TAG_EMAIL);
                    String grade = item.getString(TAG_GRD);
                    String school = item.getString(TAG_SCHOOL);
                    SharedPreferences sf = getSharedPreferences("yumi",MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sf.edit();
                    editor.putString("id", sId);
                    editor.putString("usertype","student"); //유저타입(학생)으로 저장
                    editor.putString("nickName" , nickName);
                    editor.putString("email " , email);
                    editor.putString("school", school);
                    editor.putString("grade",grade);
                    editor.putString("pw", sPw);
                    editor.apply();

                    Toast.makeText(getApplicationContext(),"안녕하세요, " + nickName ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, StudentQuestionlist.class);
                    startActivity(intent);
                }
                //선생님인 경우
                else if (type.equals("teacher")) {
                    String nickName = item.getString(TAG_NICK);
                    String email = item.getString(TAG_EMAIL);
                    String univ = item.getString(TAG_UNIV);
                    SharedPreferences sf = getSharedPreferences("yumi",MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sf.edit();
                    editor.putString("id", sId);
                    editor.putString("usertype","teacher"); //유저타입(학생)으로 저장
                    editor.putString("nickName" , nickName);
                    editor.putString("email " , email);
                    editor.putString("university" , univ);
                    editor.putString("pw", sPw);
                    editor.apply();

                    Toast.makeText(getApplicationContext(),"안녕하세요, " + nickName + " 님" ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, TutorQuestionlist.class);
                    startActivity(intent);
                }
            }
            else if (correctness.equals("false")) {
                message = (TextView)findViewById(R.id.ifFail);
                message.setText("ID 또는 비밀번호를 확인해주세요.");
                message.setTextColor(Color.RED);
            }
            else if (correctness.equals("none")){
                message = (TextView)findViewById(R.id.ifFail);
                message.setText("가입된 정보가 없습니다. 입력 정보를 다시 확인해 주세요.");
                message.setTextColor(Color.RED);
            }

        } catch (JSONException e) {

        }

    }
}
