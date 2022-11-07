package br.com.android.exemplopix.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.android.exemplopix.R
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.navBack
import br.com.android.exemplopix.commons.showAlertDialog
import br.com.android.exemplopix.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<FragmentInfoBinding>(
    R.layout.fragment_info
) {

    private val _infoViewModel: InfoViewModel by viewModels()

    private val args: InfoFragmentArgs by navArgs()

    private var isDialogOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
    }

    private fun setupList() {
        val list = args.nomes?.toMutableList()?.map { teste ->
             teste.replace("\\s+".toRegex(), " ")
        }?.filter {
            it.isNotEmpty() && it != " "
        }?.joinToString(separator = ", ") {
            it
        }

        binding.textView.text = if (list.isNullOrEmpty()) {
            "Nenhum parâmetro para listar."
        } else {
            "$list."
        }
    }

    private fun setupBackButton() {
        if (isDialogOpen) return

        isDialogOpen = true

        showAlertDialog(
            R.string.dialog_info,
            null,
            R.string.sim,
            { navBack() },
            R.string.nao,
            { isDialogOpen = false },
            true
        )
    }

    override fun setupViewModel() {
        binding.vm = _infoViewModel
    }

    override fun setupObservers() {
        _infoViewModel.onNavigateBack.observe(viewLifecycleOwner) { mustNavigate ->
            if (mustNavigate) setupBackButton()
        }
    }
}