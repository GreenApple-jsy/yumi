package com.example.yumi;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.Calendar;


//전환 할 Activity
public class MenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("Log in & Sign up");
        setContentView(R.layout.menu_ui);

        ///////////////////////////////PUSH ALARM///////////////////////////
        //1. 로그인 기능 완벽히 구현되면 m..enuActivity가 아닌 선생님 페이지에만 나타나도록 제작
        //2. 과거 시간 입력시 무조건 나타남 -> 따라서 실행한 시각+1분이 되는 순간 푸시가 뜨도록 설정 -> 나중에 수정
        //3. 디비 연동되면 선생님 question 테이블에서 reservation=1인 행들만 갖고 와서 그 시간에 맞게 알람 구현
        Calendar Time = Calendar.getInstance();
        System.out.println(" now is " + Time.get(Calendar.HOUR) +":"+Time.get(Calendar.MINUTE));
        Time.set(Calendar.HOUR, Time.get(Calendar.HOUR));
        Time.set(Calendar.MINUTE,Time.get(Calendar.MINUTE)+1);
        Time.set(Calendar.SECOND,0);
        Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this,AlarmReceiver.class);
        PendingIntent ServicePending = PendingIntent.getBroadcast(MenuActivity.this, 0, intent, 0);
        AlarmManager alarmManager;
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,Time.getTimeInMillis(),ServicePending);

        Toast.makeText(getApplicationContext(),"알람 설정 완료"+Time.getTime(),Toast.LENGTH_LONG).show();
        //////////////////////////////////////////////////////////////////////


    }


    public void onClick(View view) {
        switch(view.getId()){
            case R.id.angry_btn:
                //학생으로 가입하기 눌렀을 때
                Intent intent = new Intent(getApplicationContext(), Studentsignup.class);
                startActivity(intent);
                break;
            case R.id.angry_btn2:
                //선생님으로 가입하기 눌렀을 때
                Intent intent2 = new Intent(getApplicationContext(), Teachersignup.class);
                startActivity(intent2);
                break;
            case R.id.angry_btn3:
                //로그인하기 눌렀을 때
                Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent3);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
    @Override public void onBackPressed() { }
}
