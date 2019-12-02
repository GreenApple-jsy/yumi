package com.example.yumi;

public class QuestionData {
    private int id;
    private String book;
    private String page;
    private String q_number;
    private String start_time;
    private String q_image;
    private String t_id;
    private String s_id;
    private int complete;
    private String q_link;
    private String age;
    private String semester;
    private int reservation;
    private String school_type;
    private String dates;
    private String chapter;
    private String nickname;

    public QuestionData(int id, String book, String page, String q_number, String start_time,
                        String q_image, String t_id, String s_id, int complete, String q_link,
                        String age, String semester, int reservation , String school_type,
                        String chapter, String dates, String nickname){
        this.id = id;
        this.book = book;
        this.page = page;
        this.q_number = q_number;
        this.start_time = start_time;
        this.q_image = q_image;
        this.t_id = t_id;
        this.s_id = s_id;
        this.complete = complete;
        this.q_link = q_link;
        this.age = age;
        this.semester = semester;
        this.reservation = reservation;
        this.school_type=school_type;
        this.chapter=chapter;
        this.dates=dates;
        this.nickname=nickname;
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

    public String getSchool()
    {
        return this.school_type;
    }
    public String getCHP()
    {
        return this.chapter;
    }
    public String getDates()
    {
        return this.dates;
    }
    public String getNickname(){return this.nickname;}
}