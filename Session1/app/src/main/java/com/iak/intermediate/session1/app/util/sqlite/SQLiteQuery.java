package com.iak.intermediate.session1.app.util.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iak.intermediate.session1.app.model.local.MemberLocal;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aderifaldi on 02/03/2016.
 */
public class SQLiteQuery {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public SQLiteQuery(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //INSERT MEMBER
    public void insertMember(MemberLocal data){

        open();
        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.MEMBER_NAME, data.getName());
        values.put(SQLiteHelper.MEMBER_ADDRESS, data.getAddress());
        values.put(SQLiteHelper.MEMBER_PHOTO, data.getPhoto());

        database.insert(SQLiteHelper.TABLE_MEMBER, null, values);
        database.close();
    }

    //SELECT MEMBER
    public List<MemberLocal> selectMember(){
        List<MemberLocal> data = new LinkedList<MemberLocal>();

        String query = "SELECT "
                + SQLiteHelper.MEMBER_NAME + ", "
                + SQLiteHelper.MEMBER_ADDRESS + ", "
                + SQLiteHelper.MEMBER_PHOTO +
                " FROM "
                + SQLiteHelper.TABLE_MEMBER;
        Log.d("db", "query " + query);

        Cursor cursor = database.rawQuery(query, null);
        MemberLocal value = null;

        if (cursor.moveToFirst()) {
            do {
                value = new MemberLocal();
                value.setName(cursor.getString(0));
                value.setAddress(cursor.getString(1));
                value.setPhoto(cursor.getString(2));

                data.add(value);
            } while (cursor.moveToNext());
        }

        return data;
    }

    //CLEAR TABLE
    public void clearTableMember(){
        database.execSQL("delete from " + SQLiteHelper.TABLE_MEMBER);
    }


}
