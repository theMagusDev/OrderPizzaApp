package com.example.pizza.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pizza.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    private var binding: FragmentSummaryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val summaryFragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = summaryFragmentBinding
        return summaryFragmentBinding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}