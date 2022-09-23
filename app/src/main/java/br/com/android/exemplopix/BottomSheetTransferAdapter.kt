package br.com.android.exemplopix

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.android.exemplopix.databinding.ContactsPixAdapterBinding

class BottomSheetTransferAdapter : RecyclerView.Adapter<BottomSheetTransferAdapter.BottomSheetViewHolder>() {

    private val data = mutableListOf<ContactsPixModel>()

    fun setData(list: List<ContactsPixModel>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    class BottomSheetViewHolder(
        binding: ContactsPixAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.nome
        fun bind(item: ContactsPixModel) {
            name.text = item.nameContact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BottomSheetViewHolder (
        ContactsPixAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}