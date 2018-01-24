package com.example.sonu.updatefirebase.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.sonu.updatefirebase.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONU on 1/24/2018.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="EatitDB.db";
    private static final int DB_VER=1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts()
    {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect={"ProductName","ProductId","Quantity","Price","Discount"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(database,sqlselect,null,null,null,null,null);

        final List<Order> result=new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addtoCart(Order order)
    {
        SQLiteDatabase database= getReadableDatabase();
        String query =String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');"
        ,order.getProductId(),order.getProductName(),order.getQuantity(),order.getPrice(),order.getDiscount());
        database.execSQL(query);

    }
    public void cleanCart()
    {
        SQLiteDatabase database= getReadableDatabase();
        String query =String.format("DELETE FROM OrderDetail");
        database.execSQL(query);
    }

}
