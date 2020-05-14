package com.example.indrmprojesi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class sqlite extends SQLiteOpenHelper {

    private static final String indirimler_tablosu="indirimler_tablosu";
    private static final String kullanici_tablosu="kullanici_tablosu";
    private static final String favoriler_tablosu="favoriler_tablosu";

    public sqlite(@Nullable Context context) {
        super(context,"indirimprojesi.db", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String sql ="create table " +kullanici_tablosu+ "(id integer primary key autoincrement, isim text, mail text, telefon text, parola text)";
    String sql2="create table " +indirimler_tablosu+ "(id integer primary key autoincrement, turu text, baslik text, ayrinti text, magaza text,gonderen text)";
    String sql3="create table " +favoriler_tablosu+ "(id integer primary key autoincrement, turu text, baslik text, ayrinti text, magaza text,favaekleyen text)";
    db.execSQL(sql);
    db.execSQL(sql2);
    db.execSQL(sql3);
    Log.i("qTAG", "eeeeeeeeeee");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+kullanici_tablosu);
        db.execSQL("drop table if exists "+indirimler_tablosu);
        db.execSQL("drop table if exists "+favoriler_tablosu);
        onCreate(db);
    }

    public boolean insert(String isim,String mail,String telefon,String parola){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isim",isim);
        contentValues.put("mail",mail);
        contentValues.put("telefon",telefon);
        contentValues.put("parola",parola);
        long ins = db.insert("kullanici_tablosu",null,contentValues);
        if(ins==-1) return false;
        else return true;
    }
    public boolean mailkontrol(String mail){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from kullanici_tablosu where mail=?",new String[]{mail});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    public List<indirimClass> listele(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<indirimClass> liste = new ArrayList<indirimClass>();
        Cursor c = db.rawQuery("SELECT * FROM "+indirimler_tablosu ,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            indirimClass ind=new indirimClass();
            ind.setTuru(c.getString(c.getColumnIndex("turu")));
            ind.setBaslik(c.getString(c.getColumnIndex("baslik")));
            ind.setAyrinti(c.getString(c.getColumnIndex("ayrinti")));
            ind.setMagaza(c.getString(c.getColumnIndex("magaza")));
            ind.setGonderen(c.getString(c.getColumnIndex("gonderen")));
            liste.add(ind);
            c.moveToNext();
        }
        return liste;
    }

    public boolean insertIndirim(String turu,String baslik,String ayrinti,String magaza,String gonderen){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("turu",turu);
        contentValues.put("baslik",baslik);
        contentValues.put("ayrinti",ayrinti);
        contentValues.put("magaza",magaza);
        contentValues.put("gonderen",gonderen);
        long ins = db.insert(indirimler_tablosu,null,contentValues);
        if(ins==-1) return false;
        else return true;
    }
    public boolean loginkontrol(String mail,String parola){
        SQLiteDatabase db= getReadableDatabase();
        String[] kolonlar={"id"};
        String[] arananlar={mail,parola};
        String selection="mail=? and parola=?";
        Cursor c=db.query(kullanici_tablosu,kolonlar,selection,arananlar,null,null,null);
        int count=c.getCount();
        db.close();
        if(count>0) return true;
        else return false;
    }


    public profileClass profildondur(String mail){
        SQLiteDatabase db=getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "+kullanici_tablosu ,null);
        c.moveToFirst();
        profileClass pro = new profileClass();
        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("mail")).equals(mail)){
                pro.setIsim(c.getString(c.getColumnIndex("isim")));
                pro.setMail(c.getString(c.getColumnIndex("mail")));
                pro.setTelefon_no(c.getString(c.getColumnIndex("telefon")));
            }
            c.moveToNext();
        }

        return pro;
    }
    public List<indirimClass> favlistele(String isim){
        SQLiteDatabase db = this.getReadableDatabase();
        List<indirimClass> liste = new ArrayList<indirimClass>();
        Cursor c = db.rawQuery("SELECT * FROM "+favoriler_tablosu ,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            indirimClass ind=new indirimClass();
            ind.setTuru(c.getString(c.getColumnIndex("turu")));
            ind.setBaslik(c.getString(c.getColumnIndex("baslik")));
            ind.setAyrinti(c.getString(c.getColumnIndex("ayrinti")));
            ind.setMagaza(c.getString(c.getColumnIndex("magaza")));
            ind.setFavlayan(c.getString(c.getColumnIndex("favaekleyen")));

            if(ind.getFavlayan().equals(isim)){
            liste.add(ind);}
            c.moveToNext();
        }
        return liste;
    }
    public boolean favaekle(indirimClass ind){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("turu",ind.getTuru());
        contentValues.put("baslik",ind.getBaslik());
        contentValues.put("ayrinti",ind.getAyrinti());
        contentValues.put("magaza",ind.getMagaza());
        contentValues.put("favaekleyen",ind.getFavlayan());

        long ins=db.insert(favoriler_tablosu,null,contentValues);
        if(ins==-1) return false;
        else return true;
    }
    public void favdansil(indirimClass ind){
        SQLiteDatabase db=getReadableDatabase();
        String where="baslik=?";
        String[] aranan={ind.getBaslik()};

        db.delete(favoriler_tablosu,where,aranan);
    }
    public boolean isfav(indirimClass ind){
        SQLiteDatabase db=getReadableDatabase();

        String[] kolonlar={"id"};
        String[] arananlar={ind.getBaslik(),ind.getFavlayan()};
        String selection="baslik=? and favaekleyen=?";
        Cursor c=db.query(favoriler_tablosu,kolonlar,selection,arananlar,null,null,null);
        int count=c.getCount();
        db.close();
        if(count>0) return true;
        else return false;
    }
}
