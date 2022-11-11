package com.dikang.holadoc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Transaksi(
    var pasien:User,
    var dokter:User,
    var keluhan: String,
    var chats:ArrayList<Chat>,
) : Parcelable {
}