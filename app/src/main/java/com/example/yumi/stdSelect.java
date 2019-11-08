package com.example.yumi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class stdSelect extends AppCompatActivity {
    ArrayAdapter<CharSequence>midHighAdapter, subAdapter;
    int mOh = 0;
    int sub = 0;
    int cat = 0;
    String grade= "";
    String subj = "";
    String categ = "";
    String[] array;
    String[] subArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.std_category);

        final Spinner dropdown = (Spinner) findViewById(R.id.midHigh);
        final Spinner subject = (Spinner)findViewById(R.id.subject);
        final Spinner category = (Spinner)findViewById(R.id.category);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mOh = position;
                // 중학교
                if (position == 1 ) {
                    grade = "중학생";
                    midHighAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_subject, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position1, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position2, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position3, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position4, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position5, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.mid_position6, android.R.layout.simple_spinner_dropdown_item);
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
                    midHighAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_subject, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position1, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position2, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position3, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position4, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position5, android.R.layout.simple_spinner_dropdown_item);
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
                                subAdapter = ArrayAdapter.createFromResource(stdSelect.this, R.array.high_position6, android.R.layout.simple_spinner_dropdown_item);
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


    public void search(View view ){
        if (mOh == 0 || sub == 0 || cat == 0){
            Toast.makeText(getApplicationContext(), "전체 항목을 선택해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), grade + " " + subj + " " + categ, Toast.LENGTH_SHORT).show();
        }
    }

}
