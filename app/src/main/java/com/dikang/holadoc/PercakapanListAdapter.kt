package com.dikang.holadoc

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class PercakapanListAdapter(
    val context: Activity,
    val role: String,
    val arrChat: ArrayList<Chat>
):ArrayAdapter<Chat>(context, R.layout.chat_list, arrChat) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.chat_list, null, true)

        var tvNama = rowView.findViewById<TextView>(R.id.tvChatName)
        var tvIsi= rowView.findViewById<TextView>(R.id.tvChatIsi)

        tvNama.text = "${ arrChat[position].nama }"
        tvIsi.text = arrChat[position].isi

        if (role == "Pasien"){
            if (arrChat[position].role == "Pasien"){
                tvNama.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                tvIsi.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            }else{
                tvNama.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                tvIsi.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
        }else{
            if (arrChat[position].role == "Dokter"){
                tvNama.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                tvIsi.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            }else{
                tvNama.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                tvIsi.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
        }

        return rowView
    }
}