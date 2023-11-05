package com.example.appdev1.ui.frags

import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.appdev1.R
import com.example.appdev1.base.BaseFragment
import com.example.appdev1.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {

    override val binding by viewBinding(FragmentFirstBinding::bind)

    override fun initialize() = with(binding) {
        btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
        btnExit.setOnClickListener {
            activity?.finish()
        }
    }
}