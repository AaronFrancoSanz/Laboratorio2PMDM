package com.example.laboratorio2pmdm

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
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

        // 1. Nombre
        val nombreEditText = findViewById<EditText>(R.id.editTextText2)
        nombreEditText.requestFocus()

        // Registrar nombre cuando se escribe
        nombreEditText.setOnFocusChangeListener { _, tieneFoco ->
            if (!tieneFoco) {
                Log.d("NOMBRE", "Nombre: ${nombreEditText.text}")
            }
        }

        // 2. Raza - Spinner
        val spinner = findViewById<Spinner>(R.id.spinner)

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

        // Log individual del Spinner
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("RAZA", "Raza seleccionada: ${razas[position]}")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("RAZA", "No se ha seleccionado ninguna raza")
                }
            }

        // 3. Facción
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val radioComunidad = findViewById<RadioButton>(R.id.radioButton)
        val radioMordor = findViewById<RadioButton>(R.id.radioButton2)

        // Comunidad del Anillo
        radioComunidad.setOnClickListener {
            Log.d("FACCION", "Facción seleccionada: Comunidad del Anillo")
        }

        // Huestes de Mordor
        radioMordor.setOnClickListener {
            Log.d("FACCION", "Facción seleccionada: Huestes de Mordor")
        }

        // 4. Habilidades
        val checkBoxSigilo = findViewById<CheckBox>(R.id.checkBox2)
        val checkBoxCombate = findViewById<CheckBox>(R.id.checkBox)

        // Sigilo
        checkBoxSigilo.setOnCheckedChangeListener { _, seleccionado ->
            if (seleccionado) {
                Log.d("HABILIDAD", "Habilidad seleccionada: Sigilo")
            } else {
                Log.d("HABILIDAD", "Habilidad deseleccionada: Sigilo")
            }
        }

        // Combate con Espada
        checkBoxCombate.setOnCheckedChangeListener { _, seleccionado ->
            if (seleccionado) {
                Log.d("HABILIDAD", "Habilidad seleccionada: Combate con Espada")
            } else {
                Log.d("HABILIDAD", "Habilidad deseleccionada: Combate con Espada")
            }
        }

        // 5. Botón de registro
        val botonRegistro = findViewById<ImageButton>(R.id.imageButton2)

        val registrarEnLogcat: (String) -> Unit = { personaje ->
            Log.d("REGISTRO", personaje)
        }

        botonRegistro.setOnClickListener {

            val nombre = nombreEditText.text.toString()
            val raza = spinner.selectedItem.toString()

            val faccion = when (radioGroup.checkedRadioButtonId) {
                R.id.radioButton -> "Comunidad del Anillo"
                R.id.radioButton2 -> "Huestes de Mordor"
                else -> "Sin facción"
            }

            val habilidades = mutableListOf<String>()

            if (checkBoxSigilo.isChecked) {
                habilidades.add("Sigilo")
            }

            if (checkBoxCombate.isChecked) {
                habilidades.add("Combate con Espada")
            }

            if (habilidades.isEmpty()) {
                habilidades.add("Ninguna")
            }

            val personaje = """
                Nombre: $nombre
                Raza: $raza
                Facción: $faccion
                Habilidades: ${habilidades.joinToString(", ")}
            """.trimIndent()

            Toast.makeText(
                this,
                personaje,
                Toast.LENGTH_LONG
            ).show()

            registrarEnLogcat(personaje)
        }
    }
}
