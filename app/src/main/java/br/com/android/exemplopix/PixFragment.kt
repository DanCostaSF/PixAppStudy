package br.com.android.exemplopix

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.android.exemplopix.commons.MaskMoney
import br.com.android.exemplopix.commons.MaskMoney.Companion.numero
import br.com.android.exemplopix.commons.navTo
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

// Colocar MaskMoney em outro package common(Falta desabilitar o button)
// Criar um parametro para passar para o fragment de dúvidas. Parametro: List<String>?. Quando tu chegar na tela de dúvidas, formatar as Strings em uma String só no seguinte formato:
// Aleluia, Aiaiai, Danilo é brabo. Se passar apenas uma, tem que ter apenas o ponto. Ex: Danilo. Se passar uma lista sem items ou o parametro nulo, exibir mensagem default: Nenhum parametro para listar.
// Colocar as strings no arquivo string.xml
// Criar uma extension para exibição de dialog com Sim e Não que receba: Titulo, mensagem, onNãoClick{} e onSimCLick()
// Criar um style para o "Digite o valor da transferência"
// Criar style para o botão
// Criar o ViewModel e colocar tudo o que for possível de lógica lá.
class PixFragment : Fragment() {

    private var _binding: FragmentPixBinding? = null
    private val binding get() = _binding!!

    // Legal no onCreateView() é ter apenas uma função, que é inflar o layout (O que na verdade nem vamos mais fazer pq vai estar no BaseFragment)

    // Agora para adicionar comportamentos, o legal é utilizar o onViewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mlocal = Locale("pt", "BR")
        binding.editText.addTextChangedListener(
            MaskMoney(binding.editText, mlocal) { value ->
                // Desabilitar aqui...
            }
        )

        val nomes = arrayOf(
            "teste 1",
            "teste 2"
        )

        binding.apply {
            setupToolbar(nomes)
            setupNavigationListener()
            setupNextButton()
            setupToqueButton()
        }
    }

    private fun FragmentPixBinding.setupNavigationListener() {
        toolbar.setNavigationOnClickListener {
            requireActivity().finishAffinity()
        }
    }

    private fun FragmentPixBinding.setupToolbar(list: Array<String>?) {
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.info_button) {
                menuItem.isEnabled = false
                setupInfoButton(list)
            }
            menuItem.isEnabled = true
            true
        }
    }

    private fun setupInfoButton(list: Array<String>?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Dúvida?")
        builder.setMessage("Tem alguma dúvida?")
        builder.setPositiveButton("Sim") { _, _ ->
            navTo(PixFragmentDirections.actionPixFragmentToInfoFragment(
                list
            ))
        }
        builder.setNegativeButton("Não") { _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
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
}



