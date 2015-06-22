package com.example.tabishhassan.customcontentprovider;

/**
 * Created by tabishhassan on 6/22/15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomerDB extends SQLiteOpenHelper{

    /** Database name */
    private static String DBNAME = "sqllistviewdemo";

    /** Version number of the database */
    private static int VERSION = 1;

    /** Field 1 of the table cust_master, which is the primary key */
    public static final String KEY_ROW_ID = "_id";

    /** Field 2 of the table cust_master, stores the customer code */
    public static final String KEY_CODE = "cust_code";

    /** Field 3 of the table cust_master, stores the customer name */
    public static final String KEY_NAME = "cust_name";

    /** Field 4 of the table cust_master, stores the phone number of the customer */
    public static final String KEY_PHONE = "cust_phone";

    /** A constant, stores the the table name */
    private static final String DATABASE_TABLE = "cust_master";

    /** An instance variable for SQLiteDatabase */
    public SQLiteDatabase mDB;

    /** Constructor */
    public CustomerDB(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    /** This is a callback method, invoked when the method
     * getReadableDatabase() / getWritableDatabase() is called
     * provided the database does not exists
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =     "create table "+ DATABASE_TABLE + " ( "
                + KEY_ROW_ID + " integer primary key autoincrement , "
                + KEY_CODE + " text  , "
                + KEY_NAME + "  text  , "
                + KEY_PHONE + "  text  ) " ;

        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + KEY_CODE + "," + KEY_NAME + "," + KEY_PHONE + " ) "
                + " values ( 'C01', 'Aji','1234567890' )";
        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + KEY_CODE + "," + KEY_NAME + "," + KEY_PHONE + " ) "
                + " values ( 'C02', 'Ajith','0123456789' )";
        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + KEY_CODE + "," + KEY_NAME + "," + KEY_PHONE + " ) "
                + " values ( 'C03', 'James','2013456789' )";
        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + KEY_CODE + "," + KEY_NAME + "," + KEY_PHONE + " ) "
                + " values ( 'C04', 'Mohammed' , '9012345678' )";
        db.execSQL(sql);

    }

    /** Returns all the customers in the table */
    public Cursor getAllCustomers(){
        return mDB.query(DATABASE_TABLE, new String[] { KEY_ROW_ID,  KEY_CODE , KEY_NAME, KEY_PHONE } ,
                null, null, null, null,
                KEY_NAME + " asc ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}