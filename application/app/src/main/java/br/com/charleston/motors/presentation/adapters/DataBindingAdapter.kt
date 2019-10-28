package br.com.charleston.motors.presentation.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.presentation.screens.home.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout


class DataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["bindInt"], requireAll = false)
        fun bindInt(textView: TextView, value: Int?) {
            value?.let {
                textView.text = String.format("%d", it)
            }
        }

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
        @BindingAdapter(value = ["bindFilterDescription"], requireAll = false)
        fun bindFilterDescription(textView: TextView, makeModel: MakeModel?) {
            makeModel?.let {
                val text = if (it.id == 0) {
                    "Showing all cars"
                } else {
                    String.format("Showing cars by %s", it.name)
                }

                textView.text = text
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["bindSearch"], requireAll = false)
        fun bindSearch(textInput: TextInputLayout, viewModel: HomeViewModel?) {
            textInput.setEndIconOnClickListener {
                viewModel?.input?.filterFavorite(textInput.editText?.text.toString())
            }
            textInput.editText?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.isEmpty()?.let {
                        viewModel?.input?.filterFavorite("")
                    } ?: viewModel?.input?.filterFavorite("")
                }
            })
        }

        @JvmStatic
        @BindingAdapter(
            value = ["bindButtonFavorite", "bindButtonFavoriteLoading"],
            requireAll = false
        )
        fun bindButtonFavorite(button: MaterialButton, model: VehicleModel?, isLoading: Boolean?) {
            if (model != null && isLoading != null) {
                when {
                    isLoading -> {
                        button.text =
                            button.context.getString(R.string.vehicle_detail_button_favorite)
                        button.isEnabled = false
                    }
                    model.isFavorite.get() -> {
                        button.text =
                            button.context.getString(R.string.vehicle_detail_button_disfavor)
                        button.isEnabled = true
                    }
                    else -> {
                        button.text =
                            button.context.getString(R.string.vehicle_detail_button_favorite)
                    }
                }
            } else {
                button.text = button.context.getString(R.string.vehicle_detail_button_favorite)
            }
        }
    }
}