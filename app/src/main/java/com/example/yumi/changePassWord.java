package com.example.yumi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class changePassWord extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    EditText currentPW;
    EditText newPW;
    EditText newPW2;

    TextView validNow;
    TextView validNew;
    TextView validNew2;

    String origin = "";
    String current = "";
    String newpw = "";
    String newpw2 = "";
    String JsonResultString;
    String loginId="";
    String usertype = "";
    phpConnect task;
    String address = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        SharedPreferences auto = getSharedPreferences("yumi", Activity.MODE_PRIVATE);
        loginId = auto.getString("id",null);
        usertype = auto.getString("usertype", null);
        if(usertype.equals("student")){
            address="stdChangePW";
        }
        else if (usertype.equals("teacher")){
            address="tutorChangePW";
        }
    }



    class phpConnect extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String link = "http://1.234.38.211/"+address+".php?id="+loginId+"&pw="+newpw;
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
                // mTextViewResult.setText("error occurred");
            }
            else {
                JsonResultString = result;
                showResult();
            }
        }
    }

    public void showResult() {
        String TAG_JSON = "webnautes";
        String TAG_CHECK = "check";

        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            String check = item.getString(TAG_CHECK);

            if (check.equals("0")) {
                Toast.makeText(getApplicationContext(),"패스워드를 변경하지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
            if (check.equals("1")) {
                Toast.makeText(getApplicationContext(),"패스워드가 변경되었습니다. 다시 로그인해주세요", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(changePassWord.this, MenuActivity.class);
                startActivity(intent);
            }

        } catch (JSONException e) {

        }

    }



    public void changeValid(View view){

        currentPW = (EditText)findViewById(R.id.currentPwd);
        newPW = (EditText)findViewById(R.id.newPwd);
        newPW2 = (EditText)findViewById(R.id.newPwd2);


        current = currentPW.getText().toString();
        newpw = newPW.getText().toString();
        newpw2 = newPW2.getText().toString();

        validNow = (TextView)findViewById(R.id.validPwd);
        validNew = (TextView)findViewById(R.id.newPwdValid);
        validNew2 = (TextView)findViewById(R.id.reinputValid);

        //현재 패스워드가 맞는지 확인
        SharedPreferences auto = getSharedPreferences("yumi", Activity.MODE_PRIVATE);
        origin = auto.getString("pw",null);
        if(current.equals(origin)){
            validNow.setText("현재 패스워드와 일치합니다.");
            validNow.setTextColor(Color.BLUE);
            //바꾸고자 하는 패스워드가 이전 패스워드랑 같은지 확인 + 6자 넘는지
            if(current.equals(newpw)) {
                validNew.setText("현재 패스워드와 일치합니다.");
                validNew.setTextColor(Color.RED);
                validNew2.setText("");
            }
            else if (newpw.length() < 6){
                validNew.setText("6자리 이상으로 입력해 주세요.");
                validNew.setTextColor(Color.RED);
                validNew2.setText("");
            }
            else{
                validNew.setText("정상적으로 입력되었습니다.");
                validNew.setTextColor(Color.BLUE);

                //재입력한 패스워드가 같은지 확인
                if(newpw.equals(newpw2)){
                    validNew2.setText("입력하신 패스워드와 일치합니다.");
                    validNew2.setTextColor(Color.BLUE);

                    // 서버 연결
                    task = new phpConnect();
                    task.execute();
                }
                else{
                    validNew2.setText("입력하신 패스워드와 불일치합니다.");
                    validNew2.setTextColor(Color.RED);
                }
            }
        }
        else{
            validNow.setText("현재 패스워드와 일치하지 않습니다.");
            validNow.setTextColor(Color.RED);
            validNew.setText("");
            validNew2.setText("");
        }



    }
}
