package com.gp.shoppingy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class productDataDisplay extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5, t6, t7;
    Button bt1;
    int tempind;
    shopDB shopdb;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_data_display);

        t1 = findViewById(R.id.pNameTxt);
        t2 = findViewById(R.id.pcatTxt);
        t3 = findViewById(R.id.pPrcieTxt);
        t4 = findViewById(R.id.pQuantityTxt);
        t5 = findViewById(R.id.pIncreaseTxt);
        t6 = findViewById(R.id.pDecreaseTxt);
        t7 = findViewById(R.id.pTotalTxt);
        tempind = myMainClassJava.getProductind();
        shopdb = new shopDB(this);

        displayProduct();

    }

    public void displayProduct() {
        cursor = shopdb.getProductData(myMainClassJava.getId(tempind));
        while (!cursor.isAfterLast()) {
            t1.setText("Name : " + cursor.getString(1));
            t2.setText("Category : " + cursor.getString(6));////////
            t3.setText("Price = " + myMainClassJava.getprice(tempind) + "$");
            t4.setText("" + myMainClassJava.getCount(tempind));
            t7.setText("Total Price = " + myMainClassJava.getSubTotal(tempind) + "$");
            break;
        }
    }




    public void confirmFunc(View view) {
        finish();
    }

    public void IncAmountFunc(View view) {
        if(Integer.parseInt(cursor.getString(3)) > 0) {
            myMainClassJava.incQuantity(tempind);
            shopdb.decreaseProductQuantity(myMainClassJava.getId(tempind),1);
            displayProduct();
        }
    }

    public void decAmountFunc(View view) {
        if(myMainClassJava.getCount(tempind) > 0)
        {
            myMainClassJava.decQuantity(tempind);
            shopdb.increaseProductQuantity(myMainClassJava.getId(tempind),1);
            displayProduct();
        }
    }
}