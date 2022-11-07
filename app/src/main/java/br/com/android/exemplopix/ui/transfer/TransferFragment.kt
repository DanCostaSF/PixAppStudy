package br.com.android.exemplopix.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.android.exemplopix.R
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.navTo
import br.com.android.exemplopix.commons.observeAndNavigateBack
import br.com.android.exemplopix.commons.showSnackBar
import br.com.android.exemplopix.databinding.FragmentTransferBinding

class TransferFragment : BaseFragment<FragmentTransferBinding>(
    R.layout.fragment_transfer
) {

    private val _transferViewModel: TransferViewModel by viewModels()

    private lateinit var transferAdapter: TransferAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
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

        _transferViewModel.onNavigateToTransfer.observe(viewLifecycleOwner) {
            if (it) {
                navTo(TransferFragmentDirections.actionTransferFragmentToPixManualFragment())
                _transferViewModel.onNavigateOff()
            }
        }

        _transferViewModel.snackBarInsertString.observe(viewLifecycleOwner) { text ->
            binding.fab.setOnClickListener {
                it.showSnackBar(text)
            }
        }

        _transferViewModel.contacts.observe(viewLifecycleOwner) { persons ->
            transferAdapter.setData(persons)
        }
    }
}

