package com.example.yumi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Privateinformation2 extends AppCompatActivity {

    EditText et_nickname, et_email;
    RadioGroup et_schooltype; RadioButton rd;
    String sId, sPw,sNick, sEmail, sSchool, sGrade;
    String JsonResultString;
    Privateinformation2.GetData task;
    EditText et_nicknames;
    String nickCheck="1";
    Privateinformation2.GetData1 task1;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("회원정보 입력하기");
        setContentView(R.layout.activity_privateinformation_student);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


        Button nickname_check = (Button) findViewById(R.id.id_check3);
        nickname_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //닉네임 중복 체크
                //0-> ok,1->not ok
                et_nicknames = (EditText) findViewById(R.id.nick_text);
                String nickname = et_nicknames.getText().toString();
                if(nickname.length()> 6){
                    Toast.makeText(getApplicationContext(), "6자리 이하로 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    task = new Privateinformation2.GetData();
                    task.execute("http://1.234.38.211/nickname_check.php?nickname=" + nickname, "");
                }
            }
        });

        //체크박스 학년 구분
        final Spinner dropdown = (Spinner) findViewById(R.id.spinner2);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position != 0) {
                    sGrade = (String) parent.getItemAtPosition(position).toString(); //String 입력받음.
                    Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + "을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });



        //이전 페이지에서 입력한 id,pw 데이터 받음.
        Intent intent = getIntent(); /*데이터 수신*/
        sId = intent.getExtras().getString("id");  /*String형*/
        sPw = intent.getExtras().getString("pw");  /*String형*/

    }

    //회원가입이 완료되면 로그인 화면으로 넘어가기
    public void complete(View view){

        //텍스트 입력 창에 입력된 스트링 받아오기
        et_nickname = (EditText) findViewById(R.id.nick_text);
        et_schooltype = (RadioGroup) findViewById(R.id.radioGroup2);  //라디오그룹에서
        rd = (RadioButton) findViewById(et_schooltype.getCheckedRadioButtonId()); //선택된 걸 받음.
        et_email = (EditText) findViewById(R.id.email_text);

        if(view.getId()==R.id.complete) {

            sNick = et_nickname.getText().toString();  //닉네임 값 받음.
            sSchool = rd.getText().toString();  //선택된 학교 값(스트링 : 고등학교 / 중학교)을 받음.
            sEmail = et_email.getText().toString(); //이메일 주소 받음.

            if((nickCheck.equals("0"))&&(sNick.length()>0) && (sSchool.length()>0) && (sEmail.length()>0)){
                RegisterDB rdb = new RegisterDB();
                rdb.execute();

                //다음 액티비티로 넘어감.
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
            else if(nickCheck.equals("1")){
                Toast.makeText(Privateinformation2.this, "" +
                        "닉네임 중복 확인 해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //앱바 메뉴 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // User chose the "Settings" item, show the app settings UI...
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class RegisterDB extends AsyncTask<Void, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Privateinformation2.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "id=" + sId + "&pw=" + sPw +"&nickname=" + sNick+ "&email="+sEmail + "&school_type="+sSchool +"&grade="+sGrade+"";
            try {
                /* 서버연결 */
                URL url = new URL("http://1.234.38.211/student_signup.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                int responseStatusCode = conn.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream =conn.getInputStream();
                }
                else{
                    inputStream = conn.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }
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
            nickCheck = item.getString(TAG_CHECK);

            if (nickCheck.equals("1")) {
                Toast.makeText(getApplicationContext(),"중복된 닉네임이 존재합니다.", Toast.LENGTH_SHORT).show();
            }
            if (nickCheck.equals("0")) {
                Toast.makeText(getApplicationContext(),"가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {

        }

    }
    private class GetData1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                JsonResultString = result;
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
    }

    public void InitializeQuestionData1() {
        String TAG_JSON = "webnautes";
        String TAG_CHECK = "check";

        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            String check = item.getString(TAG_CHECK);

            if (check.equals("1")) {
                flag = 1;
            }
            if (check.equals("0")) {
                flag = 2;
            }

        } catch (JSONException e) {
        }
    }
}