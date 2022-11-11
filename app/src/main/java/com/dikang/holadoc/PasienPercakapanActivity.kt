package com.dikang.holadoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class PasienPercakapanActivity : AppCompatActivity() {

    lateinit var tvNamaPasien:TextView
    lateinit var tvKeluhan:TextView
    lateinit var lvChat:ListView
    lateinit var btnChat:Button
    lateinit var btnBack:Button
    lateinit var btnSelesai:Button
    lateinit var edtChatIn:EditText
    lateinit var adapter:PercakapanListAdapter

    lateinit var trans: Transaksi
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien_percakapan)

        trans = intent.getParcelableExtra<Transaksi>("trans")!!
        user = intent.getParcelableExtra<User>("user")!!

        tvNamaPasien = findViewById(R.id.tvNamaPasien)
        tvKeluhan = findViewById(R.id.tvKeluhan)

        tvNamaPasien.text = trans.pasien.nama
        tvKeluhan.text = trans.keluhan

        lvChat = findViewById(R.id.lvChatDokter)
        adapter = PercakapanListAdapter(this, "Pasien", trans.chats)
        lvChat.adapter = adapter

        btnChat = findViewById(R.id.btnSend)
        edtChatIn = findViewById(R.id.edtChatIn)
        btnChat.setOnClickListener {
            trans.chats.add(Chat(user.nama, "Pasien", edtChatIn.text.toString()))
            adapter.notifyDataSetChanged()
        }

        btnBack = findViewById(R.id.btnBackChatPasien)
        btnBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("trans", trans)
            setResult(100, resultIntent)
            finish()
        }

        btnSelesai = findViewById(R.id.btnSelesai)
        btnSelesai.setOnClickListener {
            val resultIntent = Intent()
            setResult(404, resultIntent)
            finish()
        }
    }
}