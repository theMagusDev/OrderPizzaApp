package com.example.pizza

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pizza.databinding.FragmentFlavorBinding

class FlavorFragment : Fragment() {
    /* Binding object instance corresponding to the fragment_start.xml layout
    * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    * when the view hierarchy is attached to the fragment.
    */
    private var binding: FragmentFlavorBinding? = null

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
        binding?.flavorFragment = this
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