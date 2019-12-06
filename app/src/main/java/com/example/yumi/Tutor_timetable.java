package com.example.yumi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class Tutor_timetable extends AppCompatActivity{
    char[] timepick = new char[100];
    String temp;
    String JsonResultString;
    int flag = 0;
    String weekDay,year,month,day;
    int daynumber;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25,b26,
    b27,b28,b29,b30,b31,b32,b33,b34,b35,b36,b37,b38,b39,b40,b41,b42,b43,b44,b45,b46,b47,b48,b49,b50,b51,
    b52,b53,b54,b55,b56,b57,b58,b59,b60,b61,b62,b63,b64,b65,b66,b67,b68,b69,b70,b71,b72,b73,b74,b75,b76,b77,
    b78,b79,b80,b81,b82,b83,b84,b85,b86,b87,b88,b89,b90,b91;
    int realday=0;
    String time="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_timetable);

        Intent intent = getIntent(); /*데이터 수신*/
        final int question_id = intent.getExtras().getInt("question_id");
        b1= findViewById(R.id.button1);b2= findViewById(R.id.button2);b3= findViewById(R.id.button3);b4= findViewById(R.id.button4);
        b5= findViewById(R.id.button5);b6= findViewById(R.id.button6);b7= findViewById(R.id.button7);b8= findViewById(R.id.button8);b9= findViewById(R.id.button9);
        b10= findViewById(R.id.button10);b11= findViewById(R.id.button11);b12= findViewById(R.id.button12);b13= findViewById(R.id.button13);b14= findViewById(R.id.button14);
        b15= findViewById(R.id.button15);b16= findViewById(R.id.button16);b17= findViewById(R.id.button17);b18= findViewById(R.id.button18);b19= findViewById(R.id.button19);
        b20= findViewById(R.id.button20);b21= findViewById(R.id.button21);b22= findViewById(R.id.button22);b23= findViewById(R.id.button23);b24= findViewById(R.id.button24);
        b25= findViewById(R.id.button25);b26= findViewById(R.id.button26);b27= findViewById(R.id.button27);
        b28= findViewById(R.id.button28);b29= findViewById(R.id.button29);b30= findViewById(R.id.button30);b31= findViewById(R.id.button31);b32= findViewById(R.id.button32);b33= findViewById(R.id.button33);
        b34= findViewById(R.id.button34);b35= findViewById(R.id.button35);b36= findViewById(R.id.button36);b37= findViewById(R.id.button37);b38= findViewById(R.id.button38);b39= findViewById(R.id.button39);b40= findViewById(R.id.button40);b41= findViewById(R.id.button41);b42= findViewById(R.id.button42);b43= findViewById(R.id.button43);
        b44= findViewById(R.id.button44);b45= findViewById(R.id.button45);b46= findViewById(R.id.button46);b47= findViewById(R.id.button47);b48= findViewById(R.id.button48);b49= findViewById(R.id.button49);b50= findViewById(R.id.button50);b51= findViewById(R.id.button51);b52= findViewById(R.id.button52);b53= findViewById(R.id.button53);b54= findViewById(R.id.button54);
        b55= findViewById(R.id.button55);b56= findViewById(R.id.button56);b57= findViewById(R.id.button57);b58= findViewById(R.id.button58);b59= findViewById(R.id.button59);
        b60= findViewById(R.id.button60);b61= findViewById(R.id.button61);b62= findViewById(R.id.button62);
        b63= findViewById(R.id.button63);b64= findViewById(R.id.button64);b65= findViewById(R.id.button65);b66= findViewById(R.id.button66);b67= findViewById(R.id.button67);b68= findViewById(R.id.button68);b69= findViewById(R.id.button69);
        b70= findViewById(R.id.button70);b71= findViewById(R.id.button71);b72= findViewById(R.id.button72);b73= findViewById(R.id.button73);b74= findViewById(R.id.button74);b75= findViewById(R.id.button75);b76= findViewById(R.id.button76);b77= findViewById(R.id.button77);b78= findViewById(R.id.button78);
        b79= findViewById(R.id.button79);b80= findViewById(R.id.button80);b81= findViewById(R.id.button81);b82= findViewById(R.id.button82);
        b83= findViewById(R.id.button83);b84= findViewById(R.id.button84);b85= findViewById(R.id.button85);b86= findViewById(R.id.button86);
        b87= findViewById(R.id.button87);b88= findViewById(R.id.button88);b89= findViewById(R.id.button89);b90= findViewById(R.id.button90);b91= findViewById(R.id.button91);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String days = dayFormat.format(currentTime);
        if(weekDay.equals("Mon")){
            daynumber = 1;
            realday=1;
        }
        if(weekDay.equals("Tue")){
            Toast.makeText(getApplicationContext(),weekDay,Toast.LENGTH_SHORT).show();
            daynumber = 2;
            realday=2;
        }
        if(weekDay.equals("Wed")){
            daynumber = 3;
            realday=3;
        }
        if(weekDay.equals("Thu")){
            daynumber = 4;
            realday=4;
        }
        if(weekDay.equals("Fri")){
            daynumber = 5;
            realday=5;
        }
        if(weekDay.equals("Sat")){
            daynumber = 6;
            realday=6;
        }
        if(weekDay.equals("Sun")){
            daynumber = 7;
            realday=7;
        }
        Button complete = (Button) findViewById(R.id.complete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day= "월";
                if(daynumber == 1){
                    day = "월";
                }else if(daynumber== 2){
                    day = "화";
                }
                else if(daynumber== 3){
                    day = "수";
                }
                else if(daynumber== 4){
                    day = "목";
                }
                else if(daynumber== 5){
                    day = "금";
                }
                else if(daynumber== 6){
                    day = "토";
                }
                else if(daynumber== 7){
                    day = "일";
                }
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                String year = yearFormat.format(currentTime);
                String month = monthFormat.format(currentTime);
                String days = dayFormat.format(currentTime);
                if(realday < daynumber){
                    days = Integer.toString(Integer.parseInt(days) + (daynumber-realday));
                }
                else if(realday > daynumber){
                    days = Integer.toString(Integer.parseInt(days) + 7-(realday-daynumber));
                }
                if( (Integer.parseInt(month) == 1 ||Integer.parseInt(month) == 3 || Integer.parseInt(month) == 5 || Integer.parseInt(month) == 7 || Integer.parseInt(month) == 8 || Integer.parseInt(month) == 10|| Integer.parseInt(month) == 12 ) && Integer.parseInt(days) > 31){
                    int num = Integer.parseInt(days) - 31;
                    days = Integer.toString(num);
                    month = Integer.toString(Integer.parseInt(month) +1);
                }
                if( (Integer.parseInt(month) == 4 ||Integer.parseInt(month) == 6 || Integer.parseInt(month) == 9 || Integer.parseInt(month) == 11 ) && Integer.parseInt(days) > 30){
                    int num = Integer.parseInt(days) - 30;
                    days = Integer.toString(num);
                    month = Integer.toString(Integer.parseInt(month) +1);
                }
                if( (Integer.parseInt(month) == 2 ) && Integer.parseInt(days) > 28){
                    int num = Integer.parseInt(days) - 28;
                    days = Integer.toString(num);
                    month = Integer.toString(Integer.parseInt(month) +1);
                }
                String date = year + "년" + month + "월" + days + "일" + day + "요일" + time;
                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), Tutor_reservation.class);
                intent3.putExtra("datepick", date);
                intent3.putExtra("question_id", question_id);
                startActivity(intent3);
            }
        });

        GetData task = new GetData();
        task.execute("http://1.234.38.211/getQuestionTime.php?id=" + Integer.toString(question_id), "");
    }

    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                JsonResultString = result;
                InitializeQuestionData();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                return null;
            }
        }
    }

    public void InitializeQuestionData() {
        String TAG_JSON = "webnautes";
        String TAG_TIME = "timetable";

        try {
            JSONObject jsonObject = new JSONObject(JsonResultString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            JSONObject item = jsonArray.getJSONObject(0);
            temp = item.getString(TAG_TIME);

            timepick = temp.toCharArray();
            if(timepick[2] == '0') b1.setBackgroundResource(R.drawable.image_dash2);else if(timepick[2] == '1') b1.setBackgroundResource(R.color.colorAccent);
            if(timepick[3] == '0') b2 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[3] == '1') b2.setBackgroundResource(R.color.colorAccent);
            if(timepick[4] == '0') b3 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[4] == '1') b3.setBackgroundResource(R.color.colorAccent);
            if(timepick[5] == '0') b4 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[5] == '1') b4.setBackgroundResource(R.color.colorAccent);
            if(timepick[6] == '0') b5 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[6] == '1') b5.setBackgroundResource(R.color.colorAccent);
            if(timepick[7] == '0') b6 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[7] == '1') b6.setBackgroundResource(R.color.colorAccent);
            if(timepick[8] == '0') b7 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[8] == '1') b7.setBackgroundResource(R.color.colorAccent);
            if(timepick[9] == '0') b8 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[9] == '1') b8.setBackgroundResource(R.color.colorAccent);
            if(timepick[10] == '0') b9 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[10] == '1') b9.setBackgroundResource(R.color.colorAccent);
            if(timepick[11] == '0') b10 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[11] == '1') b10.setBackgroundResource(R.color.colorAccent);
            if(timepick[12] == '0') b11 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[12] == '1') b11.setBackgroundResource(R.color.colorAccent);
            if(timepick[13] == '0') b12 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[13] == '1') b12.setBackgroundResource(R.color.colorAccent);
            if(timepick[14] == '0') b13 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[14] == '1') b13.setBackgroundResource(R.color.colorAccent);
            if(timepick[15] == '0') b14 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[15] == '1') b14.setBackgroundResource(R.color.colorAccent);
            if(timepick[16] == '0') b15 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[16] == '1') b15.setBackgroundResource(R.color.colorAccent);
            if(timepick[17] == '0') b16 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[17] == '1') b16.setBackgroundResource(R.color.colorAccent);
            if(timepick[18] == '0') b17 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[18] == '1') b17.setBackgroundResource(R.color.colorAccent);
            if(timepick[19] == '0') b18 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[19] == '1') b18.setBackgroundResource(R.color.colorAccent);
            if(timepick[20] == '0') b19 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[20] == '1') b19.setBackgroundResource(R.color.colorAccent);
            if(timepick[21] == '0') b20 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[21] == '1') b20.setBackgroundResource(R.color.colorAccent);
            if(timepick[22] == '0') b21 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[22] == '1') b21.setBackgroundResource(R.color.colorAccent);
            if(timepick[23] == '0') b22 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[23] == '1') b22.setBackgroundResource(R.color.colorAccent);
            if(timepick[24] == '0') b23 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[24] == '1') b23.setBackgroundResource(R.color.colorAccent);
            if(timepick[25] == '0') b24 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[25] == '1') b24.setBackgroundResource(R.color.colorAccent);
            if(timepick[26] == '0') b25 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[26] == '1') b25.setBackgroundResource(R.color.colorAccent);
            if(timepick[27] == '0') b26 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[27] == '1') b26.setBackgroundResource(R.color.colorAccent);
            if(timepick[28] == '0') b27 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[28] == '1') b27.setBackgroundResource(R.color.colorAccent);
            if(timepick[29] == '0') b28 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[29] == '1') b28.setBackgroundResource(R.color.colorAccent);
            if(timepick[30] == '0') b29 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[30] == '1') b29.setBackgroundResource(R.color.colorAccent);
            if(timepick[31] == '0') b30 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[31] == '1') b30.setBackgroundResource(R.color.colorAccent);
            if(timepick[32] == '0') b31 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[32] == '1') b31.setBackgroundResource(R.color.colorAccent);
            if(timepick[33] == '0') b32 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[33] == '1') b32.setBackgroundResource(R.color.colorAccent);
            if(timepick[34] == '0') b33 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[34] == '1') b33.setBackgroundResource(R.color.colorAccent);
            if(timepick[35] == '0') b34 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[35] == '1') b34.setBackgroundResource(R.color.colorAccent);
            if(timepick[36] == '0') b35 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[36] == '1') b35.setBackgroundResource(R.color.colorAccent);
            if(timepick[37] == '0') b36 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[37] == '1') b36.setBackgroundResource(R.color.colorAccent);
            if(timepick[38] == '0') b37 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[38] == '1') b37.setBackgroundResource(R.color.colorAccent);
            if(timepick[39] == '0') b38 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[39] == '1') b38.setBackgroundResource(R.color.colorAccent);
            if(timepick[40] == '0') b39 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[40] == '1') b39.setBackgroundResource(R.color.colorAccent);
            if(timepick[41] == '0') b40 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[41] == '1') b40.setBackgroundResource(R.color.colorAccent);
            if(timepick[42] == '0') b41 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[42] == '1') b41.setBackgroundResource(R.color.colorAccent);
            if(timepick[43] == '0') b42 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[43] == '1') b42.setBackgroundResource(R.color.colorAccent);
            if(timepick[44] == '0') b43 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[44] == '1') b43.setBackgroundResource(R.color.colorAccent);
            if(timepick[45] == '0') b44 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[45] == '1') b44.setBackgroundResource(R.color.colorAccent);
            if(timepick[46] == '0') b45 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[46] == '1') b45.setBackgroundResource(R.color.colorAccent);
            if(timepick[47] == '0') b46 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[47] == '1') b46.setBackgroundResource(R.color.colorAccent);
            if(timepick[48] == '0') b47 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[48] == '1') b47.setBackgroundResource(R.color.colorAccent);
            if(timepick[49] == '0') b48 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[49] == '1') b48.setBackgroundResource(R.color.colorAccent);
            if(timepick[50] == '0') b49 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[50] == '1') b49.setBackgroundResource(R.color.colorAccent);
            if(timepick[51] == '0') b50 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[51] == '1') b50.setBackgroundResource(R.color.colorAccent);
            if(timepick[52] == '0') b51 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[52] == '1') b51.setBackgroundResource(R.color.colorAccent);
            if(timepick[53] == '0') b52 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[53] == '1') b52.setBackgroundResource(R.color.colorAccent);
            if(timepick[54] == '0') b53 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[54] == '1') b53.setBackgroundResource(R.color.colorAccent);
            if(timepick[55] == '0') b54 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[55] == '1') b54.setBackgroundResource(R.color.colorAccent);
            if(timepick[56] == '0') b55 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[56] == '1') b55.setBackgroundResource(R.color.colorAccent);
            if(timepick[57] == '0') b56 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[57] == '1') b56.setBackgroundResource(R.color.colorAccent);
            if(timepick[58] == '0') b57 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[58] == '1') b57.setBackgroundResource(R.color.colorAccent);
            if(timepick[59] == '0') b58 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[59] == '1') b58.setBackgroundResource(R.color.colorAccent);
            if(timepick[60] == '0') b59 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[60] == '1') b59.setBackgroundResource(R.color.colorAccent);
            if(timepick[61] == '0') b60 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[61] == '1') b60.setBackgroundResource(R.color.colorAccent);
            if(timepick[62] == '0') b61 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[62] == '1') b61.setBackgroundResource(R.color.colorAccent);
            if(timepick[63] == '0') b62 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[63] == '1') b62.setBackgroundResource(R.color.colorAccent);
            if(timepick[64] == '0') b63 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[64] == '1') b63.setBackgroundResource(R.color.colorAccent);
            if(timepick[65] == '0') b64 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[65] == '1') b64.setBackgroundResource(R.color.colorAccent);
            if(timepick[66] == '0') b65 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[66] == '1') b65.setBackgroundResource(R.color.colorAccent);
            if(timepick[67] == '0') b66 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[67] == '1') b66.setBackgroundResource(R.color.colorAccent);
            if(timepick[68] == '0') b67 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[68] == '1') b67.setBackgroundResource(R.color.colorAccent);
            if(timepick[69] == '0') b68 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[69] == '1') b68.setBackgroundResource(R.color.colorAccent);
            if(timepick[70] == '0') b69 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[70] == '1') b69.setBackgroundResource(R.color.colorAccent);
            if(timepick[71] == '0') b70 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[71] == '1') b70.setBackgroundResource(R.color.colorAccent);
            if(timepick[72] == '0') b71 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[72] == '1') b71.setBackgroundResource(R.color.colorAccent);
            if(timepick[73] == '0') b72 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[73] == '1') b72.setBackgroundResource(R.color.colorAccent);
            if(timepick[74] == '0') b73 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[74] == '1') b73.setBackgroundResource(R.color.colorAccent);
            if(timepick[75] == '0') b74 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[75] == '1') b74.setBackgroundResource(R.color.colorAccent);
            if(timepick[76] == '0') b75 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[76] == '1') b75.setBackgroundResource(R.color.colorAccent);
            if(timepick[77] == '0') b76 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[77] == '1') b76.setBackgroundResource(R.color.colorAccent);
            if(timepick[78] == '0') b77 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[78] == '1') b77.setBackgroundResource(R.color.colorAccent);
            if(timepick[79] == '0') b78 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[79] == '1') b78.setBackgroundResource(R.color.colorAccent);
            if(timepick[80] == '0') b78 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[80] == '1') b79.setBackgroundResource(R.color.colorAccent);
            if(timepick[81] == '0') b80 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[81] == '1') b80.setBackgroundResource(R.color.colorAccent);
            if(timepick[82] == '0') b81 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[82] == '1') b81.setBackgroundResource(R.color.colorAccent);
            if(timepick[83] == '0') b82 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[83] == '1') b82.setBackgroundResource(R.color.colorAccent);
            if(timepick[84] == '0') b83 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[84] == '1') b83.setBackgroundResource(R.color.colorAccent);
            if(timepick[85] == '0') b84 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[85] == '1') b84.setBackgroundResource(R.color.colorAccent);
            if(timepick[86] == '0') b85 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[86] == '1') b85.setBackgroundResource(R.color.colorAccent);
            if(timepick[87] == '0') b86 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[87] == '1') b86.setBackgroundResource(R.color.colorAccent);
            if(timepick[88] == '0') b87 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[88] == '1') b87.setBackgroundResource(R.color.colorAccent);
            if(timepick[89] == '0') b88 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[89] == '1') b88.setBackgroundResource(R.color.colorAccent);
            if(timepick[90] == '0') b89 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[90] == '1') b89.setBackgroundResource(R.color.colorAccent);
            if(timepick[91] == '0') b90 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[91] == '1') b90.setBackgroundResource(R.color.colorAccent);
            if(timepick[92] == '0') b91 .setBackgroundResource(R.drawable.image_dash2);else if(timepick[92] == '1') b91.setBackgroundResource(R.color.colorAccent);

            flag= 1;
        } catch (JSONException e) {
        }
    }

    public void onClick1(View view) {
        daynumber = 1;
        time = "12시";
    }
    public void onClick2(View view) {
        daynumber = 2;
        time = "12시";
    }
    public void onClick3(View view) {
        daynumber = 3;
        time = "12시";
    }
    public void onClick4(View view) {
        daynumber = 4;
        time = "12시";
    }
    public void onClick5(View view) {
        daynumber = 5;
        time = "12시";
    }
    public void onClick6(View view) {
        daynumber = 6;
        time = "12시";
    }
    public void onClick7(View view) {
        daynumber = 7;
        time = "12시";
    }
    public void onClick8(View view) {
        daynumber = 1;
        time = "12시";
    }
    public void onClick9(View view) {
        daynumber = 2;
        time = "1시";
    }
    public void onClick10(View view) {
        daynumber = 3;
        time = "1시";
    }
    public void onClick11(View view) {
        daynumber = 4;
        time = "1시";
    }
    public void onClick12(View view) {
        daynumber = 5;
        time = "1시";
    }
    public void onClick13(View view) {
        daynumber = 6;
        time = "1시";
    }
    public void onClick14(View view) {
        daynumber = 7;
        time = "1시";
    }
    public void onClick15(View view) {
        daynumber = 1;
        time = "2시";
    }
    public void onClick16(View view) {
        daynumber = 2;
        time = "2시";
    }
    public void onClick17(View view) {
        daynumber = 3;
        time = "2시";
    }
    public void onClick18(View view) {
        daynumber = 4;
        time = "2시";
    }
    public void onClick19(View view) {
        daynumber = 5;
        time = "2시";
    }
    public void onClick20(View view) {
        daynumber = 6;
        time = "2시";
    }
    public void onClick21(View view) {
        daynumber = 7;
        time = "2시";
    }
    public void onClick22(View view) {
        daynumber = 1;
        time = "3시";
    }
    public void onClick23(View view) {
        daynumber = 2;
        time = "3시";
    }
    public void onClick24(View view) {
        daynumber = 3;
        time = "3시";
    }
    public void onClick25(View view) {
        daynumber = 4;
        time = "3시";
    }
    public void onClick26(View view) {
        daynumber = 5;
        time = "3시";
    }
    public void onClick27(View view) {
        daynumber = 6;
        time = "3시";
    }
    public void onClick28(View view) {
        daynumber = 7;
        time = "3시";
    }
    public void onClick29(View view) {
        daynumber = 1;
        time = "4시";
    }
    public void onClick30(View view) {
        daynumber = 2;
        time = "4시";
    }public void onClick31(View view) {
        daynumber = 3;
        time = "4시";
    }
    public void onClick32(View view) {
        daynumber = 4;
        time = "4시";
    }
    public void onClick33(View view) {
        daynumber = 5;
        time = "4시";
    }
    public void onClick34(View view) {
        daynumber = 6;
        time = "4시";
    }
    public void onClick35(View view) {
        daynumber = 7;
        time = "4시";
    }
    public void onClick36(View view) {
        daynumber = 1;
        time = "5시";
    }
    public void onClick37(View view) {
        daynumber = 2;
        time = "5시";
    }
    public void onClick38(View view) {
        daynumber = 3;
        time = "5시";
    }
    public void onClick39(View view) {
        daynumber = 4;
        time = "5시";
    }
    public void onClick40(View view) {
        daynumber = 5;
        time = "5시";
    }
    public void onClick41(View view) {
        daynumber = 6;
        time = "5시";
    }
    public void onClick42(View view) {
        daynumber = 7;
        time = "5시";
    }
    public void onClick43(View view) {
        daynumber = 1;
        time = "6시";
    }
    public void onClick44(View view) {
        daynumber = 2;
        time = "6시";
    }
    public void onClick45(View view) {
        daynumber = 3;
        time = "6시";
    }public void onClick46(View view) {
        daynumber = 4;
        time = "6시";
    }public void onClick47(View view) {
        daynumber = 5;
        time = "6시";
    }public void onClick48(View view) {
        daynumber = 6;
        time = "6시";
    }public void onClick49(View view) {
        daynumber = 7;
        time = "6시";
    }public void onClick50(View view) {
        daynumber = 1;
        time = "7시";
    }public void onClick51(View view) {
        daynumber = 2;
        time = "7시";
    }public void onClick52(View view) {
        daynumber = 3;
        time = "7시";
    }public void onClick53(View view) {
        daynumber = 4;
        time = "7시";
    }public void onClick54(View view) {
        daynumber = 5;
        time = "7시";
    }public void onClick55(View view) {
        daynumber = 6;
        time = "7시";
    }public void onClick56(View view) {
        daynumber = 7;
        time = "7시";
    }public void onClick57(View view) {
        daynumber = 1;
        time = "8시";
    }public void onClick58(View view) {
        daynumber = 2;
        time = "8시";
    }public void onClick59(View view) {
        daynumber = 3;
        time = "8시";
    }public void onClick60(View view) {
        daynumber = 4;
        time = "8시";
    }public void onClick61(View view) {
        daynumber = 5;
        time = "8시";
    }public void onClick62(View view) {
        daynumber = 6;
        time = "8시";
    }public void onClick63(View view) {
        daynumber = 7;
        time = "8시";
    }public void onClick64(View view) {
        daynumber = 1;
        time = "9시";
    }public void onClick65(View view) {
        daynumber = 2;
        time = "9시";
    }public void onClick66(View view) {
        daynumber = 3;
        time = "9시";
    }public void onClick67(View view) {
        daynumber = 4;
        time = "9시";
    }public void onClick68(View view) {
        daynumber = 5;
        time = "9시";
    }public void onClick69(View view) {
        daynumber = 6;
        time = "9시";
    }public void onClick70(View view) {
        daynumber = 7;
        time = "9시";
    }public void onClick71(View view) {
        daynumber = 1;
        time = "10시";
    }public void onClick72(View view) {
        daynumber = 2;
        time = "10시";
    }public void onClick73(View view) {
        daynumber = 3;
        time = "10시";
    }public void onClick74(View view) {
        daynumber= 4;
        time = "10시";
    }public void onClick75(View view) {
        daynumber = 5;
        time = "10시";
    }public void onClick76(View view) {
        daynumber = 6;
        time = "10시";
    }public void onClick77(View view) {
        daynumber = 7;
        time = "10시";
    }public void onClick78(View view) {
        daynumber = 1;
        time = "11시";
    }public void onClick79(View view) {
        daynumber = 2;
        time = "11시";
    }public void onClick80(View view) {
        daynumber = 3;
        time = "11시";
    }public void onClick81(View view) {
        daynumber = 4;
        time = "11시";
    }public void onClick82(View view) {
        daynumber = 5;
        time = "11시";
    }public void onClick83(View view) {
        daynumber = 6;
        time = "11시";
    }public void onClick84(View view) {
        daynumber = 7;
        time = "11시";
    }public void onClick85(View view) {
        daynumber = 1;
        time = "12시";
    }public void onClick86(View view) {
        daynumber = 2;
        time = "12시";
    }public void onClick87(View view) {
        daynumber = 3;
        time = "12시";
    }public void onClick88(View view) {
        daynumber = 4;
        time = "12시";
    }public void onClick89(View view) {
        daynumber = 5;
        time = "12시";
    }public void onClick90(View view) {
        daynumber = 6;
        time = "12시";
    }public void onClick91(View view) {
        daynumber = 7;
        time = "12시";
    }
}
