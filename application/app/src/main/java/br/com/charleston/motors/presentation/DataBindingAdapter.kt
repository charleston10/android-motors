package br.com.charleston.motors.presentation

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.domain.model.MakeModel

class DataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["bindListMake"], requireAll = false)
        fun bindListMake(recyclerView: RecyclerView, items: List<MakeModel>?) {
            items?.let {
                recyclerView.adapter = MakeAdapter()
                    .apply {
                        this.items = items
                    }
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            }
        }
    }
}