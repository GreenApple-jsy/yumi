package com.example.yumi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        SharedPreferences auto = getSharedPreferences("yumi", Activity.MODE_PRIVATE);
        String loginId = auto.getString("id",null);
        String nickname = auto.getString("nickName",null);
        String position = auto.getString("usertype",null);

        if(loginId !=null && position != null && nickname != null) {
            if(position.equals("student")) {
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        startActivity(new Intent(MainActivity.this, StudentQuestionlist.class));
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 3000);
            }
            else if(position.equals("teacher")){
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        startActivity(new Intent(MainActivity.this, TutorQuestionlist.class));
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
        else {
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }
}