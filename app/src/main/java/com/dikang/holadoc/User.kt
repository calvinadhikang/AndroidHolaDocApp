package com.dikang.holadoc

import android.os.Parcelable
import android.telephony.mbms.StreamingServiceInfo
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var nama:String,
    var user:String,
    var pass:String,
    var spesialis:String,
    var desc:String,
    var tahun:String,
    var transaksi: ArrayList<Transaksi>,
    var biaya:Int = 0,
    var pendapatan: Int = 0
) : Parcelable {
}