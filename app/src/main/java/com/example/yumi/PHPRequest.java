package com.example.yumi;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PHPRequest extends AppCompatActivity {

    public void PHPRequest(String adr){
        try {
            Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
            URL url = new URL("http://172.30.113.220/data.php");       // URL 설정
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
        }
    }

    public String PhPtest(final String data1) {
        try {
            Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
            URL url = new URL("http://127.0.0.1/data.php");       // URL 설정
            String postData = "Data1=" + data1;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = "clearly done";
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

}