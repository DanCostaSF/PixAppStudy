package br.com.android.exemplopix

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.*
import br.com.android.exemplopix.commons.MaskMoney.Companion.numero
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

// val list = arrayOf(" ") aparece um ponto(X)
// val list = arrayOf("123", " ", "456")
// ViewModel -> replicar o comportamente dos clicks. TBM do click do menuItem.(X)
// disable do botão no ViewModel, criando val isEnabled = MutableLiveData(false) ... isEnabled.value = true (X)
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
            if (menuItem.itemId == R.id.info_button) {
                setupInfoButton(menuItem)
            }
            true
        }
    }

    private fun setupInfoButton(menu: MenuItem) {
        if (isDialogOpen) return

        isDialogOpen = true

        val list = emptyArray<String>()

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
            _pixViewModel.onBalanceContainerClick()
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

