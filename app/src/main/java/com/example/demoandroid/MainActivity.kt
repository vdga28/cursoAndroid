package com.example.demoandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var nameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var emailField: EditText
    private lateinit var lastNameField: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameField = findViewById(R.id.name_field)
        emailField = findViewById(R.id.email_field)
        passwordField = findViewById(R.id.password_field)
        lastNameField = findViewById(R.id.lastname_field)
        lastname_field.setOnFocusChangeListener { v, hasFocus ->
            if (lastname_field.checkEmpty()) {
                showToast("campo vacio")
            }
        }
        passwordField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length > 6) {
                    showToast("clave segura")
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }
        }
        )
        button = findViewById(R.id.button)
        button.setOnClickListener {
            validateFieldNotEmpty()
        }
    }
    private fun showGenericErrorDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Campos Vacios")
        builder.setMessage("Tenes campos vacios")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            showToast("Tenes campos vacios")
            nameField.requestFocus()
        }
        builder.show()
    }
    private fun showOkDialog(context: Context, title: String, description: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(description)
        builder.setPositiveButton("Aceptar", null)
        builder.show()
    }
    private fun validateFieldNotEmpty() {
        if (emailField.checkEmpty() || nameField.checkEmpty() || passwordField.checkEmpty() || lastname_field.checkEmpty()) {
            showGenericErrorDialog(this)
        } else {
            sendData()
        }
    }
    private fun showToast(name: String) {
        Toast.makeText(this, "Hola $name", Toast.LENGTH_LONG).show()
    }
    private fun EditText.checkEmpty(): Boolean {
        return this.text.isEmpty()
    }

    fun sendData() {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra(Constants.EXTRA_NAME, nameField.text)
            putExtra(Constants.EXTRA_LAST_NAME, lastNameField.text)
            putExtra(Constants.EXTRA_EMAIL, emailField.text)
        }
        startActivity(intent)
    }
}