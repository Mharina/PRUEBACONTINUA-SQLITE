package com.example.pruebacontinua_sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(contex: MainActivity.Mochila) : SQLiteOpenHelper (contex, DATABASE, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE="BDMochila.db"
        private const val TABLA_MOCHILA= "mochila"
        private const val KEY_ID= "id"
        private const val TIPO_ARTICULO= "tipo"
        private const val NOMBRE= "nombre"
        private const val PESO= "peso"
        private const val PRECIO= "precio"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_MOCHILA($KEY_ID INTEGER PRIMARY KEY, $TIPO_ARTICULO TEXT , $NOMBRE TEXT, $PESO INTEGER, $PRECIO INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA")
        onCreate(db)
    }
    fun addArticulo(articulo: MainActivity.Articulo){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TIPO_ARTICULO, articulo.getNombre())
            put(NOMBRE, articulo.getNombre())
            put(PESO, articulo.getPeso())
            put(PRECIO, articulo.getPrecio())
        }
        db.insert(TABLA_MOCHILA, null, values)
        db.close()
    }
    @SuppressLint("Range")
    fun getArticulos(): ArrayList<MainActivity.Articulo>{
        val mochila = ArrayList<MainActivity.Articulo>()
        val selectQuery = "SELECT * FROM $TABLA_MOCHILA"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val tipo = cursor.getString(cursor.getColumnIndex(TIPO_ARTICULO))
            val nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
            val peso = cursor.getInt(cursor.getColumnIndex(PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(PRECIO))
            mochila.add(MainActivity.Articulo(tipo, nombre, peso, precio))
        }
        cursor.close()
        db.close()
        return mochila
    }
}