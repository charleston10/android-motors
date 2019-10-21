package br.com.charleston.motors.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentHomeBinding

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

    private fun handlerState(favoriteState: FavoriteState) {
        when (favoriteState) {
            is FavoriteState.Empty -> {
                getViewDataBinding().includeContainerFavorite.isEmpty = true
            }
            is FavoriteState.Success -> {
                getViewDataBinding().includeContainerFavorite.isEmpty = false
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

    private fun bindView(){
        getViewDataBinding().viewModel = getViewModel()
    }

    /* private fun setupScroll() {
         val list = getViewDataBinding().listVehicles

         list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                 val visiblePosition =
                     (list.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                 if (visiblePosition == (list.adapter as VehicleAdapter).itemCount - 1) {
                     getViewModel().input.nextVehiclePage()
                 }
             }
         })
     }*/
}