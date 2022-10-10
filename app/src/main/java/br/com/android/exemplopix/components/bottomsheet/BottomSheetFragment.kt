package br.com.android.exemplopix.components.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.databinding.BottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(
    private val typeBS: String,
    private val listType: List<String>
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetAdapter: BottomSheetAdapter

    private val viewModel: BottomSheetViewModel by viewModels()

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
        binding.txvType.text = typeBS
        setupViewModel()
        setupObservers()
        setupRecycler()
        setupAdapter()
    }

    private fun setupAdapter() {
        bottomSheetAdapter.setData(listType)
    }

    private fun setupRecycler() {
        bottomSheetAdapter = BottomSheetAdapter()
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

enum class TypeDialog(val text: String) {
    FINANCEIRO("Instituição Financeira"),
    TYPE_ACCOUNT("Tipo de conta"),
    TITULARIDADE("Titularidade")
}