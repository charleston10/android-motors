package br.com.charleston.motors.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentHomeBinding
import android.widget.PopupMenu
import android.widget.Toast
import br.com.charleston.motors.presentation.adapters.FavoriteAdapter


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        bindView()
        getViewModel().input.initialize()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
    }

    private fun observerViewModel() {
        getViewModel().output.run {
            makeLiveData.observe(this@HomeFragment,
                Observer {
                    getViewDataBinding().makes = it.toTypedArray()
                })

            vehicleLiveData.observe(this@HomeFragment,
                Observer {
                    getViewDataBinding().vehicles = it.toTypedArray()
                })

            favoriteEvent.observe(this@HomeFragment,
                Observer {
                    handlerState(it)
                })

            makeSelectEvent.observe(this@HomeFragment,
                Observer {
                    startVehicles(it)
                })
        }
    }

    private fun handlerState(state: FavoriteState) {
        when (state) {
            is FavoriteState.Empty -> {
                getViewDataBinding().includeContainerFavorite.isEmpty = true
            }
            is FavoriteState.Success -> {
                getViewDataBinding().includeContainerFavorite.isEmpty = false
            }
            is FavoriteState.Remove -> {
                showPopUpFavoriteAction(
                    state.anchor,
                    state.vehicleModel,
                    state.position
                )
            }
            is FavoriteState.Removed -> {
                removeFavoriteItemOnList(state.position)
                showMessageFavoriteRemove(state.vehicleModel.model)
            }
            is FavoriteState.RemoveFail -> {
                showMessageFavoriteRemoveFail()
            }
            is FavoriteState.StartDetail -> {
                startDetail(state.vehicleModel)
            }
        }
    }

    private fun startVehicles(makeModel: MakeModel) {
        view?.let {
            val action = HomeFragmentDirections
                .actionHomeFragmentToVehicleFragment(makeModel)

            Navigation
                .findNavController(it)
                .navigate(action)
        }
    }

    private fun startDetail(vehicleModel: VehicleModel) {
        view?.let {
            val action = HomeFragmentDirections
                .actionHomeFragmentToVehicleDetailFragment(vehicleModel)

            Navigation
                .findNavController(it)
                .navigate(action)
        }
    }

    private fun bindView() {
        getViewDataBinding().viewModel = getViewModel()
    }

    private fun showPopUpFavoriteAction(anchor: View, vehicleModel: VehicleModel, position: Int) {
        PopupMenu(this.context, anchor).apply {
            menuInflater.inflate(R.menu.menu_favorite, this.menu)
            setOnMenuItemClickListener {
                getViewModel().input.removeFavorite(vehicleModel, position)
                true
            }
        }.show()
    }

    private fun showMessageFavoriteRemove(value: String) {
        Toast.makeText(this.context, "Favorite $value removed", Toast.LENGTH_SHORT).show()
    }

    private fun removeFavoriteItemOnList(position: Int) {
        (getViewDataBinding().includeContainerFavorite.listFavorite.adapter as? FavoriteAdapter)?.remove(
            position
        )
    }

    private fun showMessageFavoriteRemoveFail() {
        Toast.makeText(this.context, "Fail on remove favorite", Toast.LENGTH_SHORT).show()
    }
}