package com.example.yumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Student_timeselect extends AppCompatActivity {
    String[] timepick = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        for (int i = 0; i < 100; i++) {
            timepick[i] = "0";
        }

        Button complete = (Button) findViewById(R.id.complete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), uploadq.class);
                intent3.putExtra("datepick", timepick);
                startActivity(intent3);
            }
        });


    }

    public void onClick1(View view) {
        Button button = findViewById(view.getId());
        if (timepick[1] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[1] = "1";
        } else if (timepick[1] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[1] = "0";
        }
    }
    public void onClick2(View view) {
        Button button = findViewById(view.getId());
        if (timepick[2] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[2] = "1";
        } else if (timepick[1] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[2] = "0";
        }
    }
    public void onClick3(View view) {
        Button button = findViewById(view.getId());
        if (timepick[3] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[3] = "1";
        } else if (timepick[3] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[3] = "0";
        }
    }
    public void onClick4(View view) {
        Button button = findViewById(view.getId());
        if (timepick[4] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[4] = "1";
        } else if (timepick[4] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[4] = "0";
        }
    }
    public void onClick5(View view) {
        Button button = findViewById(view.getId());
        if (timepick[5] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[5] = "1";
        } else if (timepick[5] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[5] = "0";
        }
    }
    public void onClick6(View view) {
        Button button = findViewById(view.getId());
        if (timepick[6] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[6] = "1";
        } else if (timepick[6] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[6] = "0";
        }
    }
    public void onClick7(View view) {
        Button button = findViewById(view.getId());
        if (timepick[7] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[7] = "1";
        } else if (timepick[7] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[7] = "0";
        }
    }
    public void onClick8(View view) {
        Button button = findViewById(view.getId());
        if (timepick[8] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[8] = "1";
        } else if (timepick[8] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[8] = "0";
        }
    }
    public void onClick9(View view) {
        Button button = findViewById(view.getId());
        if (timepick[9] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[9] = "1";
        } else if (timepick[9] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[9] = "0";
        }
    }
    public void onClick10(View view) {
        Button button = findViewById(view.getId());
        if (timepick[10] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[10] = "1";
        } else if (timepick[10] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[10] = "0";
        }
    }
    public void onClick11(View view) {
        Button button = findViewById(view.getId());
        if (timepick[11] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[11] = "1";
        } else if (timepick[11] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[11] = "0";
        }
    }
    public void onClick12(View view) {
        Button button = findViewById(view.getId());
        if (timepick[12] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[12] = "1";
        } else if (timepick[12] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[12] = "0";
        }
    }
    public void onClick13(View view) {
        Button button = findViewById(view.getId());
        if (timepick[13] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[13] = "1";
        } else if (timepick[13] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[13] = "0";
        }
    }
    public void onClick14(View view) {
        Button button = findViewById(view.getId());
        if (timepick[14] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[14] = "1";
        } else if (timepick[14] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[14] = "0";
        }
    }
    public void onClick15(View view) {
        Button button = findViewById(view.getId());
        if (timepick[15] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[15] = "1";
        } else if (timepick[15] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[15] = "0";
        }
    }
    public void onClick16(View view) {
        Button button = findViewById(view.getId());
        if (timepick[16] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[16] = "1";
        } else if (timepick[16] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[16] = "0";
        }
    }
    public void onClick17(View view) {
        Button button = findViewById(view.getId());
        if (timepick[17] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[17] = "1";
        } else if (timepick[17] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[17] = "0";
        }
    }
    public void onClick18(View view) {
        Button button = findViewById(view.getId());
        if (timepick[18] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[18] = "1";
        } else if (timepick[18] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[18] = "0";
        }
    }
    public void onClick19(View view) {
        Button button = findViewById(view.getId());
        if (timepick[19] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[19] = "1";
        } else if (timepick[19] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[19] = "0";
        }
    }
    public void onClick20(View view) {
        Button button = findViewById(view.getId());
        if (timepick[20] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[20] = "1";
        } else if (timepick[20] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[20] = "0";
        }
    }
    public void onClick21(View view) {
        Button button = findViewById(view.getId());
        if (timepick[21] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[21] = "1";
        } else if (timepick[21] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[21] = "0";
        }
    }
    public void onClick22(View view) {
        Button button = findViewById(view.getId());
        if (timepick[22] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[22] = "1";
        } else if (timepick[22] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[22] = "0";
        }
    }
    public void onClick23(View view) {
        Button button = findViewById(view.getId());
        if (timepick[23] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[23] = "1";
        } else if (timepick[23] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[23] = "0";
        }
    }
    public void onClick24(View view) {
        Button button = findViewById(view.getId());
        if (timepick[24] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[24] = "1";
        } else if (timepick[24] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[24] = "0";
        }
    }
    public void onClick25(View view) {
        Button button = findViewById(view.getId());
        if (timepick[25] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[25] = "1";
        } else if (timepick[25] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[25] = "0";
        }
    }
    public void onClick26(View view) {
        Button button = findViewById(view.getId());
        if (timepick[26] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[26] = "1";
        } else if (timepick[26] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[26] = "0";
        }
    }
    public void onClick27(View view) {
        Button button = findViewById(view.getId());
        if (timepick[27] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[27] = "1";
        } else if (timepick[27] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[27] = "0";
        }
    }
    public void onClick28(View view) {
        Button button = findViewById(view.getId());
        if (timepick[28] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[28] = "1";
        } else if (timepick[28] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[28] = "0";
        }
    }
    public void onClick29(View view) {
        Button button = findViewById(view.getId());
        if (timepick[29] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[29] = "1";
        } else if (timepick[29] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[29] = "0";
        }
    }
    public void onClick30(View view) {
        Button button = findViewById(view.getId());
        if (timepick[30] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[30] = "1";
        } else if (timepick[30] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[30] = "0";
        }
    }public void onClick31(View view) {
        Button button = findViewById(view.getId());
        if (timepick[31] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[31] = "1";
        } else if (timepick[31] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[31] = "0";
        }
    }
    public void onClick32(View view) {
        Button button = findViewById(view.getId());
        if (timepick[32] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[32] = "1";
        } else if (timepick[32] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[32] = "0";
        }
    }
    public void onClick33(View view) {
        Button button = findViewById(view.getId());
        if (timepick[33] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[33] = "1";
        } else if (timepick[33] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[33] = "0";
        }
    }
    public void onClick34(View view) {
        Button button = findViewById(view.getId());
        if (timepick[34] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[34] = "1";
        } else if (timepick[34] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[34] = "0";
        }
    }
    public void onClick35(View view) {
        Button button = findViewById(view.getId());
        if (timepick[35] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[35] = "1";
        } else if (timepick[35] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[35] = "0";
        }
    }
    public void onClick36(View view) {
        Button button = findViewById(view.getId());
        if (timepick[36] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[36] = "1";
        } else if (timepick[36] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[36] = "0";
        }
    }
    public void onClick37(View view) {
        Button button = findViewById(view.getId());
        if (timepick[37] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[37] = "1";
        } else if (timepick[37] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[37] = "0";
        }
    }
    public void onClick38(View view) {
        Button button = findViewById(view.getId());
        if (timepick[38] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[38] = "1";
        } else if (timepick[38] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[38] = "0";
        }
    }
    public void onClick39(View view) {
        Button button = findViewById(view.getId());
        if (timepick[39] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[39] = "1";
        } else if (timepick[39] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[39] = "0";
        }
    }
    public void onClick40(View view) {
        Button button = findViewById(view.getId());
        if (timepick[40] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[40] = "1";
        } else if (timepick[40] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[40] = "0";
        }
    }
    public void onClick41(View view) {
        Button button = findViewById(view.getId());
        if (timepick[41] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[41] = "1";
        } else if (timepick[41] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[41] = "0";
        }
    }
    public void onClick42(View view) {
        Button button = findViewById(view.getId());
        if (timepick[42] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[42] = "1";
        } else if (timepick[42] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[42] = "0";
        }
    }
    public void onClick43(View view) {
        Button button = findViewById(view.getId());
        if (timepick[43] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[43] = "1";
        } else if (timepick[43] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[43] = "0";
        }
    }
    public void onClick44(View view) {
        Button button = findViewById(view.getId());
        if (timepick[44] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[44] = "1";
        } else if (timepick[44] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[44] = "0";
        }
    }
    public void onClick45(View view) {
        Button button = findViewById(view.getId());
        if (timepick[45] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[45] = "1";
        } else if (timepick[45] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[45] = "0";
        }
    }public void onClick46(View view) {
        Button button = findViewById(view.getId());
        if (timepick[46] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[46] = "1";
        } else if (timepick[46] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[46] = "0";
        }
    }public void onClick47(View view) {
        Button button = findViewById(view.getId());
        if (timepick[47] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[47] = "1";
        } else if (timepick[47] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[47] = "0";
        }
    }public void onClick48(View view) {
        Button button = findViewById(view.getId());
        if (timepick[48] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[48] = "1";
        } else if (timepick[48] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[48] = "0";
        }
    }public void onClick49(View view) {
        Button button = findViewById(view.getId());
        if (timepick[49] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[49] = "1";
        } else if (timepick[49] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[49] = "0";
        }
    }public void onClick50(View view) {
        Button button = findViewById(view.getId());
        if (timepick[50] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[50] = "1";
        } else if (timepick[50] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[50] = "0";
        }
    }public void onClick51(View view) {
        Button button = findViewById(view.getId());
        if (timepick[51] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[51] = "1";
        } else if (timepick[51] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[51] = "0";
        }
    }public void onClick52(View view) {
        Button button = findViewById(view.getId());
        if (timepick[52] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[52] = "1";
        } else if (timepick[52] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[52] = "0";
        }
    }public void onClick53(View view) {
        Button button = findViewById(view.getId());
        if (timepick[53] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[53] = "1";
        } else if (timepick[53] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[53] = "0";
        }
    }public void onClick54(View view) {
        Button button = findViewById(view.getId());
        if (timepick[54] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[54] = "1";
        } else if (timepick[54] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[54] = "0";
        }
    }public void onClick55(View view) {
        Button button = findViewById(view.getId());
        if (timepick[55] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[55] = "1";
        } else if (timepick[55] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[55] = "0";
        }
    }public void onClick56(View view) {
        Button button = findViewById(view.getId());
        if (timepick[56] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[56] = "1";
        } else if (timepick[56] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[56] = "0";
        }
    }public void onClick57(View view) {
        Button button = findViewById(view.getId());
        if (timepick[57] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[57] = "1";
        } else if (timepick[57] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[57] = "0";
        }
    }public void onClick58(View view) {
        Button button = findViewById(view.getId());
        if (timepick[58] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[58] = "1";
        } else if (timepick[58] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[58] = "0";
        }
    }public void onClick59(View view) {
        Button button = findViewById(view.getId());
        if (timepick[59] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[59] = "1";
        } else if (timepick[59] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[59] = "0";
        }
    }public void onClick60(View view) {
        Button button = findViewById(view.getId());
        if (timepick[60] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[60] = "1";
        } else if (timepick[60] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[60] = "0";
        }
    }public void onClick61(View view) {
        Button button = findViewById(view.getId());
        if (timepick[61] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[61] = "1";
        } else if (timepick[61] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[61] = "0";
        }
    }public void onClick62(View view) {
        Button button = findViewById(view.getId());
        if (timepick[62] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[62] = "1";
        } else if (timepick[62] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[62] = "0";
        }
    }public void onClick63(View view) {
        Button button = findViewById(view.getId());
        if (timepick[63] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[63] = "1";
        } else if (timepick[63] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[63] = "0";
        }
    }public void onClick64(View view) {
        Button button = findViewById(view.getId());
        if (timepick[64] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[64] = "1";
        } else if (timepick[64] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[64] = "0";
        }
    }public void onClick65(View view) {
        Button button = findViewById(view.getId());
        if (timepick[65] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[65] = "1";
        } else if (timepick[65] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[65] = "0";
        }
    }public void onClick66(View view) {
        Button button = findViewById(view.getId());
        if (timepick[66] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[66] = "1";
        } else if (timepick[66] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[66] = "0";
        }
    }public void onClick67(View view) {
        Button button = findViewById(view.getId());
        if (timepick[67] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[67] = "1";
        } else if (timepick[67] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[67] = "0";
        }
    }public void onClick68(View view) {
        Button button = findViewById(view.getId());
        if (timepick[68] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[68] = "1";
        } else if (timepick[68] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[68] = "0";
        }
    }public void onClick69(View view) {
        Button button = findViewById(view.getId());
        if (timepick[69] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[69] = "1";
        } else if (timepick[69] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[69] = "0";
        }
    }public void onClick70(View view) {
        Button button = findViewById(view.getId());
        if (timepick[70] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[70] = "1";
        } else if (timepick[70] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[70] = "0";
        }
    }public void onClick71(View view) {
        Button button = findViewById(view.getId());
        if (timepick[71] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[71] = "1";
        } else if (timepick[71] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[71] = "0";
        }
    }public void onClick72(View view) {
        Button button = findViewById(view.getId());
        if (timepick[72] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[72] = "1";
        } else if (timepick[72] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[72] = "0";
        }
    }public void onClick73(View view) {
        Button button = findViewById(view.getId());
        if (timepick[73] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[73] = "1";
        } else if (timepick[73] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[73] = "0";
        }
    }public void onClick74(View view) {
        Button button = findViewById(view.getId());
        if (timepick[74] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[74] = "1";
        } else if (timepick[74] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[74] = "0";
        }
    }public void onClick75(View view) {
        Button button = findViewById(view.getId());
        if (timepick[75] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[75] = "1";
        } else if (timepick[75] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[75] = "0";
        }
    }public void onClick76(View view) {
        Button button = findViewById(view.getId());
        if (timepick[76] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[76] = "1";
        } else if (timepick[76] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[76] = "0";
        }
    }public void onClick77(View view) {
        Button button = findViewById(view.getId());
        if (timepick[77] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[77] = "1";
        } else if (timepick[77] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[77] = "0";
        }
    }public void onClick78(View view) {
        Button button = findViewById(view.getId());
        if (timepick[78] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[78] = "1";
        } else if (timepick[78] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[78] = "0";
        }
    }public void onClick79(View view) {
        Button button = findViewById(view.getId());
        if (timepick[79] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[79] = "1";
        } else if (timepick[79] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[79] = "0";
        }
    }public void onClick80(View view) {
        Button button = findViewById(view.getId());
        if (timepick[80] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[80] = "1";
        } else if (timepick[80] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[80] = "0";
        }
    }public void onClick81(View view) {
        Button button = findViewById(view.getId());
        if (timepick[81] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[81] = "1";
        } else if (timepick[81] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[81] = "0";
        }
    }public void onClick82(View view) {
        Button button = findViewById(view.getId());
        if (timepick[82] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[82] = "1";
        } else if (timepick[82] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[82] = "0";
        }
    }public void onClick83(View view) {
        Button button = findViewById(view.getId());
        if (timepick[83] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[83] = "1";
        } else if (timepick[83] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[83] = "0";
        }
    }public void onClick84(View view) {
        Button button = findViewById(view.getId());
        if (timepick[84] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[84] = "1";
        } else if (timepick[84] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[84] = "0";
        }
    }public void onClick85(View view) {
        Button button = findViewById(view.getId());
        if (timepick[85] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[85] = "1";
        } else if (timepick[85] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[85] = "0";
        }
    }public void onClick86(View view) {
        Button button = findViewById(view.getId());
        if (timepick[86] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[86] = "1";
        } else if (timepick[86] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[86] = "0";
        }
    }public void onClick87(View view) {
        Button button = findViewById(view.getId());
        if (timepick[87] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[87] = "1";
        } else if (timepick[87] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[87] = "0";
        }
    }public void onClick88(View view) {
        Button button = findViewById(view.getId());
        if (timepick[88] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[88] = "1";
        } else if (timepick[88] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[88] = "0";
        }
    }public void onClick89(View view) {
        Button button = findViewById(view.getId());
        if (timepick[89] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[89] = "1";
        } else if (timepick[89] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[89] = "0";
        }
    }public void onClick90(View view) {
        Button button = findViewById(view.getId());
        if (timepick[90] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[90] = "1";
        } else if (timepick[90] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[90] = "0";
        }
    }public void onClick91(View view) {
        Button button = findViewById(view.getId());
        if (timepick[91] == "0") {
            button.setBackgroundResource(R.color.colorAccent);
            timepick[91] = "1";
        } else if (timepick[91] == "1") {
            button.setBackgroundResource(R.drawable.image_dash2);
            timepick[91] = "0";
        }
    }
}
