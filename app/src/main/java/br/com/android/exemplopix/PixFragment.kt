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

// Se o valor estiver vazio, desabilitar o botão Próximo, portanto tu pode remover o teste de o valor estar vazio no show toast.( )
// Replicar botão do navigation E quando clicar em voltar, perguntar em um novo dialog se usuário quer voltar mesmo. Se clicar em não, continua na tela de dúvidas. Se clicar em sim, fechar a tela atual.(X)
// Colocar MaskMoney em outro package common(Falta desabilitar o button)
// O valor/TOque para visualizar seja alterado de acordo com o container(X)
// Controlar quando clicar no dialog para não permitir abrir duas vezes o dialog(X)
// Colocar click fora para fechar todos os dialogs.(X)
// Criar um parametro para passar para o fragment de dúvidas. Parametro: List<String>?. Quando tu chegar na tela de dúvidas, formatar as Strings em uma String só no seguinte formato:
// Aleluia, Aiaiai, Danilo é brabo. Se passar apenas uma, tem que ter apenas o ponto. Ex: Danilo. Se passar uma lista sem items ou o parametro nulo, exibir mensagem default: Nenhum parametro para listar.
// Na primeira tela, os campos não podem se sobrepor.(X)
// Na segunda tela, a frase "Voce chegou..." precisa estar centralizada.(X)
// Colocar um valor máximo de 99.999,99(X)

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
        binding.editText.addTextChangedListener(MaskMoney(binding.editText, mlocal))

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



