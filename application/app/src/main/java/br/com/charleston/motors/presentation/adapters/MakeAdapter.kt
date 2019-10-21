package br.com.charleston.motors.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import br.com.charleston.core.base.BaseAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemMakeBinding
import java.util.*


interface MakeAdapterListener {
    fun onMakeSelect(makeModel: MakeModel)
}

class MakeAdapter(
    private val listener: MakeAdapterListener
) : BaseAdapter<MakeModel, MakeAdapter.MakeAdapterViewHolder>() {

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
            val color = randomColor(item.root.context)

            item.run {
                this.model = model
                cardView.setCardBackgroundColor(color)
                root.setOnClickListener {
                    listener.onMakeSelect(model)
                }
                executePendingBindings()
            }
        }

        private fun randomColor(context: Context): Int {
            val allColors = context.resources.getStringArray(R.array.colors)
            return Color.parseColor(allColors[Random().nextInt(allColors.size)])
        }
    }


}