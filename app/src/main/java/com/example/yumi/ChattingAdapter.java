package com.example.yumi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ChattingAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ChatData> Chattings;
    final String myID = ChattingActivity.s_myID;



    public ChattingAdapter(Context context, ArrayList<ChatData> data) {
        mContext = context;
        Chattings = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return Chattings.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ChatData getItem(int position) {
        return Chattings.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        System.out.println("받은값" + myID);
        View view = mLayoutInflater.inflate(R.layout.chatting_item, null);
        TextView message = view.findViewById(R.id.message);
        message.setText(Chattings.get(position).getMessage());

        message.setBackground(message.getResources().getDrawable(Chattings.get(position).getUserID().equals(myID)? R.drawable.mybubble:R.drawable.yourbubble));

        if (Chattings.get(position).getUserID().equals(myID)){ //내가 쓴 메시지일 경우, 말풍선 색깔바꿔주기 위함 ★★★★★★
            //message.setBackgroundColor(Color.rgb(183,231,255));
            message.setGravity(Gravity.RIGHT);
        }
        else{ //상대방이 쓴 메시지
            //message.setBackgroundColor(Color.rgb(194,247,196));
            message.setGravity(Gravity.LEFT);
        }
        return view;
    }
}
