package com.example.yumi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public EditText message;

    public ListView chat_listview;
    public ArrayList <ChatData> chattings;
    public ChattingAdapter adapter;
    public String chattingRoomname;

    public static String s_myID; //ChattingAdapter에서 알기 위함 (변수값 공유)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        @NonNull
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        SharedPreferences sf = getSharedPreferences("yumi",MODE_PRIVATE);
        final String myID = sf.getString("id","null");
        final String userType = sf.getString("usertype","null");

        s_myID = myID;
        System.out.println(s_myID);

        Intent intent = getIntent(); /*데이터 수신*/
        final String oppositeID = intent.getExtras().getString("oppositeID"); //대화할 상대방 아이디 받기

        message = findViewById((R.id.editText));
        ImageButton SendMessageButton = findViewById((R.id.button2));

        chat_listview = (ListView)findViewById(R.id.chat_listview);
        chattings = new ArrayList<ChatData>() ;
        adapter = new ChattingAdapter(this, chattings);
        chat_listview.setAdapter(adapter);

        if (userType.equals("student")) //채팅방 이름은 "학생아이디_선생아이디" 타입 유지
            chattingRoomname = myID + "_" + oppositeID;
        else
            chattingRoomname = oppositeID + "_" + myID;

        showChatList();

        SendMessageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                ChatData chatData = new ChatData(myID, message.getText().toString());  // 유저 아이디와 메세지 내용으로 chatData 만들기
                databaseReference.child(chattingRoomname).push().setValue(chatData);
                message.setText((""));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.connect_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.action_loopback:{
                Intent intent = new Intent(getApplicationContext(), ConnectActivity.class);
                startActivity(intent);
                return  true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showChatList(){
        databaseReference.child(chattingRoomname).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chattings.add(chatData);
                chat_listview.setSelection(adapter.getCount() - 1); //제일 최근 메시지 위치로 자동 스크롤
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

}
