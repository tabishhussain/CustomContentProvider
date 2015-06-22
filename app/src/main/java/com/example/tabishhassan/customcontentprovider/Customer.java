package com.example.tabishhassan.customcontentprovider;

/**
 * Created by tabishhassan on 6/22/15.
 */
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.sql.SQLException;

/** A custom Content Provider to do the database operations */
public class Customer extends ContentProvider{

    public static final String PROVIDER_NAME = "com.example.tabishhassan.customcontentprovider.customer";

    /** A uri to do operations on cust_master table. A content provider is identified by its uri */
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/customers" );

    private SQLiteDatabase db;
    public static String STUDENTS_TABLE_NAME = "cust_master";

    /** Constants to identify the requested operation */
    private static final int CUSTOMERS = 1;
    public static final String KEY_CODE = "cust_code";

    /** Field 3 of the table cust_master, stores the customer name */
    public static final String KEY_NAME = "cust_name";

    /** Field 4 of the table cust_master, stores the phone number of the customer */
    public static final String KEY_PHONE = "cust_phone";

    private static final UriMatcher uriMatcher ;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "customers", CUSTOMERS);
    }

    /** This content provider does the database operations by this object */
    CustomerDB mCustomerDB;

    /** A callback method which is invoked when the content provider is starting up */
    @Override
    public boolean onCreate() {
        mCustomerDB = new CustomerDB(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /** A callback method which is by the default content uri */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if(uriMatcher.match(uri)==CUSTOMERS){
            return mCustomerDB.getAllCustomers();
        }else{
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case CUSTOMERS:
                count = mCustomerDB.mDB.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = mCustomerDB.mDB.insert(STUDENTS_TABLE_NAME, "", values);

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        else
            return null;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0 ;
        switch ((uriMatcher.match(uri))) {
            case CUSTOMERS:
                count  = mCustomerDB.mDB.update(STUDENTS_TABLE_NAME, values, selection, selectionArgs);
        }
        return count;
    }
}