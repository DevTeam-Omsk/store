package Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DBHelper(context: Context?) : SQLiteOpenHelper(context, "db", null, 4) {
    private val LOG_TAG: String = "TAG"

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---")
        // создаем таблицу с полями
        db.execSQL(
            "create table in_cart ("
                    + "id integer primary key autoincrement,"
                    + "json_data text,"
                    + "prod_id text"
                    + ");"
        )

        db.execSQL(
                "create table catalog ("
                        + "id integer primary key autoincrement,"
                        + "json_data text" + ");"
        )
    }

    fun printCartInfo(db: SQLiteDatabase){
        Log.d(LOG_TAG, "--- Rows in in_cart: ---")
        // делаем запрос всех данных из таблицы in_cart, получаем Cursor
        val c: Cursor = db.query("in_cart", null, null, null, null, null, null)

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            val jsonData: Int = c.getColumnIndex("json_data")
            val prodId: Int = c.getColumnIndex("prod_id")
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(
                    LOG_TAG,
                    "\nprod_id = " + c.getString(prodId).toString() +
                            "\njsonData = " + c.getString(jsonData).toString()
                )
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext())
        } else Log.d(LOG_TAG, "0 rows")
        c.close()
    }

    fun printCatalogInfo(db: SQLiteDatabase){
        Log.d(LOG_TAG, "--- Rows in catalog: ---")
        // делаем запрос всех данных из таблицы in_cart, получаем Cursor
        val c: Cursor = db.query("catalog", null, null, null, null, null, null)

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            val jsonData: Int = c.getColumnIndex("json_data")
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG, c.getString(jsonData).toString())
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext())
        } else Log.d(LOG_TAG, "0 rows")
        c.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}