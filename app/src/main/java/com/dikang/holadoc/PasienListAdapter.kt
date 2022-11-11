package com.dikang.holadoc

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import org.w3c.dom.Text

class PasienListAdapter(
    val context: Activity,
    val arrDokter: ArrayList<User>,
    val idxUser: Int,
    val user:User,
    val onItemClickListener: (view:View, idxDokter:Int, idxUser:Int)->Unit

):ArrayAdapter<User>(context, R.layout.pasien_list, arrDokter) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.pasien_list, null, true)

        var tvNama = rowView.findViewById<TextView>(R.id.lstDokterNama)
        var tvSpesialis = rowView.findViewById<TextView>(R.id.lstDokterSpesialis)
        var tvBiaya = rowView.findViewById<TextView>(R.id.lstDokterBiaya)
        var tvPengalaman = rowView.findViewById<TextView>(R.id.lstDokterPengalaman)
        var btnLihat = rowView.findViewById<Button>(R.id.btnDoktorLihat)

        btnLihat.setOnClickListener {
            onItemClickListener(it, position, idxUser)
        }

        tvNama.text = "${ arrDokter[position].nama }"
        tvSpesialis.text = "Spesialis ${ arrDokter[position].spesialis}"
        tvBiaya.text = "Biaya: Rp.${ arrDokter[position].biaya.toString()}"
        tvPengalaman.text = "Pengalaman: ${ arrDokter[position].tahun} tahun"

        if (user.transaksi.size > 0){
            btnLihat.isEnabled = false;
        }

        return rowView
    }
}