
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
        Intent intent = new Intent(Cons.this , LoginActivity.class);
        startActivity(intent);
    }
}