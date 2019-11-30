package com.example.yumi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class Mail_verify extends AppCompatActivity {

    private Button send_email_btn;
    String newCode = "TestCode";

    String emailAdr = "";
    String validCode ="";
    private static String TAG = "system";
    public int valid_num = 0;
    int univIndex=0;

    String sId, sPw,  sNickname, sUniversity, sEmail;

    String univMail[] = {"null", "khu.ac.kr", "korea.ac.kr",
            "sogang.ac.kr", "snu.ac.kr", "uos.ac.kr", "skku.ac.kr", "sookmyung.ac.kr", "yonsei.ac.kr",
            "ewha.ac.kr", "cau.ac.kr", "hufs.ac.kr", "hanyang.ac.kr"  };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_verify);

        Intent intent = getIntent();
        univIndex = (int) intent.getSerializableExtra("univ");
        sId = intent.getExtras().getString("id");  /*String형*/
        sPw = intent.getExtras().getString("pw");  /*String형*/
        sNickname = intent.getExtras().getString("nickname");
        sUniversity=intent.getExtras().getString("university");

        TextView univID = (TextView) findViewById(R.id.univVRF);
        univID.setText("@"+univMail[univIndex]);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());


    }


    public void sendEmail(View view) {

        EditText editText = (EditText)findViewById(R.id.emailTexts); //받는 사람의 이메일
        emailAdr = editText.getText().toString()+"@"+univMail[univIndex];
        GMailSender sender = new GMailSender("", ""); // SUBSTITUTE

        if (android.os.Build.VERSION.SDK_INT > 9)
        {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }
        // HERE
        try
        {

            System.out.println("!!!!! email "  + emailAdr );
            sender.sendMail("익선생 인증 코드를 확인해주세요.", // subject.getText().toString(),
                    "인증코드를 입력해주세요 : " + createEmailCode(), // body.getText().toString(),
                    emailAdr, // from.getText().toString(),
                    emailAdr // to.getText().toString()
            );
            Toast.makeText(this, "전송되었습니다.", Toast.LENGTH_SHORT).show();

        }  catch (SendFailedException e) {
            Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "예외발생", Toast.LENGTH_SHORT).show();
        }

    }
    private String createEmailCode() { //이메일 인증코드 생성
        newCode = "";
        String[] str = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "!","@","#","$","%","^","&","*"};


        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }



    public void validation(View view){

        EditText validText = (EditText)findViewById(R.id.validTexts); //받는 사람의 이메일
        validCode = validText.getText().toString();
        if (validCode.equals(newCode)){
            valid_num=1;
            Toast.makeText(getApplicationContext(), "본인 인증이 되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            valid_num = 0;
            Toast.makeText(getApplicationContext(), "인증코드가 불일치합니다.", Toast.LENGTH_SHORT).show();
        }
    }


    public void afterValid(View view) {

        if(valid_num != 0 ){
            sEmail = emailAdr;
            System.out.println("!!!! after valid " + sEmail +  " " + emailAdr);
            System.out.println("----------> " + sId+ " " +sPw+ " "+sNickname+" " +sUniversity+ " " +sEmail);
            TTRegisterDB rdb = new TTRegisterDB();
            rdb.execute();
            Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            Intent valid = new Intent(Mail_verify.this , Cons.class);
            startActivity(valid);
        }
        else {
            Toast.makeText(getApplicationContext(), "본인 인증을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
        }

    }



    public class TTRegisterDB extends AsyncTask<Void, Integer, String> {

        ProgressDialog progressDialog;


        @Override
        protected String doInBackground(Void... unused) {

            String param = "id=" + sId + "&pw=" + sPw +"&email="+sEmail + "&nickname=" + sNickname+"&university=" + sUniversity;
            System.out.println("!!!!! >" + param);
            /* 인풋 파라메터값 생성 */
            try {
                /* 서버연결 */
                URL url = new URL("http://1.234.38.211/teacher_signup.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터값 전달 */
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
}
