package com.example.yumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Cons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);
    }
    
    public void loginToTutor(View view) {
        Toast.makeText(getApplicationContext(), "안녕하세요", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Cons.this , boardTutor.class);
        startActivity(intent);
    }
}