package com.dikang.holadoc

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.hardware.usb.UsbRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class DetailDoctorActivity : AppCompatActivity() {

    lateinit var tvDetailNama: TextView
    lateinit var tvDetailSpesialis: TextView
    lateinit var tvDetailPengalaman: TextView
    lateinit var tvDetailDeskripsi: TextView
    lateinit var tvDetailBiaya: TextView
    lateinit var edtKeluhan: EditText
    lateinit var btnBatal:Button
    lateinit var btnKonsul:Button

    lateinit var arrUser: ArrayList<User>
    lateinit var arrDokter: ArrayList<User>
    lateinit var dokter: User
    var idxUser = -1
    var idxDokter = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_doctor)

        arrUser = intent.getParcelableArrayListExtra<User>("arrUser")!!
        arrDokter = intent.getParcelableArrayListExtra<User>("arrDokter")!!
        idxUser = intent.getIntExtra("idxUser", -1)
        idxDokter = intent.getIntExtra("idxDokter", -1)
        dokter = arrDokter[idxDokter]

        tvDetailNama = findViewById(R.id.tvDetailNamaDokter)
        tvDetailSpesialis = findViewById(R.id.tvDetailSpesialisDokter)
        tvDetailPengalaman = findViewById(R.id.tvDetailTahunDokter)
        tvDetailDeskripsi = findViewById(R.id.tvDetailDeskripsiDokter)
        tvDetailBiaya = findViewById(R.id.tvDetailBiayaDokter)
        edtKeluhan = findViewById(R.id.editTextTextMultiLine)
        btnBatal = findViewById(R.id.btnBatal)
        btnKonsul = findViewById(R.id.btnKonsul)

        tvDetailNama.text = "${ dokter.nama }"
        tvDetailSpesialis.text = "Spesialis : ${dokter.spesialis}"
        tvDetailPengalaman.text = "Menjadi dokter selama : ${dokter.tahun}"
        tvDetailDeskripsi.text = dokter.desc
        tvDetailBiaya.text = "Biaya : Rp. ${dokter.biaya}"

        btnBatal.setBackgroundColor(Color.RED)
        btnBatal.setOnClickListener {
            startActivity(Intent(this, PasienActivity::class.java))
        }
        btnKonsul.setOnClickListener {
            var keluhan = edtKeluhan.text.toString()
            if (keluhan == ""){
                Toast.makeText(this, "Keluhan harus di isi", Toast.LENGTH_SHORT).show()
            }else{
                //make transaksi
                var realDokterIdx = -1
                arrUser.forEachIndexed { index, user ->
                    if (user.user == dokter.user){
                        realDokterIdx = index
                    }
                }

                var trans: Transaksi= Transaksi(arrUser[idxUser], arrUser[realDokterIdx], keluhan, arrayListOf())

                val resultIntent = Intent()
                resultIntent.putExtra("trans", trans)
                resultIntent.putExtra("idxDokter", realDokterIdx)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}