package com.gp.shoppingy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txt1 , txt2;
    shopDB shopdb;
    CheckBox ch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shopdb = new shopDB(this);

        shopdb.InializeSomeShopData();

        ch1 = findViewById(R.id.rememberMe);
        txt1 = findViewById(R.id.userNameText);
        txt2 = findViewById(R.id.userPasswordText);

        checkNeededPermissions();
        checkRemeberMe();
        }

    public void checkRemeberMe(){

        Cursor cursor = shopdb.getLastLogin();
        String s1 = "",s2 = "";
        int ty = 0;

        while (!cursor.isAfterLast()) {
            s1 = cursor.getString(1);
            s2 = cursor.getString(2);
            ty = Integer.parseInt(cursor.getString(3));
            cursor.moveToNext();
        }
        if(ty == 0)
        {
            txt1.setText("");
            txt2.setText("");
            ch1.setChecked(false);
        }
        else
        {
            txt1.setText(s1);
            txt2.setText(s2);
            ch1.setChecked(true);
        }
    }
    public void signInFunc(View view)
    {
         String[] dataCustomer = new String[8];

        Cursor cursor = shopdb.getCustomer(txt1.getText().toString(),txt2.getText().toString());
        if(cursor.getCount() == 0)
            Toast.makeText(MainActivity.this,"Invalid userName or Password" , Toast.LENGTH_SHORT).show();
        else
        {
            while(!cursor.isAfterLast()) {
            for(int i=0;i<7;i++)
                dataCustomer[i] = (cursor.getString(i));
            cursor.moveToNext();
            break;
            }

            int check;
            if(ch1.isChecked())
                check =1;
            else
                check = 0;
            shopdb.inserLogin(txt1.getText().toString(),txt2.getText().toString(), check);

            Toast.makeText(MainActivity.this,"Welcome" + dataCustomer[1] , Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, shopPage.class);
          //  i.putExtra("id", dataCustomer[0]);

            myMainClassJava.setLogin(Integer.parseInt(dataCustomer[0]));
            myMainClassJava.initDataFirstLogin();
            startActivity(i);
        }

    }
    public void signUpFunc(View view)
    {
        Intent i = new Intent(MainActivity.this, signUpActivity.class);
        startActivity(i);
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        checkRemeberMe();
    }
    public void forgetpasswordFunc(View view)
    {
        Intent i = new Intent(MainActivity.this, forgetPasswordActivity.class);
        startActivity(i);
    }
    private void checkNeededPermissions(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            //when permmission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                //create Alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Grant those permissions");
                builder.setMessage("Location , Camera ");
                builder.setPositiveButton("OK", (dialog, which) ->
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.CAMERA}
                                , 123));
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    Toast.makeText(getApplicationContext(), "App Should Close,Sorry.", Toast.LENGTH_LONG).show();
                    finish();
                    //System.exit(0);
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA}
                        , 123);
            }
        }
        /*else{
            //when permission are granted
            //Toast.makeText(getApplicationContext(), "Permission already granted..", Toast.LENGTH_SHORT).show();
        }*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if ((grantResults.length > 0) && (
                    grantResults[0] + grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED)) {
                //permission granted
            //    Toast.makeText(getApplicationContext(), "Permission Granted...", Toast.LENGTH_SHORT).show();
            } else {
                //permission NOT granted
                Toast.makeText(getApplicationContext(), "Permission denied...\nYou should allow this permissions.", Toast.LENGTH_SHORT).show();
                checkNeededPermissions();
            }
        }
    }


}