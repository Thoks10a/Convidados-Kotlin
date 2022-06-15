package com.example.convidados.view.viewholder

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView
    .ViewHolder(
        bind
            .root
    ) {

    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name

        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context).setTitle("Deletar convidado")
                .setMessage("Gostaria de deletar o convidado permanentemente ?").setPositiveButton(
                    "Sim"
                ) { dialog, which -> listener.onDelete(guest.id) }
                .setNegativeButton("Não") { dialog, which ->
                    dialog.dismiss()
                }.create().show()

            true
        }
    }
}