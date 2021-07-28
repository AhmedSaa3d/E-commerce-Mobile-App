package com.gp.shoppingy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

public class shopDB extends SQLiteOpenHelper {

    private static String dataBaseName = "NewshopDataBase";
    SQLiteDatabase shopDB;

    public shopDB(Context context)
    {
        super(context, dataBaseName, null , 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table categories(catID integer primary key autoincrement," +
                "catname text not null)");

        db.execSQL("create table products(prodID integer primary key autoincrement," +
                "Name text not null," +
                "price integer not null," +
                "quantity integer not null, catid integer," +
                "FOREIGN KEY(catid) REFERENCES categories (catID))");

        db.execSQL("create table customers(custID integer primary key autoincrement," +
                "Name text not null," +
                "username text not null," +
                "password text not null," +
                "birthdate text not null," +
                "job text not null," +
                "gender text not null)");

        db.execSQL("create table orders(orderID integer primary key autoincrement," +
                "orderDate text not null," +
                "address string not null, custid integer not null," +
                " FOREIGN KEY(custid) REFERENCES customers (custID))");

        db.execSQL("create table orderDetail(orderId integer not null," +
                "prodId text not null," +
                "quantity int not null," +
                " FOREIGN KEY(orderId) REFERENCES orders (orderID)," +
                " FOREIGN KEY(prodId) REFERENCES products (prodID))");

        db.execSQL("create table login(loginId integer primary key autoincrement," +
                "username text not null," +
                "password text not null," +
                "type int not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists categories");
        db.execSQL("drop table if exists products");
        db.execSQL("drop table if exists customers");
        db.execSQL("drop table if exists orders");
        db.execSQL("drop table if exists orderDetail");
        db.execSQL("drop table if exists login");
        onCreate(db);
    }
    public void InializeSomeShopData()
    {
        createNewCat("Mobile");//1
        createNewCat("Laptop");//2

        createNewProduct("redmiNote8",2500,100,1);
        createNewProduct("redmiNote7",2000,100,1);
        createNewProduct("redmiNote10",3500,100,1);

        createNewProduct("Hp",5500,100,2);
        createNewProduct("Apple",25000,100,2);

        createNewCustomer("Admin","a","a","Male","1-1-2000","DataEntry");
    }
    public void createNewCat(String catName)
    {
        ContentValues row = new ContentValues();
        row.put("catname", catName);
        shopDB = getWritableDatabase();
        shopDB.insert("categories", null, row);
        shopDB.close();
    }
    public void createNewProduct(String name, int price, int quantity, int catId)
    {
        ContentValues row = new ContentValues();
        row.put("Name", name);
        row.put("price",price);
        row.put("quantity",quantity);
        row.put("catid",catId);
        shopDB = getWritableDatabase();
        shopDB.insert("products", null, row);
        shopDB.close();
    }
    public void createNewCustomer(String name, String username, String password, String gender, String birthdate, String job)
    {
        ContentValues row = new ContentValues();
        row.put("Name", name);
        row.put("username",username);
        row.put("password",password);
        row.put("birthdate",gender);
        row.put("job",job);
        row.put("gender",birthdate);
        shopDB = getWritableDatabase();
        shopDB.insert("customers", null, row);
        shopDB.close();
    }
    public void createNewOrders(int custId, String address)
    {
        Date now = new Date();

        ContentValues row = new ContentValues();
        row.put("orderDate", now + "");
        row.put("address",address);
        row.put("custid",custId);
        shopDB = getWritableDatabase();
        shopDB.insert("orders", null, row);
        shopDB.close();
    }
    public void createNewOrderDetails(int orderId, int prodId, int quantity)
    {
        ContentValues row = new ContentValues();
        row.put("orderId", orderId);
        row.put("prodId",prodId);
        row.put("quantity",quantity);
        shopDB = getWritableDatabase();
        shopDB.insert("orderDetail", null, row);
        shopDB.close();
    }
    public void inserLogin(String name, String pass,int ty)
    {
        ContentValues row = new ContentValues();
        row.put("username", name);
        row.put("password",pass);
        row.put("type",ty);
        shopDB = getWritableDatabase();
        shopDB.insert("login", null, row);
        shopDB.close();
    }
    public Cursor getLastLogin()
    {
        shopDB = getReadableDatabase();
        String query = "select * from login";
        Cursor cursor = shopDB.rawQuery(query,null);
        if (cursor != null)
            cursor.moveToFirst();
        shopDB.close();

        return cursor;
    }
    public int getLastOrderId()
    {
        shopDB = getReadableDatabase();
        String query = "select orderID from orders";
        Cursor cursor = shopDB.rawQuery(query,null);
        if (cursor != null)
            cursor.moveToFirst();
        shopDB.close();
        int id = 1;
        while (!cursor.isAfterLast()) {
            id = Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        return id;
    }

    public Cursor getCustomer(String username,String password)
    {
        shopDB = getReadableDatabase();
        String query = "select * from customers where username = '" + username + "' AND password = '" + password + "'";
        Cursor cursor = shopDB.rawQuery(query,null);
        if (cursor != null)
            cursor.moveToFirst();
        shopDB.close();
        return cursor;
    }

    public Boolean checknameJop(String name,String jop, String pass)
    {
        shopDB = getReadableDatabase();
        String query = "select * from customers where Name = '" + name + "' AND job = '" + jop + "'";
        Cursor cursor = shopDB.rawQuery(query,null);
        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount() == 0 || cursor == null) {
            shopDB.close();
            return false;
        }
        //shopDB.execSQL("update products set quantity = quantity - " + dec + " where prodID = " + id);
        int id = Integer.parseInt(cursor.getString(0));
      try {
          shopDB.execSQL("update customers set password = " + pass + " where custID = " + id);
      }
      catch(Exception e)
      {
          return false;
      }
      shopDB.close();
        return true;
     /* db.execSQL("create table customers(custID integer primary key autoincrement," +
                "Name text not null," +
                "username text not null," +
                "password text not null," +
                "birthdate text not null," +
                "job text not null," +
                "gender text not null)");*/
    }

    public Cursor getAllProducts(String name)
    {
        String query;

        shopDB = getReadableDatabase();

        if(name == "")
            query = "select * from products where quantity >= 1";
        else
            query = "select * from products where Name LIKE '%" + name + "%' and quantity >=1";

        Cursor cursor = shopDB.rawQuery(query,null);

        if (cursor != null)
            cursor.moveToFirst();
        shopDB.close();
        return cursor;
    }

    public Cursor getProductinCategory(String name)
    {
        shopDB = getReadableDatabase();
        String query = "select * from products inner join categories on products.catid = categories.catID where catname LIKE '%" + name + "%' and quantity >=1";
        Cursor cursor = shopDB.rawQuery(query,null);
        if (cursor != null)
            cursor.moveToFirst();
        shopDB.close();
        return cursor;
    }

    public Cursor getProductData(int id)
    {
//        String quer = "Select * from employee inner join department on employee.Dept_id =department.DeptID where EmpId = " + id;

        shopDB = getReadableDatabase();
        String query = "select * from products inner join categories on products.catid = categories.catID where prodID = " + id;
        Cursor cursor = shopDB.rawQuery(query,null);
        if (cursor != null)
            cursor.moveToFirst();
        shopDB.close();
        return cursor;
    }

   public void increaseProductQuantity(int id, int inc)
    {
        shopDB = getReadableDatabase();
        shopDB.execSQL("update products set quantity = quantity + " + inc + " where prodID = " + id);
        shopDB.close();
    }
    public void decreaseProductQuantity(int id,int dec)
    {
        shopDB = getReadableDatabase();
        shopDB.execSQL("update products set quantity = quantity - " + dec + " where prodID = " + id);
        shopDB.close();
    }

}

