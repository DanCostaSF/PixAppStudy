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

// Colocar MaskMoney em outro package common(X)
// Criar um parametro para passar para o fragment de dúvidas. Parametro: List<String>?. (X)
// Quando tu chegar na tela de dúvidas, formatar as Strings em uma String só no seguinte formato:(X)
// Aleluia, Aiaiai, Danilo é brabo. Se passar apenas uma, tem que ter apenas o ponto.(X)
// Ex: Danilo. Se passar uma lista sem items ou o parametro nulo, exibir mensagem default: Nenhum parametro para listar.(X)
// Colocar as strings no arquivo string.xml(X)
// Criar uma extension para exibição de dialog com Sim e Não que receba: Titulo, mensagem, onNãoClick{} e onSimCLick() (X)
// Criar um style para o "Digite o valor da transferência"(X)
// Criar style para o botão(X)
// Criar o ViewModel e colocar tudo o que for possível de lógica lá.
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
            {},
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

    }

    override fun setupObservers() {
        binding.vm = _pixViewModel
    }
}



