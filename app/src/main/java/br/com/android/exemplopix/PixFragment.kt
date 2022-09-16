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

// Rever o duplo click no info icon.(X)
// Nenhum parametro para listar somente deve ser exibir SE A LISTA estiver vazia. Agora, se tiver e um dos itens for vazio "", não exibir esse item.(X)
// showAlertDialog passar a receber o Int e fazer o getString lá dentro. (receber o menuItem por parâmetro) @StringRes
// Criar style de todos os textos exbidos. Inclusive o da outra tela. Criar style pro edit text que vai ser style.(X)
// ViewModel -> replicar o comportamente dos clicks. TBM do click do menuItem.(X)
// disable do botão no ViewModel, criando val isEnabled = MutableLiveData(false) ... isEnabled.value = true (X)
class PixFragment : BaseFragment<FragmentPixBinding>(
    R.layout.fragment_pix
) {

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
        menu.isEnabled = false
        val list = arrayOf(
            ""
        )
        showAlertDialog(
            getString(R.string.duvida),
            getString(R.string.duvida_dialog),
            getString(R.string.sim),
            { navTo(PixFragmentDirections.actionPixFragmentToInfoFragment(list)) },
            getString(R.string.nao),
            { menu.isEnabled = true },
            true
        )
        menu.isEnabled = true
    }

    private fun FragmentPixBinding.setupToqueButton() {
        clickMoneyShow.setOnClickListener {
            _pixViewModel.onTextViewVisibility()
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

        _pixViewModel.onTextView.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.txtvToqueVizualizar.isVisible(true)
                binding.txtValor.isVisible(false)
            } else {
                binding.txtvToqueVizualizar.isVisible(false)
                binding.txtValor.isVisible(true)
            }
        }

        _pixViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it == true) {
                showSnack()
                _pixViewModel.doneSnackBar()
            }
        }

        _pixViewModel.buttonDisable.observe(viewLifecycleOwner) {
            binding.nextButton.isEnabled = it

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

