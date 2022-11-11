package com.dikang.holadoc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Chat(
    var nama:String,
    var role:String,
    var isi:String
) : Parcelable {
}