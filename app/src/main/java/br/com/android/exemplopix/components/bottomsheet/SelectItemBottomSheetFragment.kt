package br.com.android.exemplopix.components.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.databinding.BottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// Para o bottom sheet
//abstract class BaseFragment<T : ViewDataBinding>(
//    @LayoutRes private val layoutId: Int
//) : Fragment() {
//
//    protected lateinit var binding: T
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = (DataBindingUtil.inflate(inflater, layoutId, container, false) as T).apply {
//        lifecycleOwner = viewLifecycleOwner
//        binding = this
//    }.root
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupViewModel()
//        setupObservers()
//    }
//
//    abstract fun setupViewModel()
//
//    a


class SelectItemBottomSheetFragment(
    private val typeBS: TypeDialog,
    private val listType: List<String?>,
    private val typeSelected: String?,
    private val typeStringDialog: (String?) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetAdapter: BottomSheetAdapter

    private val viewModel: SelectItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txvType.text = typeBS.title
        setupViewModel()
        setupObservers()
        setupRecycler()
        setupAdapter()
    }

    private fun setupAdapter() {
        bottomSheetAdapter.setData(listType)
        bottomSheetAdapter.setSelected(typeSelected)
    }

    private fun setupRecycler() {
        bottomSheetAdapter = BottomSheetAdapter {
            typeStringDialog.invoke(it)
            dismiss()
        }
        binding.recycler.adapter = bottomSheetAdapter
    }

    private fun setupObservers() {
        viewModel.closeDialog.observe(viewLifecycleOwner) {
            if (it) {
                dismiss()
                viewModel.doneCloseDialog()
            }
        }
    }

    private fun setupViewModel() {
        binding.vm = viewModel
    }
}