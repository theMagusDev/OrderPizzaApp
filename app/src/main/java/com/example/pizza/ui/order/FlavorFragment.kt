package com.example.pizza.ui.order

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.pizza.R
import com.example.pizza.databinding.FragmentFlavorBinding
import com.example.pizza.ui.order.model.OrderViewModel

class FlavorFragment : Fragment() {
    private val DEFAULT_PIZZA_SIZE = 30
    private var binding: FragmentFlavorBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentFlavorBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentFlavorBinding
        return fragmentFlavorBinding.root
    }

    /*
    Assign a fragment value to the layout variable so that it can reference that fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            flavorFragment = this@FlavorFragment

            orderViewModel = sharedOrderViewModel
        }

        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Menu items here
                menuInflater.inflate(R.menu.layout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_cancel_order -> {
                        cancelOrder()
                        true
                    }
                    else -> {
                        // Otherwise, do nothing.
                        // Add other buttons behaviour here if have them.
                        true
                    }
                }

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    fun goToNextScreen () {
        findNavController().navigate(R.id.action_flavorFragment_to_sizeFragment)

        // Set default pizza's size
        sharedOrderViewModel.setSize(DEFAULT_PIZZA_SIZE)
    }

    fun cancelOrder() {
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
        sharedOrderViewModel.resetOrder()
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}