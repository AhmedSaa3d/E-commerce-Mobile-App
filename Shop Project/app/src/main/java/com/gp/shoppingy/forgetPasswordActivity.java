package com.gp.shoppingy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class forgetPasswordActivity extends AppCompatActivity {


    EditText ed1,ed2,ed3;
    shopDB shopdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ed1 = findViewById(R.id.RnameText);
        ed2 = findViewById(R.id.RjobText);
        ed3 = findViewById(R.id.RuserPasswordText);
        shopdb = new shopDB(this);


    }

    public void RsubmitFunc(View view) {


        if(ed1.getText().toString().equals("") || ed2.getText().toString().equals("") || ed3.getText().toString().equals(""))
           Toast.makeText(forgetPasswordActivity.this, "Empty Fields.", Toast.LENGTH_SHORT).show();
       else if(shopdb.checknameJop(ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString()))
           Toast.makeText(forgetPasswordActivity.this, "Password changed.", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(forgetPasswordActivity.this, "Wrong Name OR Jop.", Toast.LENGTH_SHORT).show();


    }
}