package br.com.charleston.motors.presentation.screens.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import androidx.navigation.fragment.FragmentNavigatorExtras
import br.com.charleston.motors.presentation.adapters.FavoriteAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.charleston.motors.presentation.adapters.FavoriteAdapterListener
import br.com.charleston.motors.presentation.adapters.MakeAdapter
import br.com.charleston.motors.presentation.adapters.MakeAdapterListener


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    FavoriteAdapterListener,
    MakeAdapterListener {

    private val adapterFavorite by lazy { FavoriteAdapter(this) }
    private val adapterMakes by lazy { MakeAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        observerViewModel()
        setupListFavorite()
        setupListMakes()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
    }

    override fun onFavoriteShortSelected(
        carImageView: ImageView,
        vehicleModel: VehicleModel
    ) {
        getViewModel().input.onSelectShortVehicle(carImageView, vehicleModel)
    }

    override fun onFavoriteLongSelected(
        anchor: View,
        vehicleModel: VehicleModel,
        position: Int
    ) {
        getViewModel().input.onSelectLongVehicle(anchor, vehicleModel, position)
    }

    override fun onMakeSelect(makeModel: MakeModel) {
        getViewModel().input.onSelectMake(makeModel)
    }

    private fun observerViewModel() {
        getViewModel().output.run {
            makeLiveData.observe(this@HomeFragment,
                Observer {
                    adapterMakes.refreshList(it)
                })

            vehicleLiveData.observe(this@HomeFragment,
                Observer {
                    adapterFavorite.refreshList(it)
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
                startDetail(state.carImageView, state.vehicleModel)
            }

            is FavoriteState.FilterSuccess -> {
                adapterFavorite.refreshList(state.list)
            }
        }

        getViewDataBinding().includeContainerFavorite.isEmpty = state is FavoriteState.Empty
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

    private fun startDetail(carImageView: ImageView, vehicleModel: VehicleModel) {
        view?.let {
            val extras = FragmentNavigatorExtras(
                carImageView to vehicleModel.id.toString()
            )

            findNavController().navigate(
                R.id.action_homeFragment_to_vehicleDetailFragment,
                Bundle().apply {
                    putParcelable("vehicleModel", vehicleModel)
                },
                null,
                extras
            )
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

    private fun setupListFavorite() {
        getViewDataBinding().includeContainerFavorite.listFavorite.apply {
            adapter = adapterFavorite
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
    }

    private fun setupListMakes() {
        getViewDataBinding().includeContainerMake.listMakes.apply {
            adapter = adapterMakes
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }
}