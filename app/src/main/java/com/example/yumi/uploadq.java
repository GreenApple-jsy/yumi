package com.example.yumi;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class uploadq extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "phptest";
    String filename;

    private Button bt_tab1, bt_tab2,bt_tab3;

    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    String uploadFilePath;
    String uploadFileName;
    ArrayAdapter<CharSequence> midHighAdapter, subAdapter;
    int mOh = 0;
    int sub = 0;
    int cat = 0;
    String grade= "";
    String subj = "";
    String categ = "";
    String[] array;
    String[] subArray;

    int y=0, m=0, d=0, h=0, mi=0;
    final String TAGS = getClass().getSimpleName();
    ImageView imageView;
    Button complete;
    Button datepick;
    ImageButton cameraBtn;
    final static int TAKE_PICTURE = 1;
    final int DIALOG_TIMES = 1;
    final int DIALOG_TIMEE = 2;

    Spinner schoolspinner, agespinner,semesterspinner,bookspinner;
    TextView inputstime,inputetime;
    EditText inputpage,inputnumber,inputstimes,inputetimes;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    final Calendar cal = Calendar.getInstance();
    String[] timepick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        datepick = findViewById(R.id.datepick);
        complete =  findViewById(R.id.complete);
        inputnumber = findViewById(R.id.inputnumber);
        inputpage = findViewById(R.id.inputpage);
        //schoolspinner = findViewById((R.id.schoolspinner));
        //agespinner = findViewById(R.id.agespinner);
        //semesterspinner = findViewById(R.id.semesterspinner);
        bookspinner = findViewById(R.id.bookspinner);
        Button complete = findViewById(R.id.complete);
        final TextView tv = (TextView)findViewById(R.id.bookcheck);
        Spinner s = (Spinner)findViewById(R.id.bookspinner);
        imageView = findViewById(R.id.image);
        cameraBtn = findViewById(R.id.camera);
        cameraBtn.setOnClickListener(this);
        timepick = new String[100];

        final Spinner dropdown = (Spinner) findViewById(R.id.midHigh);
        final Spinner subject = (Spinner)findViewById(R.id.subject);
        final Spinner category = (Spinner)findViewById(R.id.category);

        Intent intent = getIntent();
        timepick = intent.getStringArrayExtra("datepick");

        //bt_tab3 = (Button)findViewById(R.id.bt_tab3);

        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Student_timeselect.class);
                startActivity(intent3);
            }
        });

        upLoadServerUri = "http://1.234.38.211/q_insertimage.php";
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // tv.setText("position : " + position +
                //          parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAGS, "권한 설정 완료");
            } else {
                Log.d(TAGS, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String school, age,semester,book,page,number,chapter;
                chapter = category.getSelectedItem().toString();
                //임의로 챕터값 설정해 놨어요!!

                school = dropdown.getSelectedItem().toString();
                age = subject.getSelectedItem().toString().substring(0,3);
                semester = subject.getSelectedItem().toString().substring(4);
                book = bookspinner.getSelectedItem().toString();
                if(school.equals("선택하세요")){
                    Toast.makeText(getApplicationContext(),"학교를 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(age.equals("선택하세요")){
                    Toast.makeText(getApplicationContext(),"학년을 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(semester.equals("선택하세요")){
                    Toast.makeText(getApplicationContext(),"학기를 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(book.equals("선택하세요")){
                    Toast.makeText(getApplicationContext(),"교재를 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                page = inputpage.getText().toString();
                if (page.getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(getApplicationContext(),"페이지를 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                number = inputnumber.getText().toString();
                if (number.getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(getApplicationContext(),"문제 번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences auto = getSharedPreferences("yumi", Activity.MODE_PRIVATE);
                String id = auto.getString("id",null);
                filename = id+Long.toString(System.currentTimeMillis());
                System.out.println("파일 이름" + filename);
                //Toast.makeText(getApplicationContext(),filename ,Toast.LENGTH_SHORT).show();
                String timecheck="0";
                for(int i=0; i<100; i++){
                    System.out.println(timepick[i]);
                    timecheck= timecheck+timepick[i];
                }
                System.out.println("출력함");
                System.out.println(timecheck);
                Date currentTime = Calendar.getInstance().getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

                InsertData task = new InsertData();
                task.execute("http://1.234.38.211/q_insert.php", school, age, semester, book, page, number,id,timecheck,chapter,date_text);

                Intent intent3 = new Intent(getApplicationContext(), StudentQuestionlist.class);
                startActivity(intent3);
            }
        });


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mOh = position;
                // 중학교
                if (position == 1 ) {
                    grade = "중학생";
                    midHighAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_subject, android.R.layout.simple_spinner_dropdown_item);
                    midHighAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subject.setAdapter( midHighAdapter);

                    subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sub = i ;
                            subArray= getResources().getStringArray(R.array.mid_subject);
                            subj = subArray[i];
                            if (sub == 1){
                                array = getResources().getStringArray(R.array.mid_position1);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_position1, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 2){
                                array = getResources().getStringArray(R.array.mid_position2);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_position2, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 3){
                                array = getResources().getStringArray(R.array.mid_position3);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_position3, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 4){
                                array = getResources().getStringArray(R.array.mid_position4);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_position4, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 5){
                                array = getResources().getStringArray(R.array.mid_position5);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_position5, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub== 6){
                                array = getResources().getStringArray(R.array.mid_position6);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.mid_position6, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                // 고등학교
                else if (position == 2){
                    grade= "고등학생";
                    midHighAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_subject, android.R.layout.simple_spinner_dropdown_item);
                    midHighAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subject.setAdapter( midHighAdapter);

                    subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sub = i ;
                            subArray= getResources().getStringArray(R.array.high_subject);
                            subj = subArray[i];
                            if (sub == 1){
                                array = getResources().getStringArray(R.array.high_position1);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_position1, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 2){
                                array = getResources().getStringArray(R.array.high_position2);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_position2, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 3){
                                array = getResources().getStringArray(R.array.high_position3);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_position3, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 4){
                                array = getResources().getStringArray(R.array.high_position4);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_position4, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub == 5){
                                array = getResources().getStringArray(R.array.high_position5);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_position5, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            else if (sub== 6){
                                array = getResources().getStringArray(R.array.high_position6);
                                subAdapter = ArrayAdapter.createFromResource(uploadq.this, R.array.high_position6, android.R.layout.simple_spinner_dropdown_item);
                                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(subAdapter);
                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categ = array[i];
                                        cat = i;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



    }

    public void gohome(View view){  //메인 화면으로 돌아가기
        Intent intent = new Intent(getApplicationContext(),StudentQuestionlist.class);
        startActivity(intent);
    }

    public void search(View view ){
        if (mOh == 0 || sub == 0 || cat == 0){
            Toast.makeText(getApplicationContext(), "전체 항목을 선택해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), grade + " " + subj + " " + categ, Toast.LENGTH_SHORT).show();
        }
    }

    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }
    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.camera:
                // 카메라 앱을 여는 소스
                dispatchTakePictureIntent();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            Bitmap rotatedBitmap = null;
                            switch(orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }

                            imageView.setImageBitmap(rotatedBitmap);
                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        uploadFileName = imageFileName+".jpg";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        //uploadFilePath =  Environment.getExternalStorageDirectory() +"/" ;
        uploadFilePath = image.getAbsolutePath();
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.yumi.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(uploadq.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String q_image = (String)filename;
            String serverURL = (String)params[0];
            String school_type= (String)params[1];
            String age = (String)params[2];
            String semester = (String)params[3];
            String book = (String)params[4];
            String page = (String)params[5];
            String q_number = (String)params[6];
            String id = (String)params[7];
            String timepick = params[8];
            String chapter = params[9];
            String dates = params[10];
            String postParameters = "&q_image=" +q_image + "&school_type=" + school_type + "&age=" + age + "&semester=" + semester + "&book=" + book + "&page=" + page + "&q_number=" + q_number + "&s_id=" + id + "&timetable=" + timepick + "&chapter=" + chapter + "&dates=" + dates;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }

        }
    }
    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :" +uploadFilePath + "" + uploadFileName);
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(uploadq.this, "Source File not exist :" +uploadFilePath + "" + uploadFileName,Toast.LENGTH_SHORT).show();
                }
            });
            return 0;
        }
        else
        {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + filename+".jpg" + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"+uploadFileName;
                            Toast.makeText(uploadq.this, "File Upload Complete.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(uploadq.this, "MalformedURLException",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(uploadq.this, "Got Exception : see logcat ",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;
        } // End else block
    }
}
