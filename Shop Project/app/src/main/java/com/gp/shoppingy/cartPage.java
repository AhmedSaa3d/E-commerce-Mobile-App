package com.gp.shoppingy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class cartPage extends AppCompatActivity {
   // private final FusedLocationProviderClient fusedLocationProviderClient;

    TextView ed1,ed2,ed3;
    Button btn1;
    ListView lV;
    shopDB shopdb;

    ArrayList<Integer> idSelect;
    ArrayList<String> arrayList;

    ArrayAdapter listAdapter;

    FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        ed1 = findViewById(R.id.totalTxt);
        ed2 = findViewById(R.id.longTxt);
        ed3 = findViewById(R.id.altTxt);
        btn1 = findViewById(R.id.cconfirmBtn);
        lV = findViewById(R.id.listId1);
        shopdb= new shopDB(this);
        idSelect = new ArrayList<Integer>();
        arrayList = new ArrayList<String>();
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        lV.setAdapter(listAdapter);

        displayProducts();

        changeLocation();
    }

    private void changeLocation()
    {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null)
                {
                    Double x=location.getLongitude();
                    Double y=location.getLatitude();
                    ed2.setText("Longitude : " + location.getLongitude());
                    ed3.setText("Latitude  : " + location.getLatitude());
                }
            }
        });

    }


    private void displayProducts()
    {
        Cursor cursor;
        //ArrayList<Integer> idS = new ArrayList<Integer>();
        int tempCount,priceTemp;
        for(int i=0;i<myMainClassJava.indx;i++) {
            tempCount = myMainClassJava.getCount(i);
            cursor = shopdb.getProductData(myMainClassJava.getId(i));
            priceTemp = Integer.parseInt(cursor.getString(2));
            myMainClassJava.setPrice(i,priceTemp);

            while (!cursor.isAfterLast()) {

                String temp ="";
                temp += cursor.getString(1);

                for(int j=0;j<18 - cursor.getString(1).length();j++)
                    temp+="\t";

                temp += priceTemp + "$ * ";
                temp += tempCount + "P = ";
                temp += myMainClassJava.getSubTotal(i)+"$";
                arrayList.add(temp);

              //  arrayList.add(cursor.getString(1) + "\t\t    " + priceTemp + "$\t\t    "+ tempCount + "     \t\tTotal = " + myMainClassJava.getSubTotal(i) + "$");


                listAdapter.notifyDataSetChanged();
                cursor.moveToNext();
                break;
            }
        }
        ed1.setText("Total = " + myMainClassJava.getAllTotal() + "$");

        lV.setOnItemClickListener((parent, view, position, id) -> {
            myMainClassJava.setProductind(position);///////////////////////
            Intent i = new Intent(cartPage.this, productDataDisplay.class);
            startActivity(i);
        });
    }

    public void confirmFunc(View view) {
        saveOrdersAndDetail();

        Intent i = new Intent(cartPage.this, shopPage.class);
        myMainClassJava.initDataFirstLogin();
        startActivity(i);
        finish();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        arrayList.clear();
        displayProducts();
    }

    private void saveOrdersAndDetail()
    {
        shopdb.createNewOrders(myMainClassJava.getLoginId() , ed2.getText() + "\t" + ed3.getText());
        Toast.makeText(cartPage.this,"Order Has Been Saved" , Toast.LENGTH_SHORT).show();

        int id = shopdb.getLastOrderId();

        for(int i =0 ;i<myMainClassJava.indx;i++)
        {
            shopdb.createNewOrderDetails(id, myMainClassJava.getId(i), myMainClassJava.getCount(i));
        }
    }












}




