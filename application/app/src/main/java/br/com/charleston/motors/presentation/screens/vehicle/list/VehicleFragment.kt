package br.com.charleston.motors.presentation.screens.vehicle.list

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleBinding
import br.com.charleston.motors.presentation.adapters.VehicleAdapterListener
import br.com.charleston.motors.presentation.adapters.VehicleListAdapter
import br.com.charleston.motors.presentation.extensions.animateFallDown

class VehicleFragment : BaseFragment<FragmentVehicleBinding, VehicleViewModel>() {

    private val listAdapter by lazy {
        VehicleListAdapter(object : VehicleAdapterListener {
            override fun onVehicleSelect(
                carImageView: ImageView,
                vehicleModel: VehicleModel
            ) {
                getViewModel().input.onSelectVehicle(carImageView, vehicleModel)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        bindView()
        setupList()
        findVehicles()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle
    }

    override fun getViewModel(): VehicleViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(VehicleViewModel::class.java)
    }

    private fun observerViewModel() {
        getViewModel().output.run {
            vehicleEvent.observe(viewLifecycleOwner,
                Observer {
                    handlerState(it)
                })

            vehicleListLiveData.observe(viewLifecycleOwner,
                Observer {
                    onLoadVehicles(it)
                })
        }
    }

    private fun handlerState(state: VehicleState) {
        getViewDataBinding().run {
            isEmpty = state is VehicleState.Empty
            isLoading = state is VehicleState.Loading
            isLoadingPage = state is VehicleState.LoadingPage
            isError = state is VehicleState.Error
        }

        if (state is VehicleState.StartDetail) {
            startDetail(state.carImageView, state.model)
        }
    }

    private fun startDetail(carImageView: ImageView, model: VehicleModel) {
        view?.let {
            val extras = FragmentNavigatorExtras(
                carImageView to model.id.toString()
            )

            findNavController().navigate(
                R.id.action_vehicleFragment_to_vehicleDetailFragment,
                Bundle().apply {
                    putParcelable("vehicleModel", model)
                },
                null,
                extras
            )
        }
    }

    private fun bindView() {
        getViewDataBinding().viewModel = getViewModel()
    }

    private fun setupList() {
        getViewDataBinding().listVehicles?.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visiblePosition =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    if (visiblePosition == listAdapter.itemCount - 1) {
                        getViewModel().input.nextVehiclePage()
                    }
                }
            })

            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun findVehicles() {
        arguments?.let {
            val safeArgs = VehicleFragmentArgs.fromBundle(it)
            val model = safeArgs.makeModel
            getViewDataBinding().make = model
            getViewModel().input.findVehicles(model)
        }
    }

    private fun onLoadVehicles(list: List<VehicleModel>) {
        if (listAdapter.itemCount == 0) {
            getViewDataBinding().listVehicles.animateFallDown()
        }
        listAdapter.submitList(list.toMutableList())
    }
}