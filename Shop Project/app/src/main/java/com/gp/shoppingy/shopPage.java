package com.gp.shoppingy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class shopPage extends AppCompatActivity {

    EditText ed1;
    Button btn1,btn2;
    ListView lV;
    Cursor cursor;
    shopDB shopdb;
    public ArrayList<Integer> idSelect;
    ArrayList<String> arrayList;

    ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_page);

       // customerId = getIntent().getExtras().getInt("id");
        ed1 = findViewById(R.id.shopproductText);
        btn1 = findViewById(R.id.searchBtn);
        btn2 = findViewById(R.id.cartBtn);
        lV = findViewById(R.id.listId);
        shopdb= new shopDB(this);
        
        idSelect = new ArrayList<Integer>();
        
        arrayList = new ArrayList<String>();
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        lV.setAdapter(listAdapter);

        /*we get all the products*/

        getAvaProducts();

    }

    private void getAvaProducts()
    {
       if(myMainClassJava.typeSearch.equals("P"))
           cursor = shopdb.getAllProducts(myMainClassJava.searchContent);

       else if(myMainClassJava.typeSearch.equals("C"))
          cursor = shopdb.getProductinCategory(myMainClassJava.searchContent);

       else
        cursor = shopdb.getAllProducts(""); // 'A' All data

        ArrayList<Integer> idS = new ArrayList<Integer>();
        ed1.setText(myMainClassJava.searchContent);
        while(!cursor.isAfterLast()) {
            if(cursor.isNull(0))
                break;

            String temp ="";
            temp += cursor.getString(1);
            for(int i=0;i<23 - cursor.getString(1).length();i++)
                temp+="\t";

            temp += cursor.getString(2) + "$";
            for(int i=0;i<15 - cursor.getString(2).length();i++)
                temp+="\t";
            temp += cursor.getString(3) + "P";

            arrayList.add(temp);
            listAdapter.notifyDataSetChanged();
            idS.add(Integer.parseInt(cursor.getString(0)));
            cursor.moveToNext();
        }
        if(arrayList.isEmpty())
            Toast.makeText(shopPage.this,"No Data Found" , Toast.LENGTH_SHORT).show();

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shopdb.decreaseProductQuantity(idS.get(position),1);
                myMainClassJava.addSelectid(idS.get(position));
                arrayList.clear();
                getAvaProducts();
            }
        });

    }





    @Override
    protected void onRestart()
    {
        super.onRestart();
        arrayList.clear();
        listAdapter.notifyDataSetChanged();
        getAvaProducts();
    }

 // search page
    public void searchFunc(View view) {
        Intent i = new Intent(shopPage.this, searchPage.class);
        startActivity(i);
    }
//cart page
    public void goToCartFunc(View view) {
       if(myMainClassJava.indx > 0) {
           Intent i = new Intent(shopPage.this, cartPage.class);
           startActivity(i);
       }
       else
        Toast.makeText(shopPage.this,"No Items Bought" , Toast.LENGTH_SHORT).show();

    }
}