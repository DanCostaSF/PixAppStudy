package br.com.android.exemplopix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.MaskMoney
import br.com.android.exemplopix.commons.MaskMoney.Companion.numero
import br.com.android.exemplopix.commons.navTo
import br.com.android.exemplopix.commons.showAlertDialog
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

// Rever o duplo click no info icon.
// Nenhum parametro para listar somente deve ser exibir SE A LISTA estiver vazia. Agora, se tiver e um dos itens for vazio "", não exibir esse item.
// showAlertDialog passar a receber o Int e fazer o getString lá dentro. (receber o menuItem por parâmetro) @StringRes
// Criar style de todos os textos exbidos. Inclusive o da outra tela. Criar style pro edit text que vai ser style.
// View -> replicar o comportamente dos clicks. TBM do click do menuItem.
// disable do botão no ViewModel, criando val isEnabled = MutableLiveData(false) ... isEnabled.value = true
class PixFragment : BaseFragment<FragmentPixBinding>(
    R.layout.fragment_pix
) {

    private val _pixViewModel: PixViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextSetup()

        binding.apply {
            setupToolbar()
            setupNavigationListener()
            setupNextButton()
            setupToqueButton()
        }
    }

    private fun editTextSetup() {
        val mlocal = Locale("pt", "BR")
        binding.editText.addTextChangedListener(
            MaskMoney(binding.editText, mlocal) { vl ->
                val value = vl.toString()
                binding.nextButton.isEnabled = value != "0.00"
            }
        )
    }

    private fun FragmentPixBinding.setupNavigationListener() {
        toolbar.setNavigationOnClickListener {
            requireActivity().finishAffinity()
        }
    }

    private fun FragmentPixBinding.setupToolbar() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.info_button) {
                menuItem.isEnabled = false
                setupInfoButton()
            }
            menuItem.isEnabled = true
            true
        }
    }

    private fun setupInfoButton() {
        val list = arrayOf(
            "oi",
            "alo",
            "",
            "Teste"
        )
        showAlertDialog(
            getString(R.string.duvida),
            getString(R.string.duvida_dialog),
            getString(R.string.sim),
            { navTo(PixFragmentDirections.actionPixFragmentToInfoFragment(list)) },
            getString(R.string.nao),
            { },
            true
        )
    }

    private fun FragmentPixBinding.setupToqueButton() {
        clickMoneyShow.setOnClickListener {
            if (binding.txtvToqueVizualizar.visibility == View.GONE) {
                binding.txtvToqueVizualizar.visibility = View.VISIBLE
                binding.txtValor.visibility = View.GONE
            } else {
                binding.txtvToqueVizualizar.visibility = View.GONE
                binding.txtValor.visibility = View.VISIBLE
            }
        }
    }

    private fun FragmentPixBinding.setupNextButton() {
        nextButton.setOnClickListener {
            val snackbar = Snackbar.make(
                it,
                numero.toString(),
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }
    }

    override fun setupViewModel() {
        binding.vm = _pixViewModel
    }

    override fun setupObservers() {
        _pixViewModel.onNavigateBack.observe(viewLifecycleOwner) { mustNavigate ->
            if (mustNavigate) requireActivity().finishAffinity()
        }
    }

}



