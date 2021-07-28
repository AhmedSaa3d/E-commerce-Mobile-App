package com.gp.shoppingy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class searchPage extends AppCompatActivity {

    EditText ed1;
    Button btn1,btn2,btn3;
    ImageView img1,img2,img3;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        ed1 = findViewById(R.id.sProductText);
        btn1 = findViewById(R.id.searchProductBtn);
        btn2 = findViewById(R.id.searchCategoryBtn);
        img1 = findViewById(R.id.icmic);
        img2 = findViewById(R.id.iccamera);
        img3 = findViewById(R.id.searchImageBox);

        if(ContextCompat.checkSelfPermission(searchPage.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(searchPage.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);

        }

        img1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                speak();
            }
        });

        img2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                TakePhoto();
            }
        });

    }

    private void TakePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }


     private void speak()
     {
         Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
         intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                 RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
         intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
         intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Product Name");

         try{
             startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
         }
         catch (Exception e)
         {
             Toast.makeText(searchPage.this,"" + e.getMessage() , Toast.LENGTH_SHORT).show();

         }

     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        switch(requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:
            {
                if(resultCode == RESULT_OK && null !=data)
                {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ed1.setText(result.get(0));
                }
                break;
            }
            case 100:
            {
                if(resultCode == RESULT_OK && null !=data)
                {
                    Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                    img3.setImageBitmap(captureImage);
                    ed1.setText("barcode");
                }
                break;
            }
        }

     }



    public void searchByProductFunc(View view) {
        if(!String.valueOf(ed1.getText()).equals(""))
        {
            myMainClassJava.typeSearch = "P";
            myMainClassJava.searchContent = String.valueOf(ed1.getText());
            finish();
        }
        else
            Toast.makeText(searchPage.this,"Empty Field" , Toast.LENGTH_SHORT).show();
    }

    public void searchByCategoryFunc(View view) {
        if(!String.valueOf(ed1.getText()).equals(""))
        {
            myMainClassJava.typeSearch = "C";
            myMainClassJava.searchContent = String.valueOf(ed1.getText());
            finish();
        }
        else
            Toast.makeText(searchPage.this,"Empty Field" , Toast.LENGTH_SHORT).show();
    }

    public void cancelFunc(View view) {
        myMainClassJava.searchContent = "Prodcucts";
        myMainClassJava.typeSearch = "A";
        finish();
    }
}