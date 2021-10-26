package com.example.notesappsaveonly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappsaveonly.databinding.ItemViewBinding

class Adapter (private val activity: MainActivity,private var items : ArrayList<Notes>): RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tv.text = item.note
            ibUpdate.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            ibDelete.setOnClickListener {
                activity.deleteDialog(item.id)
            }
        }
    }
    override fun getItemCount() = items.size

    fun update(items: ArrayList<Notes>){
        this.items = items
        notifyDataSetChanged()
    }
}