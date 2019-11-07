package com.example.yumi;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class uploadq extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "phptest";
    String filename;

    private Button bt_tab1, bt_tab2,bt_tab3;

    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    String uploadFilePath;
    String uploadFileName;

    int y=0, m=0, d=0, h=0, mi=0;
    final String TAGS = getClass().getSimpleName();
    ImageView imageView;
    Button complete;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        complete =  findViewById(R.id.complete);
        inputetimes = findViewById(R.id.inputetime);
        inputstimes = findViewById(R.id.inputstime);
        inputnumber = findViewById(R.id.inputnumber);
        inputpage = findViewById(R.id.inputpage);
        schoolspinner = findViewById((R.id.schoolspinner));
        agespinner = findViewById(R.id.agespinner);
        semesterspinner = findViewById(R.id.semesterspinner);
        bookspinner = findViewById(R.id.bookspinner);
        Button complete = findViewById(R.id.complete);
        Button endtime = findViewById(R.id.endtime);
        Button starttime = findViewById(R.id.starttime);
        inputstime = findViewById(R.id.inputstime);
        inputetime = findViewById(R.id.inputetime);
        final TextView tv = (TextView)findViewById(R.id.bookcheck);
        Spinner s = (Spinner)findViewById(R.id.bookspinner);
        imageView = findViewById(R.id.image);
        cameraBtn = findViewById(R.id.camera);
        cameraBtn.setOnClickListener(this);

        bt_tab2 = (Button)findViewById(R.id.bt_tab2);
        bt_tab3 = (Button)findViewById(R.id.bt_tab3);

        bt_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //얘는 영상 기능 완료 후에 할 수 있어서 아직 없어요...
            }
        });
        bt_tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadq.this, stdMyPage.class);
                startActivity(intent);
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

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_TIMES);
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_TIMEE);
            }
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
                String school, age,semester,book,page,number,starttime,endtime,image;
                school = schoolspinner.getSelectedItem().toString();
                age = agespinner.getSelectedItem().toString();
                semester = semesterspinner.getSelectedItem().toString();
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
                starttime = inputstimes.getText().toString();
                if (starttime.getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(getApplicationContext(),"시작 시간을 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                endtime = inputetimes.getText().toString();
                if (endtime.getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(getApplicationContext(),"종료 시간을 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                String id = "ayoung";
                filename = id+Long.toString(System.currentTimeMillis());
                //이미지도 코드 짜기
                InsertData task = new InsertData();
                task.execute("http://1.234.38.211/q_insert.php", school, age, semester, book, page, number,starttime, endtime,id);
                dialog = ProgressDialog.show(uploadq.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                            }
                        });
                        uploadFile(uploadFilePath);
                    }
                }).start();
            }
        });
    }
    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }
    void showTimestart() {
        TimePickerDialog tpd =
                new TimePickerDialog(uploadq.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                Toast.makeText(getApplicationContext(),
                                        hourOfDay +"시 " + minute+"분 을 선택했습니다",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, // 값설정시 호출될 리스너 등록
                        4,19, false); // 기본값 시분 등록
        // true : 24 시간(0~23) 표시
        // false : 오전/오후 항목이 생김
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

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DIALOG_TIMES :
                TimePickerDialog tpds =
                        new TimePickerDialog(uploadq.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view,
                                                          int hourOfDay, int minute) {
                                        inputstime.setText(hourOfDay +"시 " + minute+"분");
                                        Toast.makeText(getApplicationContext(),
                                                hourOfDay +"시 " + minute+"분",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }, // 값설정시 호출될 리스너 등록
                                4,19, false); // 기본값 시분 등록
                // true : 24 시간(0~23) 표시
                // false : 오전/오후 항목이 생김
                return tpds;
            case DIALOG_TIMEE :
                TimePickerDialog tpde =
                        new TimePickerDialog(uploadq.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view,
                                                          int hourOfDay, int minute) {
                                        inputetime.setText(hourOfDay +"시 " + minute+"분");
                                        Toast.makeText(getApplicationContext(),
                                                hourOfDay +"시 " + minute+"분",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }, // 값설정시 호출될 리스너 등록
                                4,19, false); // 기본값 시분 등록
                // true : 24 시간(0~23) 표시
                // false : 오전/오후 항목이 생김
                return tpde;
        }
        return super.onCreateDialog(id);
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
            String start_time = (String)params[7];
            String end_time = (String)params[8];
            String id = (String)params[9];
            String postParameters = "&q_image=" +q_image + "&school_type=" + school_type + "&age=" + age + "&semester=" + semester + "&book=" + book + "&page=" + page + "&q_number=" + q_number + "&start_time=" + start_time + "&end_time=" + end_time +  "&s_id=" + id;

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
