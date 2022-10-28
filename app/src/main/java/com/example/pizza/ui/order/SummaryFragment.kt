package com.example.pizza.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
        flavors = sharedOrderViewModel.flavors.value!!.joinToString(", ")
        Log.d("SummaryFragment", flavors)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            orderViewModel = sharedOrderViewModel
            summaryFragment = this@SummaryFragment
        }

    }

    fun getFlavors() : String {
        return flavors
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}