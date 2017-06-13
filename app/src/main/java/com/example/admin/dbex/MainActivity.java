package com.example.admin.dbex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    class MyDBHelper extends SQLiteOpenHelper {
        //idol이라는 이름의 DB생성된다.
        public MyDBHelper(Context context) {
            super(context, "idolDB", null, 1);
        }
        //idolTable이라는 이름의 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql="create table idolTable(idolName text nor null primary key,idolCount integer )";
            sqLiteDatabase.execSQL(sql);
        }

        @Override //이미 idolTable이 존재한다면 기존의 테이블을 삭제하고, 새로 테이블을 만들 때 호출하는
        public void onUpgrade(SQLiteDatabase  sqLiteDatabase, int oldVersion, int newVersion) {
            String sql="drop table if exist idolTable";//만약 아이돌테이블이 존재한다면 삭제해라.
            sqLiteDatabase.execSQL(sql);
            onCreate( sqLiteDatabase);

        }
    }

}
