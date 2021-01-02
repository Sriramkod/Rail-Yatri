package com.example.vbtt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "ttt";
    private static final String COL1 = "ID";
    private static final String COL2 = "source";
    private static final String COL3 = "destination";
    private static final String COL4 = "arrival";
    private static final String COL5 = "departure";
    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " +
                COL3 +" TEXT, " +
                COL4 +" TEXT," +
                COL5 +" TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String source,String destination,String arrival,String departure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, source);
        contentValues.put(COL3, destination);
        contentValues.put(COL4, arrival);
        contentValues.put(COL5, departure);

        //Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    /*Update1*/
    public Cursor getIndData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor data = db.rawQuery(query,null);
        Cursor data = db.rawQuery("Select * from ttt where ID=" + id + "", null);
        return data;

    }
    /*Update2 src*/
    public void updateSrc(int id, String src){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + src + "' WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }
/*Update3 des*/
public void updateDes(int id, String des){
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
            " = '" + des + "' WHERE " + COL1 + " = '" + id + "'";
    db.execSQL(query);
}
/*Update4 arr*/
public void updateArr(int id, String arr){
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
            " = '" + arr+ "' WHERE " + COL1 + " = '" + id + "'";
    db.execSQL(query);
}
/*Update5 dep*/
public void updateDep(int id, String dep){
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
            " = '" + dep + "' WHERE " + COL1 + " = '" + id + "'";
    db.execSQL(query);
}









    public Cursor getItemID(String source,String destination){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + source + "'"+ " AND " + COL3 + " = '" + destination + "'" ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }
    public void deleteName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
       // Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}