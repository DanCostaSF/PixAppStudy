package br.com.android.exemplopix.ui.pix

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.R
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.MaskMoney
import br.com.android.exemplopix.commons.MaskMoney.Companion.numero
import br.com.android.exemplopix.commons.navTo
import br.com.android.exemplopix.commons.showAlertDialog
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class PixFragment : BaseFragment<FragmentPixBinding>(
    R.layout.fragment_pix
) {

    private var isDialogOpen = false

    private val _pixViewModel: PixViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextSetup()
        binding.setupNextButton()

    }

    private fun editTextSetup() {
        val mlocal = Locale("pt", "BR")
        binding.apply {
            editText.addTextChangedListener(
                MaskMoney(editText, mlocal) { vl ->
                    _pixViewModel.onButtonDisable(vl.toString())
                }
            )
        }
    }

    private fun setupInfoButton() {
        if (isDialogOpen) return

        isDialogOpen = true

        val list = arrayOf(
            "    ",
            "Me da um chicletão", "boa noit bruno", "      ", "", ""
        )

        showAlertDialog(
            R.string.duvida,
            R.string.duvida_dialog,
            R.string.sim,
            { navTo(PixFragmentDirections.actionPixFragmentToInfoFragment(list)) },
            R.string.nao,
            { isDialogOpen = false },
            true
        )
    }

    private fun FragmentPixBinding.setupNextButton() {
        nextButton.setOnClickListener {
            _pixViewModel.showSnackBar()
            navTo(PixFragmentDirections.actionPixFragmentToTransferFragment())
        }
    }

    override fun setupViewModel() {
        binding.vm = _pixViewModel
    }

    override fun setupObservers() {
        _pixViewModel.onNavigateBack.observe(viewLifecycleOwner) { mustNavigate ->
            if (mustNavigate) requireActivity().finishAffinity()
        }

        _pixViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it == true) {
                showSnack()
                _pixViewModel.doneSnackBar()
            }
        }

        _pixViewModel.alertDialog.observe(viewLifecycleOwner) {
            if (it == true) {
                setupInfoButton()
                _pixViewModel.doneInfoButtonClick()
            }
        }
    }

    private fun showSnack() {
        val snackbar = Snackbar.make(
            binding.nextButton,
            numero.toString(),
            Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }
}


