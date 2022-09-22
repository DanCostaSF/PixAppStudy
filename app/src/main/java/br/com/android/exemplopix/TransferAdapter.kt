package br.com.android.exemplopix

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.android.exemplopix.databinding.ContactsPixAdapterBinding

class TransferAdapter : RecyclerView.Adapter<TransferAdapter.TransferViewHolder>() {

    private val data = mutableListOf<ContactsPixModel>()

    fun setData(list: List<ContactsPixModel>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    class TransferViewHolder(
        binding: ContactsPixAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.nome
        fun bind(item: ContactsPixModel) {
            name.text = item.nameContact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransferViewHolder(
        ContactsPixAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
        val contactsPix = data[position]
        holder.bind(contactsPix)
    }

    override fun getItemCount() = data.size
}