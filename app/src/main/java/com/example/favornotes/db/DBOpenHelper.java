package com.example.favornotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.favornotes.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context,"favornote.db" , null, 1);
    }

    //    创建数据库的方法，只有项目第一次运行时，会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
//        创建表示类型的表
        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);
        //创建记账表
        sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer," +
                "name varchar(10),reason varchar(80), money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db) {
//      向typetb表当中插入元素
        String sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_qita,R.mipmap.ic_qita_fs,0});
        db.execSQL(sql,new Object[]{"朋友", R.mipmap.ic_pengyou,R.mipmap.ic_pengyou_fs,0});
        db.execSQL(sql,new Object[]{"同学", R.mipmap.ic_tongxue,R.mipmap.ic_tongxue_fs,0});
        db.execSQL(sql,new Object[]{"同事", R.mipmap.ic_tongshi,R.mipmap.ic_tongshi_fs,0});
        db.execSQL(sql,new Object[]{"亲戚", R.mipmap.ic_qinqi,R.mipmap.ic_qinqi_fs,0});


        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_qt,R.mipmap.in_qt_fs,1});
        db.execSQL(sql,new Object[]{"朋友", R.mipmap.in_py,R.mipmap.in_py_fs,1});
        db.execSQL(sql,new Object[]{"同学", R.mipmap.in_tx,R.mipmap.in_tx_fs,1});
        db.execSQL(sql,new Object[]{"同事", R.mipmap.in_ts,R.mipmap.in_ts_fs,1});
        db.execSQL(sql,new Object[]{"亲戚", R.mipmap.in_qq,R.mipmap.in_qq_fs,1});


    }

    // 数据库版本在更新时发生改变，会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
