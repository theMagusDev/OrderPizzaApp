package com.example.pizza.ui.order

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.example.pizza.databinding.FragmentSummaryBinding
import com.example.pizza.ui.order.model.OrderViewModel

class SummaryFragment : Fragment() {
    private var binding: FragmentSummaryBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val summaryFragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = summaryFragmentBinding
        return summaryFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            orderViewModel = sharedOrderViewModel
            summaryFragment = this@SummaryFragment
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

    fun sendOrder() {
        val numberOfPizzas = sharedOrderViewModel.quantity.value ?: 0

        val orderDetails = getString(
            R.string.order_details,
            resources.getQuantityString(R.plurals.pizzas, numberOfPizzas, numberOfPizzas),
            sharedOrderViewModel.getFlavorsString(),
            sharedOrderViewModel.size.value.toString(),
            sharedOrderViewModel.date.value,
            sharedOrderViewModel.total.value
        )

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_email_subject))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.order_details))
        }

        if(intent.resolveActivity(activity?.packageManager!!) != null)
            startActivity(intent)
    }

    fun cancelOrder() {
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
        sharedOrderViewModel.resetOrder()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}