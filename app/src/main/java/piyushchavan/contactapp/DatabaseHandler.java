package piyushchavan.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by juhulabadmin on 21-11-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{

    static final String DB_NAME = "contacts.db";
    static final String TABLE_NAME = "contact";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_NUMBER = "number";
    static final String CREATE_QUERY = "create table contact(id integer primary key,name text, number text)";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, 1); // db is created
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //you have create tables
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }


    public long insertContact(Contact newContact){
        //insert into contact(id, name, number) values (1,sathish,123);
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,newContact.getName());
        contentValues.put(COLUMN_NUMBER,newContact.getNumber());
        long id = database.insert(TABLE_NAME,null,contentValues);
        return id;
    }

    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);

        /*
                Id  Name    Number
               1    Sat     12345
               2    kum     2464
               3    rohan   5454
               4    praveen 454574
         */

        cursor.moveToFirst();
        while (true){
            if(cursor.isAfterLast())
                break;

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

            Contact contact = new Contact();
            contact.setName(name);
            contact.setNumber(number);

            contactArrayList.add(contact);

            cursor.moveToNext();
        }

        cursor.close();

        return contactArrayList;

    }
}
