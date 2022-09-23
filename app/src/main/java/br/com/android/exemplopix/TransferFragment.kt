package br.com.android.exemplopix

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.observeAndNavigateBack
import br.com.android.exemplopix.commons.showSnackBar
import br.com.android.exemplopix.databinding.CustomBottomSheetBinding
import br.com.android.exemplopix.databinding.FragmentTransferBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

// BottomSheet que ocupe 90% da tela da tela. Que tenha um botão X no canto superior direito.(X)
// Colocar um titulo, um subtitulo, e uma igual dessa tela a da tela Transfer

class TransferFragment : BaseFragment<FragmentTransferBinding>(
    R.layout.fragment_transfer
) {

    private val _transferViewModel: TransferViewModel by viewModels()

    private lateinit var transferAdapter: TransferAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()

        binding.pixManual.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

        val sheetBinding: CustomBottomSheetBinding =
            CustomBottomSheetBinding.inflate(layoutInflater, null, false)

        sheetBinding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    private fun setupRecycler() {
        binding.recycler.run {
            setHasFixedSize(true)
            transferAdapter = TransferAdapter()
            adapter = transferAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun setupViewModel() {
        binding.vm = _transferViewModel
    }

    override fun setupObservers() {
        observeAndNavigateBack(_transferViewModel.onNavigateBack)

        _transferViewModel.snackBarString.observe(viewLifecycleOwner) { text ->
            binding.fab.setOnClickListener {
                it.showSnackBar(text)
            }
        }

        _transferViewModel.contacts.observe(viewLifecycleOwner) { persons ->
            transferAdapter.setData(persons)
        }
    }
}

