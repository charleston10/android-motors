package br.com.charleston.motors.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import br.com.charleston.core.base.BaseAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemMakeBinding

class MakeAdapter : BaseAdapter<MakeModel, MakeAdapter.MakeAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeAdapterViewHolder {
        return MakeAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_make,
                parent,
                false
            )
        )
    }

    inner class MakeAdapterViewHolder(private val item: ItemMakeBinding) :
        BaseViewHolder<MakeModel>(item.root) {
        override fun bind(model: MakeModel) {
            item.run {
                this.model = model
                executePendingBindings()
            }
        }
    }
}