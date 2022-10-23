package com.example.pizza.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pizza.databinding.FragmentPickupBinding
import com.example.pizza.ui.order.model.OrderViewModel

class PickupFragment : Fragment() {
    private var binding: FragmentPickupBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentFlavorBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentFlavorBinding
        return fragmentFlavorBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}