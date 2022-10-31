package com.example.pizza.ui.order

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.pizza.R
import com.example.pizza.databinding.FragmentSizeBinding
import com.example.pizza.ui.order.model.OrderViewModel

class SizeFragment : Fragment() {
    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentSizeBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentSizeBinding = FragmentSizeBinding.inflate(inflater, container, false)
        binding = fragmentSizeBinding
        return fragmentSizeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            sizeFragment = this@SizeFragment

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

    fun updateSize(size: Int) {
        sharedOrderViewModel.setSize(size)
        binding?.pizza30cmRadioButton?.isChecked = sharedOrderViewModel.size.value!! == 30
        binding?.pizza45cmRadioButton?.isChecked = sharedOrderViewModel.size.value!! == 45
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_sizeFragment_to_pickupFragment)

        // Set the default pickup date
        if (sharedOrderViewModel.hasNoPickupDateSelected())
            sharedOrderViewModel.setPickupDate(sharedOrderViewModel.dateOptions[0])
    }

    fun cancelOrder() {
        findNavController().navigate(R.id.action_sizeFragment_to_startFragment)
        sharedOrderViewModel.resetOrder()
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}