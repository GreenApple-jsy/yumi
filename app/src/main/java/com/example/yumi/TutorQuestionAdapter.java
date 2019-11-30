package com.example.yumi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

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
        TextView tv_book= view.findViewById(R.id.book);
        TextView tv_page= view.findViewById(R.id.page);
        TextView tv_qnum= view.findViewById(R.id.q_number);
        TextView tv_chp = view.findViewById(R.id.chapter);
        TextView tv_dates= view.findViewById(R.id.dates);
        TextView tv_school = view.findViewById(R.id.school_type);
        TextView tv_sid = view.findViewById(R.id.sid);

        tv_sid.setText(Questions.get(position).getNickname()+ " 학생");
        tv_dates.setText("질문한 날짜 : " + Questions.get(position).getDates());
        tv_school.setText(Questions.get(position).getSchool());
        tv_chp.setText(Questions.get(position).getCHP());
        tv_age.setText(Questions.get(position).getage());
        tv_book.setText("교재 : "+Questions.get(position).getbook());
        tv_page.setText(Questions.get(position).getpage() + " 페이지");
        tv_qnum.setText(Questions.get(position).getqnumber() + "번");


        return view;
    }


}