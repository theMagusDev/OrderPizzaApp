package com.example.pizza.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pizza.R
import com.example.pizza.databinding.FragmentFlavorBinding
import com.example.pizza.ui.order.model.OrderViewModel

private const val DEFAULT_PIZZA_SIZE = 35

class FlavorFragment : Fragment() {
    /* Binding object instance corresponding to the fragment_start.xml layout
    * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    * when the view hierarchy is attached to the fragment.
    */
    private var binding: FragmentFlavorBinding? = null
    private val sharedOrderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

    fun goToNextScreen () {
        findNavController().navigate(R.id.action_flavorFragment_to_sizeFragment)
        sharedOrderViewModel.setSize(DEFAULT_PIZZA_SIZE)
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