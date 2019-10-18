package br.com.charleston.motors.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["bindImageUrl"], requireAll = false)
        fun bindImageUrl(view: ImageView, url: String?) {
            url?.let {
                Glide
                    .with(view.context)
                    .load(it)
                    .apply(
                        RequestOptions
                            .bitmapTransform(RoundedCorners(24))
                    )
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindCircleUrl"], requireAll = false)
        fun bindCircleUrl(view: ImageView, url: String?) {
            url?.let {
                Glide
                    .with(view.context)
                    .load(it)
                    .apply(
                        RequestOptions.circleCropTransform()
                    )
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindWelcome"], requireAll = false)
        fun bindWelcome(textView: TextView, name: String?) {
            name?.let {
                textView.text = String.format("Bem-vindo %s", it)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindListMake"], requireAll = false)
        fun bindListMake(recyclerView: RecyclerView, items: Array<MakeModel>?) {
            items?.let {
                recyclerView.adapter = MakeAdapter()
                    .apply {
                        this.items = items.toList()
                    }
                recyclerView.layoutManager =
                    LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindListVehicle"], requireAll = false)
        fun bindListVehicle(recyclerView: RecyclerView, items: Array<VehicleModel>?) {
            items?.let {
                if (recyclerView.adapter == null) {
                    recyclerView.adapter = VehicleAdapter()
                        .apply {
                            this.items = items.toList()
                        }
                    recyclerView.layoutManager =
                        LinearLayoutManager(recyclerView.context)
                } else {
                    (recyclerView.adapter  as? VehicleAdapter)?.addAll(items.toList())
                }
            }
        }
    }
}