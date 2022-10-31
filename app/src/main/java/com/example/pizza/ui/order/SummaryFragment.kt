package com.example.pizza.ui.order

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pizza.R
import com.example.pizza.databinding.FragmentSummaryBinding
import com.example.pizza.ui.order.model.OrderViewModel

class SummaryFragment : Fragment() {
    private var binding: FragmentSummaryBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()
    private lateinit var flavors: String

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
    }

    fun sendOrder() {
        val numberOfPizzas = sharedOrderViewModel.quantity.value ?: 0

        val orderDetails = getString(
            R.string.order_details,
            resources.getQuantityString(R.plurals.pizzas, numberOfPizzas, numberOfPizzas),
            sharedOrderViewModel.getFlavorsString(),
            sharedOrderViewModel.size.value.toString(),
            sharedOrderViewModel.date.value,
            sharedOrderViewModel.price.value
        )

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_email_subject))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.order_details))
        }

        if(intent.resolveActivity(activity?.packageManager!!) != null)
            startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}