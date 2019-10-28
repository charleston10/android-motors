package br.com.charleston.motors.presentation.screens.vehicle.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding, VehicleDetailViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindModel()
        observerViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle_detail
    }

    override fun getViewModel(): VehicleDetailViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(VehicleDetailViewModel::class.java)
    }

    private fun bindModel() {
        arguments?.let {
            val safeArgs = VehicleDetailFragmentArgs.fromBundle(it)
            val model = safeArgs.vehicleModel

            getViewModel().input.initialize(model)

            bindImage(model)
            bindView(model)
        }
    }

    private fun observerViewModel() {
        getViewModel().output.vehicleEvent.observe(this,
            Observer {
                handlerState(it)
            })
    }

    private fun handlerState(state: VehicleDetailState) {
        if (state is VehicleDetailState.Error) showMessageError()
        showLoading(state is VehicleDetailState.Loading)
    }

    private fun showMessageError() {
        Toast.makeText(this.context, "Error in favoring", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(value: Boolean) {
        getViewDataBinding().isLoading = value
    }

    private fun bindView(vehicleModel: VehicleModel) {
        getViewDataBinding().viewModel = getViewModel()
        getViewDataBinding().imageView2.transitionName = vehicleModel.id.toString()
    }

    private fun bindImage(vehicleModel: VehicleModel) {
        this.context?.let { context ->
            Glide
                .with(context)
                .load(vehicleModel.image)
                .apply(
                    RequestOptions
                        .bitmapTransform(RoundedCorners(24))
                        .dontAnimate()
                )
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                })
                .into(getViewDataBinding().imageView2)
        }

    }
}