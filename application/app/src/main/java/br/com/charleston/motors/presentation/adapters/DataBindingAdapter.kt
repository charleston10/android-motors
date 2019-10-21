package br.com.charleston.motors.presentation.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.presentation.screens.home.HomeViewModel
import br.com.charleston.motors.presentation.screens.vehicle.VehicleViewModel
import com.bumptech.glide.Glide
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
                textView.text = String.format("%s", it)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindCarName"], requireAll = false)
        fun bindCarName(textView: TextView, model: VehicleModel?) {
            model?.let {
                textView.text = String.format("%s - %s", model.make, model.model)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindListMake", "bindListMakeViewModel"], requireAll = false)
        fun bindListMake(
            recyclerView: RecyclerView,
            items: Array<MakeModel>?,
            viewModel: HomeViewModel?
        ) {
            items?.let {
                recyclerView.adapter = MakeAdapter(object : MakeAdapterListener {
                    override fun onMakeSelect(makeModel: MakeModel) {
                        viewModel?.input?.onSelectMake(makeModel)
                    }

                }).apply {
                    this.items = items.toList()
                }
                recyclerView.layoutManager =
                    LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindListFavorite"], requireAll = false)
        fun bindListFavorite(recyclerView: RecyclerView, items: Array<VehicleModel>?) {
            items?.let {
                if (recyclerView.adapter == null) {
                    recyclerView.adapter = FavoriteAdapter()
                        .apply {
                            this.items = items.toList()
                        }
                    recyclerView.layoutManager =
                        GridLayoutManager(recyclerView.context, 2)
                    recyclerView.setHasFixedSize(true)
                } else {
                    (recyclerView.adapter  as? FavoriteAdapter)?.addAll(items.toList())
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindListVehicle", "bindListVehicleViewModel"], requireAll = false)
        fun bindListVehicle(
            recyclerView: RecyclerView,
            items: Array<VehicleModel>?,
            viewModel: VehicleViewModel?
        ) {
            items?.let {
                if (recyclerView.adapter == null) {
                    recyclerView.adapter = VehicleAdapter(object : VehicleAdapterListener {
                        override fun onVehicleSelect(vehicleModel: VehicleModel) {
                            viewModel?.input?.onSelectVehicle(vehicleModel)
                        }
                    }).apply {
                        this.items = items.toList()
                    }
                    recyclerView.layoutManager =
                        LinearLayoutManager(
                            recyclerView.context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                } else {
                    (recyclerView.adapter  as? VehicleAdapter)?.addAll(items.toList())
                }
            }
        }
    }
}