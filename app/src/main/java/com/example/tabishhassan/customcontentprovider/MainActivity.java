package com.example.tabishhassan.customcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;
    ListView mListView;
    EditText textphone,textname,textid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textid = (EditText)findViewById(R.id.editText1);
        textname = (EditText)findViewById(R.id.editText2);
        textphone = (EditText)findViewById(R.id.editText3);
        mListView = (ListView) findViewById(R.id.listview);
        Button btn_add = (Button)findViewById(R.id.button);
        Button btn_delete = (Button)findViewById(R.id.button2);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Customer.KEY_CODE,textid.getText().toString());
                values.put(Customer.KEY_NAME,textname.getText().toString());
                values.put(Customer.KEY_PHONE, textphone.getText().toString());
                Uri uri = getContentResolver().insert(
                        Customer.CONTENT_URI, values);

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = textid.getText().toString();
                String[] array = {Customer.KEY_CODE,Customer.KEY_NAME,Customer.KEY_PHONE};
                getContentResolver().delete(Customer.CONTENT_URI,"cust_code = " + id,null);
                getSupportLoaderManager().initLoader(0, null, MainActivity.this);
            }
        });


        mAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_item_layout,
                null,
                new String[] { CustomerDB.KEY_CODE, CustomerDB.KEY_NAME, CustomerDB.KEY_PHONE},
                new int[] { R.id.code , R.id.name, R.id.phone }, 0);

        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);

        /** Creating a loader for populating listview from sqlite database */
        /** This statement, invokes the method onCreatedLoader() */
        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = Customer.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    /** A callback method, invoked after the requested content provider returned all the data */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        mAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
    }
}