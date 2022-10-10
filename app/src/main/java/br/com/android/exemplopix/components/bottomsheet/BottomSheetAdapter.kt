package br.com.android.exemplopix.components.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.android.exemplopix.databinding.RecyclerViewBottomSheetBinding

class BottomSheetAdapter : RecyclerView.Adapter<BottomSheetAdapter.BSAViewHolder>() {

    private val data = mutableListOf<String>()

    fun setData(list: List<String>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    inner class BSAViewHolder(binding: RecyclerViewBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val typeItem = binding.txvItem

        fun bind(item: String) {
            typeItem.text = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = BSAViewHolder(
        RecyclerViewBottomSheetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: BSAViewHolder,
        position: Int
    ) = holder.bind(data[position])

    override fun getItemCount() = data.size
}