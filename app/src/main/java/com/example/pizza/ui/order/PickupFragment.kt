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
import com.example.pizza.databinding.FragmentPickupBinding
import com.example.pizza.ui.order.model.OrderViewModel

class PickupFragment : Fragment() {
    private var binding: FragmentPickupBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentFlavorBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentFlavorBinding
        return fragmentFlavorBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            orderViewModel = sharedOrderViewModel

            pickupFragment = this@PickupFragment
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
                        // Back button pressed
                        activity?.onBackPressedDispatcher?.onBackPressed()
                        true
                    }
                }

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_summaryFragment)
    }

    fun cancelOrder() {
        findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
        sharedOrderViewModel.resetOrder()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}