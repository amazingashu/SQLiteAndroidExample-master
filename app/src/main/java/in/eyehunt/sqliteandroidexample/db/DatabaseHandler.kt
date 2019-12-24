package `in`.eyehunt.sqliteandroidexample.db

import `in`.eyehunt.sqliteandroidexample.model.Users
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($ID Integer PRIMARY KEY, $EMAIL TEXT,$FIRST_NAME TEXT, $LAST_NAME TEXT)"
        db?.execSQL(CREATE_TABLE)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }

    //Inserting (Creating) data
    fun addUser(user: Users): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EMAIL, user.email)
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    //get all users
    fun getAllUsers(): String {
        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var email = cursor.getString(cursor.getColumnIndex(EMAIL))
                    var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))

                    allUser = "$allUser\n$id $firstName $lastName $email"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }


    fun getUsers(_id:Int): String {
        var User: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME  where $ID=$_id"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
               while(cursor.moveToNext()) {
                   var id = cursor.getString(cursor.getColumnIndex(ID))
                   var email = cursor.getString(cursor.getColumnIndex(EMAIL))
                   var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                   var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))
                    User="$email,$firstName,$lastName"
               }


        }
        cursor.close()
        db.close()
        return User
    }

    companion object {
        private val DB_NAME = "AllUsers"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "MyUsers"

        private val ID = "id"
        private val EMAIL = "Email"
        private val FIRST_NAME = "FirstName"
        private val LAST_NAME = "LastName"
    }
}