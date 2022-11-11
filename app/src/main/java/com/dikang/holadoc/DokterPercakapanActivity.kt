package com.dikang.holadoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class DokterPercakapanActivity : AppCompatActivity() {

    lateinit var tvNama: TextView
    lateinit var tvKeluhan: TextView
    lateinit var lvChat: ListView
    lateinit var edtPesan: EditText
    lateinit var btnBack: Button
    lateinit var btnSend: Button

    lateinit var trans: Transaksi
    lateinit var adapter: PercakapanListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dokter_percakapan)

        trans = intent.getParcelableExtra<Transaksi>("trans")!!

        btnBack = findViewById(R.id.btnBack)
        btnSend = findViewById(R.id.btnSend2)
        edtPesan = findViewById(R.id.edtChatIn2)
        tvNama = findViewById(R.id.tvNamaPasien2)
        tvKeluhan = findViewById(R.id.tvKeluhan2)
        lvChat = findViewById(R.id.lvChatDokter)

        tvNama.text = "Nama Pasien: " + trans.pasien.nama
        tvKeluhan.text = "Keluhan: " + trans.keluhan

        adapter = PercakapanListAdapter(this, "Dokter", trans.chats)
        lvChat.adapter = adapter

        btnSend.setOnClickListener {
            var msg = edtPesan.text.toString()

            trans.chats.add(Chat(trans.dokter.nama, "Dokter", msg))
            adapter.notifyDataSetChanged()
        }

        btnBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("trans", trans)
            setResult(100, resultIntent)
            finish()
        }
    }
}