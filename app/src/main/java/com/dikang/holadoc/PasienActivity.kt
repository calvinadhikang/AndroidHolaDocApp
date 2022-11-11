package com.dikang.holadoc

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat

class PasienActivity : AppCompatActivity() {

    lateinit var lstView:ListView
    lateinit var tvStatus:TextView
    lateinit var tvLogin:TextView
    lateinit var btnPercakapan:Button
    lateinit var btnLogout:Button
    lateinit var edtSearchByNama:EditText

    lateinit var arrUser:ArrayList<User>
    lateinit var arrDokter:ArrayList<User>
    var idx = -1
    lateinit var user:User
    lateinit var adapter: PasienListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        arrUser = intent.getParcelableArrayListExtra<User>("arrUser")!!
        idx = intent.getIntExtra("idx", -1)!!
        user = arrUser[idx]

        tvLogin = findViewById(R.id.tvLoginPasien)
        tvLogin.text = "Hi, " + user.nama

        lstView = findViewById(R.id.listViewDokter)
        arrDokter = arrayListOf()
        arrUser.forEachIndexed { index, user ->
            if (user.spesialis != "-" && user.biaya > 0){
                arrDokter.add(user)
            }
        }

        edtSearchByNama = findViewById(R.id.edtSearchByNama)

        adapter = PasienListAdapter(this, arrDokter, idx, user){ view, idxDokter, idxUser ->
            var intent = Intent(this, DetailDoctorActivity::class.java)
            intent.putExtra("arrUser", arrUser)
            intent.putExtra("arrDokter", arrDokter)
            intent.putExtra("idxDokter", idxDokter)
            intent.putExtra("dokter", arrUser[idxDokter])
            intent.putExtra("idxUser", idx)
            addLauncher.launch(intent)
        }
        lstView.adapter = adapter
        adapter.notifyDataSetChanged()

        btnPercakapan = findViewById(R.id.btnPercakapan)

        if(user.transaksi.size > 0 && user.transaksi[0].keluhan == "DENIED"){
            tvStatus = findViewById(R.id.tvstatuskonsul)
            tvStatus.text = "KONSULTASI SEBELUMNYA DITOLAK"
            tvStatus.setTextColor(Color.RED)
            user.transaksi = arrayListOf()
        }
        changeButtonPercakapan()

        btnPercakapan.setOnClickListener {
            var intent = Intent(this, PasienPercakapanActivity::class.java)
            intent.putExtra("trans", user.transaksi[0])
            intent.putExtra("user", user)
            addLauncher.launch(intent)
        }

        btnLogout = findViewById(R.id.btnLogoutPasien)
        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("arrUser", arrUser)
            startActivity(intent)
        }
    }

    val addLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            val data = result.data
            if(data != null){
                var idxDokter = data.getIntExtra("idxDokter", -1)
                var Trans = data.getParcelableExtra<Transaksi>("trans")!!

                user.transaksi.add(Trans)
                arrUser[idxDokter].transaksi.add(Trans)

                Toast.makeText(this, "Berhasil add", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                changeButtonPercakapan()
            }
        }
        if(result.resultCode == 100){
            val data = result.data
            if(data != null){
                var Trans = data.getParcelableExtra<Transaksi>("trans")!!

                user.transaksi[0] = Trans
                //ganti trans dokter
                arrUser.forEachIndexed { index, user ->
                    if (user.nama == Trans.dokter.nama){
                        user.transaksi.forEachIndexed { idx, transaksi ->
                            if (transaksi.pasien.nama == Trans.pasien.nama){
                                user.transaksi[idx] = Trans
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged()
                changeButtonPercakapan()
            }
        }
        if(result.resultCode == 404){
            val data = result.data
            if(data != null){
                //bila selesai transaksi
                var Trans:Transaksi = user.transaksi[0]
                arrUser.forEachIndexed { index, user ->
                    if (user.nama == Trans.dokter.nama){
                        var earning = user.biaya
                        user.pendapatan += earning

                        user.transaksi.forEachIndexed { idxTrans, transaksi ->
                            if (transaksi.dokter.nama == user.nama){
                                user.transaksi.removeAt(idxTrans)
                            }
                        }
                    }
                }

                user.transaksi = arrayListOf()
                changeButtonPercakapan()
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun changeButtonPercakapan(){
        if (user.transaksi.size == 0){
            btnPercakapan.isEnabled = false
        }else{
            btnPercakapan.isEnabled = true
        }
    }
}