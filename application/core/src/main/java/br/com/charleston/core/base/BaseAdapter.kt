package br.com.charleston.core.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<T, A : BaseViewHolder<T>> : RecyclerView.Adapter<A>() {

    private val itemsList = ArrayList<T>()

    var items: List<T>?
        get() = itemsList
        set(value) {
            itemsList.clear()
            if (value != null && value.isNotEmpty()) {
                itemsList.addAll(value)
            }
            notifyDataSetChanged()
        }

    fun remove(position: Int) {
        if (itemsList.isNotEmpty()) {
            itemsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        itemsList.clear()
        notifyDataSetChanged()
    }

    fun addAll(items: List<T>) {
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    fun refreshList(items: List<T>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): A

    override fun onBindViewHolder(viewHolder: A, position: Int) {
        viewHolder.bind(getItem(position))
    }

    override fun getItemCount(): Int = items?.size ?: 0
    private fun getItem(position: Int): T = itemsList[position]
}

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: T)
}