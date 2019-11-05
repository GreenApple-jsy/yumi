package com.example.yumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Codematching_S extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codematching__s);
    }
    //X버튼 누르면 초기 게시판 화면으로 돌아감
    public void cancel(View view){
        if(view.getId()==R.id.logo){
            Intent intent = new Intent(getApplicationContext(), TutorQuestionlist.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.validBtn2){
                /*확인 버튼을 누르면 학생이 적은 매칭코드가 선생님 테이블에 있는지 확인한 뒤 있다면
                팝업창으로 선생님 닉네임을 보여주기 구현*/
        }
    }

}
