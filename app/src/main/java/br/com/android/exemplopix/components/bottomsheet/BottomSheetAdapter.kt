package br.com.android.exemplopix.components.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.android.exemplopix.databinding.RecyclerViewBottomSheetBinding

class BottomSheetAdapter(
    private val onItemClicked: (String?) -> Unit
) : RecyclerView.Adapter<BottomSheetAdapter.BSAViewHolder>() {

    private val data = mutableListOf<String?>()
    private var selected: String? = null

    fun setData(list: List<String?>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    fun setSelected(select: String?) {
        this.selected = select
        notifyDataSetChanged()
    }

    inner class BSAViewHolder(val binding: RecyclerViewBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String?) = binding.run {
            txvItem.text = item
            content.setOnClickListener {
                onItemClicked(item)
            }
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
    ) {
        if (data[position] == selected) {
            holder.binding.checkbox.isChecked = true
        }
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}