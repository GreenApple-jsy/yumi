package com.example.yumi;

public class QuestionData {
    private int id;
    private String book;
    private String page;
    private String q_number;
    private String start_time;
    private String end_time;
    private String q_image;
    private String t_id;
    private String s_id;
    private int complete;
    private int good;
    private String q_link;
    private String age;
    private String semester;
    private int reservation;

    public QuestionData(int id, String book, String page, String q_number, String start_time, String end_time, String q_image, String t_id, String s_id, int complete, int good, String q_link, String age, String semester, int reservation){
        this.id = id;
        this.book = book;
        this.page = page;
        this.q_number = q_number;
        this.start_time = start_time;
        this.end_time = end_time;
        this.q_image = q_image;
        this.t_id = t_id;
        this.s_id = s_id;
        this.complete = complete;
        this.good = good;
        this.q_link = q_link;
        this.age = age;
        this.semester = semester;
        this.reservation = reservation;
    }

    public int getid()
    {
        return this.id;
    }

    public String getbook()
    {
        return this.book;
    }
    public String getpage()
    {
        return this.page;
    }
    public String getqnumber()
    {
        return this.q_number;
    }
    public String getstime()
    {
        return this.start_time;
    }
    public String getetime()
    {
        return this.end_time;
    }
    public String getimage()
    {
        return ("http://1.234.38.211/uploadimage/" + this.q_image + ".jpg");
    }
    public String gettid()
    {
        return this.t_id;
    }
    public String getsid()
    {
        return this.s_id;
    }
    public int getcomplete()
    {
        return this.complete;
    }
    public int getgood()
    {
        return this.good;
    }
    public String getlink()
    {
        return this.q_link;
    }
    public String getage()
    {
        return this.age;
    }
    public String getsemester()
    {
        return this.semester;
    }
    public int getreservation()
    {
        return this.reservation;
    }
}