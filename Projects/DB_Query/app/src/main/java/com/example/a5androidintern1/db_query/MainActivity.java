package com.example.a5androidintern1.db_query;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    final String LOG_TAG = "myLog";

    String name[] = {"Китай", "США", "Бразлия", "Россия", "Япония", "Германия", "Египет", "Италия", "Франция", "Канада" } ;
    int people[] = {1400, 311, 195, 142, 128, 82, 80, 60,66, 35};
    String region[] = {"Азия", "Америка", "Америка", "Европа", "Азия", "Европа", "Африка", "Европа", "Европа", "Америка" } ;
    Button btnAll, btnFunc, btnPeople, btnGroup, btnHaving, btnSort;
    EditText etFunc, etPeople, etRegionPeople;
    RadioGroup rgSort;

    SQLiteDatabase db;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAll =(Button)findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);
        btnFunc =(Button)findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);
        btnPeople =(Button)findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);
        btnGroup =(Button)findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);
        btnHaving =(Button)findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);
        btnSort =(Button)findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        etFunc =(EditText) findViewById(R.id.etFunc);
        etPeople = (EditText)findViewById(R.id.etPeople);
        etRegionPeople = (EditText)findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup)findViewById(R.id.rgSort);
        dbHelper = new DBHelper(this);
        //Подкличение к бд для записи и чтения
        db = dbHelper.getWritableDatabase();

        //проверка существования записей
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if(c.getCount()==0){
            ContentValues cv = new ContentValues();
            //заполнение табицы
            for (int i=0; i<10;i++){
                cv.put("name", name[i]);
                cv.put("people",people[i]);
                cv.put("region", region[i]);
                Log.d(LOG_TAG,"id = "+ db.insert("mytable",null,cv));
            }


        }
        c.close();
        dbHelper.close();
        onClick(btnAll);
    }

    @Override
    public void onClick(View view) {
        //Подкличение к бд для записи и чтения
        db = dbHelper.getWritableDatabase();
        //данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        //переменные для query

        String columns[] = null;
        String selection = null;
        String selectionArgs[] = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        //курсор
        Cursor c = null;

        //определяем нажатую кнопку
        switch (view.getId()){
            case R.id.btnAll:
                Log.d(LOG_TAG,"----все записи----");
                c = db.query("mytable", null, null, null, null, null, null);


                break;
            //функция
            case R.id.btnFunc:
                Log.d(LOG_TAG,"--- Функция "+ sFunc +" ---");
                columns = new String[] {sFunc};
                c = db.query("mytable", columns, null, null, null, null, null);
                break;
            case R.id.btnPeople:
                Log.d(LOG_TAG,"--- Население больше "+ sPeople +" ---");
                selection = "people > ?";
                selectionArgs = new String[] {sPeople};
                c = db.query("mytable",null,selection,selectionArgs,null, null,null);
                break;
            case R.id.btnGroup:
                Log.d(LOG_TAG,"--- Население по региону ---");
                columns = new String[]{"region","sum(people) as people" };
                groupBy = "region";
                c = db.query("mytable",columns,null,null,groupBy, null,null);
                break;
            case R.id.btnHaving:
                Log.d(LOG_TAG,"--- Регионы с населением больше "+ sRegionPeople +" ---");
                columns = new String[]{"region","sum(people) as people" };
                groupBy = "region";
                having = "sum(people) >"+ sRegionPeople;
                c = db.query("mytable",columns,null,null,groupBy, having,null);
                break;
            case  R.id.btnSort:
                switch (rgSort.getCheckedRadioButtonId()){
                    case R.id.rbName:
                        Log.d(LOG_TAG,"--- Cортировка по наименованию ---");
                        orderBy="name";
                        break;
                    case R.id.rbRegion:
                        Log.d(LOG_TAG,"--- Cортировка по региону ---");
                        orderBy="region";
                        break;
                    case R.id.rbPeople:
                        Log.d(LOG_TAG,"--- Cортировка по населению ---");
                        orderBy="people";
                        break;
                }
                c = db.query("mytable",null,null,null,null, null,orderBy);
                break;

        }
        if(c!=null){
            if(c.moveToFirst()){
                String str;
                do{
                    str = "";
                    for(String cn : c.getColumnNames()){
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn))+ ";");
                    }
                    Log.d(LOG_TAG,str);
                }while (c.moveToNext());

            }c.close();
        }else
            Log.d(LOG_TAG,"Cursor is null");
        dbHelper.close();


    }
    class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context){
            super(context,"myDB", null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d(LOG_TAG,"--- onCreate DataBase ---");

            db.execSQL("create table mytable ("+
            "id integer primary key autoincrement,"+
            "name text,"+"people integer,"+"region text"+");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
