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

// Quando clicar no botão Próximo, navegar para a nova tela do print que enviei
// Quando usuario digitar alguma info, validar se é um CPF, CNPJ, celular ou email válido, se for, habilitar o botão (>) que está no canto inferior direito.
class PixFragment : BaseFragment<FragmentPixBinding>(
    R.layout.fragment_pix
) {

    private var isDialogOpen = false

    private val _pixViewModel: PixViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextSetup()

        binding.apply {
            setupToolbar()
            setupNextButton()
            setupToqueButton()
            setupContentShow()
        }
    }

    private fun FragmentPixBinding.setupContentShow() {
        clickMoneyShow.setOnClickListener {
            _pixViewModel.onTextViewVisibility2()
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

    private fun FragmentPixBinding.setupToolbar() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            _pixViewModel.onMenuClick(menuItem)
            true
        }
    }

    private fun setupInfoButton() {
        if (isDialogOpen) return

        isDialogOpen = true

        val list = arrayOf<String>()

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

    private fun FragmentPixBinding.setupToqueButton() {
        clickMoneyShow.setOnClickListener {
            _pixViewModel.onTextViewVisibility()
            _pixViewModel.onTextViewVisibility2()
        }
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

        _pixViewModel.menuClick.observe(viewLifecycleOwner) {
            if (it == true) {
                setupInfoButton()
                _pixViewModel.doneMenuClick()
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

