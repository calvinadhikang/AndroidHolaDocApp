package com.dikang.holadoc

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.math.log

class DoctorActivity : AppCompatActivity() {

    lateinit var tvLogin:TextView
    lateinit var tvPendapatan:TextView
    lateinit var lvPasien: ListView
    lateinit var edtBiaya:EditText
    lateinit var btnLogout:Button
    lateinit var btnSave:Button
    lateinit var adapter:DoctorListAdapter

    lateinit var arrUser: ArrayList<User>
    var idx: Int = -1
    lateinit var user:User
    var idxTrans:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        arrUser = intent.getParcelableArrayListExtra<User>("arrUser")!!
        idx = intent.getIntExtra("idx", -1)
        user = arrUser[idx]

        tvLogin = findViewById(R.id.tvLoginDokter)
        tvLogin.text = "Hi, ${user.nama}"

        edtBiaya = findViewById(R.id.edtBiaya)
        lvPasien = findViewById(R.id.lvDaftarPasien)

        btnSave = findViewById(R.id.btnSaveBiaya)
        btnSave.setOnClickListener {
            var biaya = edtBiaya.text.toString().toInt()
            user.biaya = biaya
        }

        btnLogout = findViewById(R.id.btnDokterLogout)
        btnLogout.setBackgroundColor(Color.RED)
        btnLogout.setOnClickListener {
            logout()
        }

        tvPendapatan = findViewById(R.id.tvDokterPendapatan)
        tvPendapatan.text = "Total Pendapatan : Rp " + user.pendapatan.toString()

        initValue()
    }

    val addLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == 100){
            val data = result.data
            if(data != null){
                var Trans = data.getParcelableExtra<Transaksi>("trans")!!

                user.transaksi[idxTrans] = Trans
                //ganti trans pasien
                arrUser.forEachIndexed { _, user ->
                    if (user.nama == Trans.pasien.nama){
                        user.transaksi[0] = Trans
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun initValue(){
        //listview
        adapter = DoctorListAdapter(this, user.transaksi)
        lvPasien.adapter = adapter
        registerForContextMenu(lvPasien)

        //biaya
        edtBiaya.setText(user.biaya.toString())
    }

    fun logout(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("arrUser", arrUser)
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.doctor_list_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detail ->{
                var info: AdapterContextMenuInfo = item.menuInfo as AdapterContextMenuInfo
                idxTrans = info.position

                var intent = Intent(this, DokterPercakapanActivity::class.java)
                intent.putExtra("trans", user.transaksi[idxTrans]);
                addLauncher.launch(intent)

                return true
            }
            R.id.menu_tolak ->{
                var info: AdapterContextMenuInfo = item.menuInfo as AdapterContextMenuInfo
                idxTrans = info.position

                var trans = user.transaksi[idxTrans]
                arrUser.forEachIndexed { index, user ->
                    if (user.nama == trans.pasien.nama){
                        user.transaksi[0].keluhan = "DENIED"
                    }
                }

                user.transaksi.removeAt(idxTrans)
                adapter.notifyDataSetChanged()

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}