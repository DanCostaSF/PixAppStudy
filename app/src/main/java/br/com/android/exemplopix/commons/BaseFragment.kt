package br.com.android.exemplopix.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class UBRFragment<VDB : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = (DataBindingUtil.inflate(inflater, layoutId, container, false) as VDB).apply {
        lifecycleOwner = viewLifecycleOwner
        binding = this
    }.root

    abstract fun setupViewModel()

    abstract fun setupObservers()

}