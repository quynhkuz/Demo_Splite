package com.example.demosplite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MyDBHelper(context: Context) :
    SQLiteOpenHelper(context, DBName, null, VERSION) {
    private var myDB: SQLiteDatabase? = null
    override fun onCreate(db: SQLiteDatabase) {
        // tao database
        val queryTable = "CREATE TABLE " + TABLE_NAME + " ( " + iD +
                " INTEGER PRIMARY KEY autoincrement," + sbd + " TEXT NOT NULL, " +
                ten + " TEXT NOT NULL, " + dToan + " FLOAT NOT NULL," + dLy + " FLOAT NOT NULL," + dHoa + " FLOAT NOT NULL" + ")"

        Log.d("AAA",queryTable.toString())
        db.execSQL(queryTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun openDB() {
        myDB = writableDatabase
    }

    fun closeDB() {
        if (myDB != null && myDB!!.isOpen) {
            myDB!!.close()
        }
    }

    // insert
    fun Insert(sv : DataModel): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(sbd, sv.sbd)
        values.put(ten, sv.hoten)
        values.put(dToan, sv.toan)
        values.put(dLy, sv.ly)
        values.put(dHoa, sv.hoa)
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateSV(sv:DataModel): Long {
        val values = ContentValues()
        values.put(iD, sv.id)
        values.put(sbd, sv.sbd)
        values.put(ten, sv.hoten)
        values.put(dToan, sv.toan)
        values.put(dLy, sv.ly)
        values.put(dHoa, sv.hoa)
        val where = iD + " = " + sv.id
        return myDB!!.update(TABLE_NAME, values, where, null).toLong()
    }

    fun deleteSV(id: Int): Long {
        Log.d("AAA", "id $id")
        val where = "$iD = $id"
        return myDB!!.delete(TABLE_NAME, where, null).toLong()

    }

    fun DisplayAll(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        return myDB!!.rawQuery(query, null)
    }

    companion object {
        private const val DBName = "mydb.db"
        private const val VERSION = 1
        private const val TABLE_NAME = "Sinhvien"
        const val iD = "_id"
        const val sbd = "sbd"
        const val ten = "name"
        const val dToan = "toan"
        const val dLy = "ly"
        const val dHoa = "hoa"
    }
}