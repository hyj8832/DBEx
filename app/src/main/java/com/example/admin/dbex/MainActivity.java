package com.example.admin.dbex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
  EditText editName,editCount,editResultName,editResultCount;
    Button butInit,butInsert,butSelect;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDb; //데이터 베이스 참조 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=(EditText)findViewById(R.id.edit_group_name);
        editCount=(EditText)findViewById(R.id.edit_group_count);
        editResultCount=(EditText)findViewById(R.id.edit_result_count);
        editResultName=(EditText)findViewById(R.id.edit_result_name);
        butInit=(Button)findViewById(R.id.but_init);
        butInsert=(Button)findViewById(R.id.but_insert);
        butSelect=(Button)findViewById(R.id.but_select);

        //DB생성
        myHelper=new MyDBHelper(this);
        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다. (첫번째 이벤트 핸들링 ..초기화 버튼 클릭했을 때)
        butInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDb=myHelper.getWritableDatabase();//참조값 대입
                myHelper.onUpgrade(sqlDb,1,2);//버전 번호
                sqlDb.close();
            }
        });
        butInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDb=myHelper.getWritableDatabase();
                //(Text)문자열을 저장할 땐 ' ' ,integer라서 뒤엔 ''가 없다.
                String sql="insert into idolTable values('"+editName.getText()+"',"+editCount.getText()+")";//값이 들어감.

                sqlDb.execSQL(sql);
                sqlDb.close();
                Toast.makeText(MainActivity.this,"저장됨",Toast.LENGTH_LONG).show();
            }
        });
        butSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDb=myHelper.getReadableDatabase();//읽기 가능한 데이터베이스
                String sql="select * from idolTable";
                Cursor cursor=sqlDb.rawQuery(sql,null);
                String names="Idol 이름"+"\r\n"+"============="+"\r\n";
                String counts="Idol 인원수"+"\r\n"+"============="+"\r\n";
                //커서를 접근할 수 없다면 false 반환
                while(cursor.moveToNext()){

                }
            }
        });

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
