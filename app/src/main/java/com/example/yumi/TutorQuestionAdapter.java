package com.example.yumi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TutorQuestionAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<QuestionData> Questions;

    public TutorQuestionAdapter(Context context, ArrayList<QuestionData> data) {
        mContext = context;
        Questions = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return Questions.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public QuestionData getItem(int position) {
        return Questions.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.q_listformat, null);

        System.out.println(Questions.get(position).getimage());
        TextView tv_age = view.findViewById(R.id.age);
        TextView tv_semester = view.findViewById(R.id.semester);
        TextView tv_book= view.findViewById(R.id.book);
        TextView tv_page= view.findViewById(R.id.page);
        TextView tv_qnum= view.findViewById(R.id.q_number);
        TextView tv_stime= view.findViewById(R.id.start_time);
        TextView tv_etime= view.findViewById(R.id.end_time);
        TextView tv_reserv= view.findViewById(R.id.reservation);
        TextView tv_complete= view.findViewById(R.id.complete);
        TextView tv_good= view.findViewById(R.id.good);

        tv_age.setText(Questions.get(position).getage());
        tv_semester.setText(Questions.get(position).getsemester());
        tv_book.setText("교재 : " + Questions.get(position).getbook());
        tv_page.setText(Questions.get(position).getpage() + "pg");
        tv_qnum.setText(Questions.get(position).getqnumber() + "번");
        tv_stime.setText("풀이 가능 시간 : " +Questions.get(position).getstime());
        tv_etime.setText(" ~ " + Questions.get(position).getetime());
        if(Questions.get(position).getreservation() == 1)
            tv_reserv.setText("풀이 예약 완료");
        else
            tv_reserv.setText("대기 중");

        if(Questions.get(position).getcomplete() == 1)
            tv_complete.setText("풀이 완료");
        else
            tv_complete.setText(" ");

        tv_good.setText(Integer.toString(Questions.get(position).getgood()) + " 추천");

        return view;
    }


}