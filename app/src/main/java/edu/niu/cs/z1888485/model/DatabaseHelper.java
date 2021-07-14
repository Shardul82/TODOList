package edu.niu.cs.z1888485.model;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  DatabaseHelper                                 *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: DatabaseHelper is used to add, delete, retrieve    *
 *          items in the database. The methods defined are     *
 *          onCreate, onUpgrade, insertItem, retrieveItems,    *
 *          updateItem, deleteItem and deleteAll               *
 *                                                             *
 ***************************************************************/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "list.db";
    public static final String TABLE_NAME = "list_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String version = "1";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    // onCreate method is used here to create table in database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("+ COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                                        COL_2 + " TEXT" + ")");
    }

    // onUpgrade is used here if table already exists.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if  exists " + TABLE_NAME);
    }

    /****************************************************************
     *                                                              *
     * Method Name:  insertItem                                     *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to insert item in the database  *
     *          table. If resultOfOperation is -1, it will return   *
     *          false means operation failed or true if operation   *
     *          successful.                                         *
     *                                                              *
     ***************************************************************/
    public boolean insertItem(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);

        long resultOfOperation = db.insert(TABLE_NAME, null, contentValues);

        if(resultOfOperation == -1){
            return  false; // operation failed
        }
        else{
            return true; // test passed
        }
    }

    /****************************************************************
     *                                                              *
     * Method Name:  retrieveItems                                  *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to retrieve item from the       *
     *          database table.                                     *
     *                                                              *
     ***************************************************************/
    public ArrayList<Item> retrieveItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);

        ArrayList itemArrayList = new ArrayList();

        if(cursor.getCount() != 0){
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                Item item = new Item(cursor.getInt(0),
                                        cursor.getString(1));
                itemArrayList.add(item);
            }
        }
        return itemArrayList;
    }

    /****************************************************************
     *                                                              *
     * Method Name:  updateItem                                     *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to update item in the           *
     *          database table.                                     *
     *                                                              *
     ***************************************************************/
    public Boolean updateItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, item.getId());
        contentValues.put(COL_2, item.getDescription());
        db.update(TABLE_NAME, contentValues, "id = ?",new String[]{item.getId()+""});
        return true;
    }

    /****************************************************************
     *                                                              *
     * Method Name:  deleteItem                                     *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to delete selected item from    *
     *          the database table.                                 *
     *                                                              *
     ***************************************************************/
    public Boolean deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id = ?",new String[]{item.getId()+""});
        return true;
    }

    /****************************************************************
     *                                                              *
     * Method Name:  deleteAll                                      *
     *                                                              *
     *                                                              *
     * Purpose: This method is called when startOver method is      *
     *          called. It will delete all the items from the       *
     *          database table.                                     *
     *                                                              *
     ***************************************************************/
    public Boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_NAME);
        return true;
    }
}
