package com.example.yumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Student_timeselect extends AppCompatActivity implements View.OnClickListener {
    String[] timepick = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        for(int i=0; i<100; i++){
            timepick[i] = "0";
        }

        Button complete = (Button)findViewById(R.id.complete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), uploadq.class);
                intent3.putExtra("datepick",timepick);
                startActivity(intent3);
            }
        });


    }

    public void onClick(View view) {
        for (int i = 1; i < 92; i++) {
            if (view.getId() == R.id.button+i){
                Button button = findViewById(view.getId());
                if (timepick[i] == "0") {
                    button.setBackgroundResource(R.color.colorAccent);
                    timepick[i] = "1";
                } else if (timepick[i] == "1") {
                    button.setBackgroundResource(R.color.colorPrimaryDark);
                    timepick[i] = "0";
                }
            }
        }
    }
}


