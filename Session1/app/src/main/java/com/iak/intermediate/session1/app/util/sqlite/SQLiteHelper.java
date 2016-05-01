package com.iak.intermediate.session1.app.util.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aderifaldi on 02/03/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "IAK-intermediate.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_MEMBER = "Member";

    public static final String MEMBER_ID = "MemberId";
    public static final String MEMBER_NAME = "MemberName";
    public static final String MEMBER_ADDRESS = "MemberAddress";
    public static final String MEMBER_PHOTO = "MemberPhoto";

    private static final String CREATE_TABLE_MEMBER = "CREATE TABLE if not EXISTS "
            + TABLE_MEMBER + "("
            + MEMBER_ID +" integer primary key autoincrement,"
            + MEMBER_NAME +" text,"
            + MEMBER_ADDRESS +" text,"
            + MEMBER_PHOTO +" text " +
            ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIStS " + TABLE_MEMBER);
        onCreate(db);
    }

}
