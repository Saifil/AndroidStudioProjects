package com.example.saifil.arcaninedataapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper { //created to define the database

    //declare the DB variables
    private static final int DATABASE_VERSION = 1; //version of db
    private static final String DATABASE_NAME = "movesets.db"; //name od db
    private static final String TABLE_MOVESETS = "movesets"; //table name
    private static final String COLUMN_ID = "_id"; //table's column id name
    private static final String COLUMN_MOVESETNAME = "movesetname"; //column name for movesetname

    //constructor
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //Used to set up a database

        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create a table
        String query = "CREATE TABLE " + TABLE_MOVESETS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MOVESETNAME + " TEXT );";

        //execute the query
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //use this method to update the table

        //drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVESETS + ";");
        //create new table
        onCreate(sqLiteDatabase);

    }

    public void addMoveset(Movesets movesets) { //used to add values to the database


        //create an reference to ContentValue obj.
        ContentValues values = new ContentValues();

        //combines the data to be stored in the DB
        values.put(COLUMN_MOVESETNAME,movesets.get_moveName());
        /*
        first param is the moveset name column's name
        second param is the inserting item's name itself
         */

        //getWritable when we want to write to the DB
        SQLiteDatabase db = getWritableDatabase();

        //insert value in the DB
        db.insert(TABLE_MOVESETS,null,values);
        /*
        first param is the table name
        second param is null
        third param is the value to store
         */

        //close the DB
        db.close();

    }

    public void deleteMoveset(String moveName) { //used to delete entry from the DB

        //getWritable when we want to write to the DB
        SQLiteDatabase db = getWritableDatabase();

        //query to delete entry
        String query = "DELETE FROM " + TABLE_MOVESETS
                + " WHERE " + COLUMN_MOVESETNAME + " = \"" + moveName + "\";";
        db.execSQL(query); // execute the query

    }

    public String dbToString() { //returns printable string value

        String dbString = "";

        //getWritable when we want to write to the DB
        SQLiteDatabase db = getWritableDatabase();

        //retrieve entry from the data base
        String query = "SELECT * "
                + "FROM " + TABLE_MOVESETS + " WHERE 1";

        //declare a cursor which points to currently accesses table row
        Cursor cs = db.rawQuery(query,null);

        cs.moveToFirst(); //places the cursor to first position

        while (!cs.isAfterLast()) { //runs through every entry of the table

            //check if the moveset's name entry is not null
            if (cs.getString(cs.getColumnIndex(COLUMN_MOVESETNAME)) != null) {

                //retrieves the entry from corcor using the column number
                dbString += cs.getString(cs.getColumnIndex(COLUMN_MOVESETNAME));
                dbString += "\n";
            }

            cs.moveToNext(); // moves the cursor to next entry
        }

        db.close(); //close the DB

        return dbString; // return the printable value
    }

}
