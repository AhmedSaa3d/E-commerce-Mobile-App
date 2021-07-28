package com.gp.shoppingy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class signUpActivity extends AppCompatActivity {


    EditText txt1 , txt2, txt3, txt4, txt5;
    Button btn1;
    RadioButton rd1,rd2;
    CalendarView cal1;
    shopDB shopdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        shopdb = new shopDB(this);

        txt1 = (EditText) findViewById(R.id.nameText);
        txt2 = (EditText)findViewById(R.id.userNameText);
        txt3 = (EditText) findViewById(R.id.userPasswordText);
        txt5 = (EditText) findViewById(R.id.jobText);
        rd1 = (RadioButton) findViewById(R.id.radio1Btn);
        rd2 = (RadioButton) findViewById(R.id.radio2Btn);
        cal1 = (CalendarView ) findViewById(R.id.calenderView);
        btn1 = (Button) findViewById(R.id.addBtn);

    }

    public void submitFunc(View view) {
        String str1,str2,str3,str4,str5,str6;
        str1 = txt1.getText().toString();
        str2 = txt2.getText().toString();
        str3 = txt3.getText().toString();
        str5 = txt5.getText().toString();
        str6 = cal1.getDate() + "";

        if(rd1.isChecked() && !rd2.isChecked() )
            str4 = "Male";
        else if(!rd1.isChecked() && rd2.isChecked())
            str4 = "Female";
        else
            str4 = "";

        if (!str1.equals("") && !str2.equals("") && !str3.equals("") && !str4.equals("") && !str5.equals("") && !str6.equals("")) {
            shopdb.createNewCustomer(str1,str2,str3,str4, str6,str5);
        //    Toast.makeText(signUpActivity.this,(str1+str2+str3+str4+ str6+str5) , Toast.LENGTH_LONG).show();
              Toast.makeText(signUpActivity.this,"Customer Add Correctly" , Toast.LENGTH_LONG).show();
            finish();

        }
        else
        {
            Toast.makeText(signUpActivity.this, "Invalid Input Data", Toast.LENGTH_SHORT).show();
            txt1.setText("");
            txt2.setText("");
            txt3.setText("");
            str4 = "";
            txt5.setText("");
            rd1.setChecked(false);
            rd2.setChecked(false);
        }

        /*cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view1, int year, int month, int dayOfMonth) {
                // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
                String str6 = dayOfMonth + "-" + (month + 1) + "-" + year;
           btn1.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   if (!str1.equals("") && !str2.equals("") && !str3.equals("") && !str4.equals("") && !str5.equals("") && !str6.equals("")) {
                       shopdb.createNewCustomer(str1,str2,str3,str4, str6,str5);
                       Toast.makeText(signUpActivity.this,(str1+str2+str3+str4+ str6+str5) , Toast.LENGTH_LONG).show();
                   }
                   else
                   {
                       Toast.makeText(signUpActivity.this, "Invalid Input Data", Toast.LENGTH_SHORT).show();
                   }
               }
           });
            }
        });*/


    }


}