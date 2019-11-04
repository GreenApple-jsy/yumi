package com.example.yumi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class getTutorVideo extends AppCompatActivity {


    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;
    private static final String TAG_book = "book";
    private static final String TAG_QN = "q_number";

    TextView mTextViewResult;
    phpVDConnect task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_my_video);
        mlistView = (ListView)findViewById(R.id.listView_vd_list) ;
        task = new phpVDConnect();
        task.execute();


        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getMoreView(position);
            }
        });
    }

    void getMoreView(int position){

        // 리스트 클릭시 비디오 자세히 보기 구현(?) 자리
        Toast toast = Toast.makeText(getApplicationContext(), "position " + position, Toast.LENGTH_LONG); toast.show();

    }

    class phpVDConnect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String link = "http://1.234.38.211/getTutorVideo.php";
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();
                return sb.toString();

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d(TAG, "response  - " + result);

            if (result == null){
                mTextViewResult.setText("error occurred");
            }
            else {
                mJsonString = result;
                showResult();
            }
        }
    }

    private void showResult(){

        mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String bookName = item.getString(TAG_book);
                String qn = item.getString(TAG_QN);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_book, bookName);
                hashMap.put(TAG_QN,qn);


                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getTutorVideo.this, mArrayList, R.layout.tutor_video_list,
                    new String[]{TAG_book,TAG_QN},
                    new int[]{R.id.textView_list_book, R.id.textView_list_page}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
