package com.dikang.holadoc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterPasienActivity : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var edtUser: EditText
    lateinit var edtPass: EditText
    lateinit var edtCPass: EditText
    lateinit var btnRegister: Button
    lateinit var btnToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pasien)

        edtName = findViewById(R.id.edtName)
        edtUser = findViewById(R.id.edtUser)
        edtPass = findViewById(R.id.edtPass)
        edtCPass = findViewById(R.id.edtCPass)

        btnToLogin = findViewById(R.id.btnPasienToLogin)
        btnToLogin.setOnClickListener {
            finish()
        }

        btnRegister = findViewById(R.id.btnRegisterPasien)
        btnRegister.setOnClickListener {

            var nama = edtName.text.toString()
            var user = edtUser.text.toString()
            var pass = edtPass.text.toString()
            var cpas = edtCPass.text.toString()

            if (nama == "" || user == "" || pass == "" || cpas == ""){
                Toast.makeText(this, "Inputan tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            }else{
                if (pass != cpas){
                    Toast.makeText(this, "Password dan Confirm tidak sama !", Toast.LENGTH_SHORT).show()
                }else{
                    var new = User(nama, user, pass, "-", "-", "-", arrayListOf<Transaksi>())

                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW", new)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}