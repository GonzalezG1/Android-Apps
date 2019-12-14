package com.example.stockwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
//From professer's example, just modified to fit this assignment

    private static final String TAG = "DBHandler";
    private static final String dbName = "StockDB";
    private static final int dbVersion = 1;
    private SQLiteDatabase sqldb;


    public DBHandler(Context context){
        super(context, dbName, null, dbVersion);
        sqldb = getWritableDatabase();
    }

    private static final String stockTable = "stockTable";
    private static final String symbol = "SYMBOL";
    private static final String name = "NAME";


    private static String createCommand = "CREATE TABLE " + stockTable + " (" + symbol + " TEXT not null unique, " + name + " TEXT not null)";


    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int oldVer, int newVer){
        return;
    }


    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        sqldb.execSQL(createCommand);
    }


    public void shutDown(){
        sqldb.close();
    }

    public void delStock(String symbolValue){

        int cnt = sqldb.delete(stockTable, symbol + " = ?", new String[]{symbol});

    }

    public void addStock(String symbolValue, String nameValue){

        ContentValues stockValues = new ContentValues();
        stockValues.put(symbol, symbolValue);
        stockValues.put(name, nameValue);


        long key = sqldb.insert(stockTable, null, stockValues);

    }

    //Returns the stocks that are in the database
    public ArrayList<String[]> getStocks(){

        ArrayList<String[]> stockArrayList = new ArrayList<String[]>();


        Cursor c = sqldb.query(stockTable, new String[]{symbol, name}, null, null, null, null, null);


        if(c!=null){
            if(c.moveToFirst()){ //moves cursor to first row
                for(int i = 0; i < c.getCount(); i++){
                    //get the values for s=symbol n=name, add to array list, move the cursor
                    String s = c.getString(0);
                    String n = c.getString(1);
                    stockArrayList.add(new String[]{s, n});
                    Log.d(TAG, "getStocks: Added another stock to array list");
                    c.moveToNext();
                }
                c.close();
            }
        }
        Log.d(TAG, "getStocks: Done adding stocks to arraylist");
        return stockArrayList;
    }

    public void dumpLog(){

        Cursor c = sqldb.query(stockTable, new String[]{symbol, name}, null, null, null, null, null);


        if(c!=null){
            if(c.moveToFirst()) { //moves cursor to first row
                for (int i = 0; i < c.getCount(); i++) {
                    //getting the values for s = symbol n =name
                    String s = c.getString(0);
                    String n = c.getString(1);

                    Log.d(TAG, "dumpLog: SYMBOL:" + s + " NAME:" + n);
                    c.moveToNext();
                }
                c.close();
            }
        }
    }
}
