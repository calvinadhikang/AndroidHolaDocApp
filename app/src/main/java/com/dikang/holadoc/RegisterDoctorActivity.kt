package com.dikang.holadoc

import android.content.Intent
import android.hardware.usb.UsbRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.util.Calendar

class RegisterDoctorActivity : AppCompatActivity() {

    lateinit var edtName:EditText
    lateinit var edtUser:EditText
    lateinit var edtPass:EditText
    lateinit var edtCPass:EditText
    lateinit var edtDesc:EditText
    lateinit var edtTahun:EditText
    lateinit var spinner:Spinner
    lateinit var btnRegister:Button
    lateinit var btnToLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_doctor)

        edtName = findViewById(R.id.edtName2)
        edtUser = findViewById(R.id.edtUser2)
        edtPass = findViewById(R.id.edtPass2)
        edtCPass = findViewById(R.id.edtCPass2)
        edtDesc = findViewById(R.id.edtDesc)
        edtTahun = findViewById(R.id.edtTahunPraktik)
        spinner = findViewById(R.id.spinner)

        btnToLogin = findViewById(R.id.btnDoctorToLogin)
        btnToLogin.setOnClickListener {
            finish()
        }

        btnRegister = findViewById(R.id.btnRegisterPasien2)
        btnRegister.setOnClickListener {

            var nama = edtName.text.toString()
            var user = edtUser.text.toString()
            var pass = edtPass.text.toString()
            var cpas = edtCPass.text.toString()
            var desc = edtDesc.text.toString()
            var tahun = edtTahun.text.toString()
            var spesial = spinner.selectedItem.toString()

            if (nama == "" || user == "" || pass == "" || cpas == "" || desc == "" || tahun == ""){
                Toast.makeText(this, "Inputan tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            }else{
                if (pass != cpas){
                    Toast.makeText(this, "Password dan Confirm tidak sama !", Toast.LENGTH_SHORT).show()
                }else{
                    var yearNow:Int = Calendar.YEAR.toInt()
                    var tahun = tahun.toInt()
                    var umur = yearNow - tahun

                    var new = User("dr. $nama", user, pass, spesial, desc, umur.toString(), arrayListOf<Transaksi>())

                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW", new)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}