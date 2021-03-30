package Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DBHelper(context: Context?) : SQLiteOpenHelper(context, "myDB", null, 1) {
    private val LOG_TAG: String = "TAG"

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---")
        // создаем таблицу с полями
        db.execSQL("create table in_cart ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "prod_id integer,"
                + "price text,"
                + "image text"
                +");")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}