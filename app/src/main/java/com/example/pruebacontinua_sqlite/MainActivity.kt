package com.example.pruebacontinua_sqlite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boton = findViewById<Button>(R.id.button)
        boton.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.editTextText2).text.toString()
            val tipo = findViewById<EditText>(R.id.editTextText2).text.toString()
            val peso = findViewById<EditText>(R.id.editTextText2).text.toString().toInt()
            val precio = findViewById<EditText>(R.id.editTextText2).text.toString().toInt()
            val articulo = Articulo("Arma", "Espada", peso, precio)
            var intent = Intent(this@MainActivity, Mostrar::class.java)
            startActivity(intent)
        }
    }

    class Articulo(private var tipoArticulo: String, private var nombre: String, private var peso: Int, private var precio: Int) {

        fun getPeso(): Int {
            return peso
        }
        fun getNombre(): String {
            return nombre
        }
        fun getTipoArticulo(): String {
            return tipoArticulo
        }
        fun getPrecio(): Int{
            return precio
        }
        override fun toString(): String {
            return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
        }
    }
    class Mochila(context: Context){
        private var contenido=ArrayList<Articulo>()
        val dbHelper = BaseDatos(this)
        fun getNumeroArticulos():Int{
            return 1
        }
        fun addArticulo(articulo: Articulo) {
            val dbHelper = BaseDatos(this)
            dbHelper.addArticulo(articulo)
        }
        fun getContenido(): String {
            val arrayArticulos = dbHelper.getArticulos()
            val arrayArc = arrayArticulos.joinToString("\n")
            return arrayArc
        }
        fun findObjeto(nombre: String): Int {
            val arrayArticulos = dbHelper.getArticulos()
            return arrayArticulos.indexOfFirst { it.getNombre() == nombre }
        }
        override fun toString(): String {
            return if (contenido.isEmpty()) {
                "Mochila vacía"
            } else {
                "Artículos en la mochila: ${contenido.joinToString("\n")}"
            }
        }
    }
}