package com.example.laboratorio2pmdm

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // 1. Nombre: EditText con foco automático
        val nombreEditText = findViewById<EditText>(R.id.editTextText2)
        nombreEditText.requestFocus()

        val spinner = findViewById<Spinner>(R.id.spinner)

// 2. Raza: Spinner
        val razas = resources.getStringArray(R.array.razas)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            razas
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter

        // Lambda para registrar la estructura elegida en Logcat
        val registrarGuerrero: (String) -> Unit = { guerrero ->
            Log.d("GUERRERO", guerrero)
        }

        // 5. Botón de registro
        val botonRegistro = findViewById<ImageButton>(R.id.imageButton2)

        botonRegistro.setOnClickListener {

            // Nombre
            val nombre = nombreEditText.text.toString()

            // Raza
            val raza = spinner.selectedItem.toString()

            // 3. Facción
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

            val faccion = when (radioGroup.checkedRadioButtonId) {
                R.id.radioButton -> "Comunidad del Anillo"
                R.id.radioButton2 -> "Huestes de Mordor"
                else -> "Sin facción"
            }

            // 4. Habilidades especiales
            val sigilo = findViewById<android.widget.CheckBox>(
                R.id.checkBox2
            ).isChecked

            val combate = findViewById<android.widget.CheckBox>(
                R.id.checkBox
            ).isChecked

            val habilidades = mutableListOf<String>()

            if (sigilo) {
                habilidades.add("Sigilo")
            }

            if (combate) {
                habilidades.add("Combate con Espada")
            }

            if (habilidades.isEmpty()) {
                habilidades.add("Ninguna")
            }

            // Estructura final del personaje
            val personaje = """
                Nombre: $nombre
                Raza: $raza
                Facción: $faccion
                Habilidades: ${habilidades.joinToString(", ")}
            """.trimIndent()

            // Toast con el resumen
            Toast.makeText(
                this,
                personaje,
                Toast.LENGTH_LONG
            ).show()

            // Registrar mediante lambda en Logcat
            registrarGuerrero(personaje)
        }
    }
}
