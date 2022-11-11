package com.dikang.holadoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    lateinit var edtUser: EditText
    lateinit var edtPass: EditText
    lateinit var btnLogin: Button
    lateinit var btnToPasien: Button
    lateinit var btnToDoctor: Button

    var arrUser: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent.getParcelableArrayListExtra<User>("arrUser") != null){
            arrUser = intent.getParcelableArrayListExtra<User>("arrUser")!!
        }else{
            init()
        }

        edtUser = findViewById(R.id.edtUsername)
        edtPass = findViewById(R.id.edtPassword)
        btnToDoctor = findViewById(R.id.btnToRegisterDoctor)
        btnToDoctor.setOnClickListener {
            addLauncher.launch(Intent(this, RegisterDoctorActivity::class.java))

        }
        btnToPasien = findViewById(R.id.btnToRegisterPasien)
        btnToPasien.setOnClickListener {
            addLauncher.launch(Intent(this, RegisterPasienActivity::class.java))
        }
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            var usern = edtUser.text.toString()
            var passw = edtPass.text.toString()

            if (usern == "" || passw == ""){
                Toast.makeText(this, "Inputan tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            }else{
                arrUser.forEachIndexed { index, user ->
                    if (user.user == usern && user.pass == passw){
                        var intent = Intent()

                        if (user.spesialis == "-"){
                            intent = Intent(this, PasienActivity::class.java)
                        }else{
                            intent = Intent(this, DoctorActivity::class.java)
                        }
                        intent.putExtra("arrUser", arrUser)
                        intent.putExtra("idx", index)
                        intent.putExtra("user", user)
                        Log.d("size", arrUser.size.toString())
                        startActivity(intent)
                    }
                }
            }
        }

    }

    val addLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            val data = result.data
            if(data != null){
                var user = data.getParcelableExtra<User>("NEW")!!
                arrUser.add(user)
            }
        }
    }

    fun init(){
        arrUser.add(User("user1", "a", "a", "-", "-", "0", arrayListOf(), 0))
        arrUser.add(User("dr. Dokter 1", "dok", "dok", "Umum", "Ini Dokter", "2002", arrayListOf(), 100))
        arrUser.add(User("dr. Dokter 2", "dok2", "dok2", "Umum", "Ini Dokter", "2002", arrayListOf(), 100))
        arrUser.add(User("omg", "dok3", "dok3", "Umum", "Ini Dokter", "2002", arrayListOf(), 100))
    }
}