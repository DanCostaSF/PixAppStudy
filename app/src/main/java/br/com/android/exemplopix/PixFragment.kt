package br.com.android.exemplopix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.android.exemplopix.commons.*
import br.com.android.exemplopix.commons.MaskMoney.Companion.numero
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

// arrayOf("  ", "  ") -> Lista deixa um ponto na tela. Remover espaços em uma String Kotlin. E ainda apenas um filter e apenas uma lista.(X)
// Cria um novo campo de texto, com label "Digite aqui a frase", e que receba um texto qualquer.(X)
// Embaixo do campo de texto, coloque um TextView que tenha o valor.(X)
// 0 Fragment! kkk Two way data binding.(X)

// " - Texto complementar"

// Click do info icon na viewModel
class PixFragment : BaseFragment<FragmentPixBinding>(
    R.layout.fragment_pix
) {

    private var isDialogOpen = false

    private val _pixViewModel: PixViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextSetup()

        binding.apply {
            setupNextButton()
            setupContentShow()
        }
    }

    private fun FragmentPixBinding.setupContentShow() {
        clickMoneyShow.setOnClickListener {
            _pixViewModel.onTextViewVisibility()
        }
    }

    private fun editTextSetup() {
        val mlocal = Locale("pt", "BR")
        binding.editText.addTextChangedListener(
            MaskMoney(binding.editText, mlocal) { vl ->
                val value = vl.toString()
                _pixViewModel.onButtonDisable(value)
            }
        )
    }

    private fun setupInfoButton() {
        if (isDialogOpen) return

        isDialogOpen = true

        val list = arrayOf("    ",
                            "Me da um chicletão", "boa noit bruno", "      ", "", "")

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

