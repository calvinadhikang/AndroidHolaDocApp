package com.dikang.holadoc

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class DoctorListAdapter(
    val context: Activity,
    val arrPasien: ArrayList<Transaksi>,
):ArrayAdapter<Transaksi>(context, R.layout.doctor_list, arrPasien) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.doctor_list, null, true)

        var tvNama = rowView.findViewById<TextView>(R.id.tvNamaListPasien)
        var tvKeluhan = rowView.findViewById<TextView>(R.id.tvKeluhanListPasien)

        tvNama.text = "Nama: ${ arrPasien[position].pasien.nama }"
        tvKeluhan.text = "Keluhan: ${ arrPasien[position].keluhan}"

        return rowView
    }
}