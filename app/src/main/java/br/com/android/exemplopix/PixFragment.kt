package br.com.android.exemplopix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar

// Separar onCreateView e onViewCreated
// Separar os comportamentos em novas funções
// Se o valor estiver vazio, desabilitar o botão Próximo, poratnto tu pode remover o teste de o valor estar vazio no show toast.
// Ambos os valores que são exibidos, formatar para BRL - R$ 10.899,00
// Toque para visualizar - Ele mesmo seja alterados, no caso.
// Click no <, voltar para a tela anterior. No fim, eu quero que ele feche o app pq é a primeira tela.
// Quando clicar no (?) mostrar um dialog.
// Titulo: Dúvida? Mensagem: Tem alguma dúdiva? Dois botões: Sim e Não.
// Clicar no Não: fecha o dialog. Clicou no sim: Navegar para um novo fragment.
// Você chegou no fragment dúvidas. Titulo: Dúvida Pix. (<) Quando clicar no voltar, voltar para a tela anterior.
// Trocar a toolbar para <com.google.android.material.appbar.AppBarLayout e dentro dele <com.google.android.material.appbar.MaterialToolbar
// Scroll view direto embaixo do Toolbar
class PixFragment : Fragment() {

    private var _binding: FragmentPixBinding? = null
    private val binding get() = _binding!!

    private var type = 0


    // Legal no onCreateView() é ter apenas uma função, que é inflar o layout (O que na verdade nem vamos mais fazer pq vai estar no BaseFragment)

    // Agora para adicionar comportamentos, o legal é utilizar o onViewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            setupNextButton()

            txtvToqueVizualizar.setOnClickListener {
                type += 1
                if (type == 1) {
                    binding.txtvSaldoLimite.visibility = View.GONE
                    binding.txtValor.visibility = View.VISIBLE
                } else if (type != 1) {
                    binding.txtvSaldoLimite.visibility = View.VISIBLE
                    binding.txtValor.visibility = View.GONE
                    type *= 0
                }
            }

            backButton.setOnClickListener {
                val snackbar = Snackbar.make(
                    it,
                    "Clicou",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
        }
    }

    private fun FragmentPixBinding.setupNextButton() {
        nextButton.setOnClickListener {
            val value = binding.editText.text
            if (value.isNullOrEmpty()) {
                val snackbar =
                    Snackbar.make(
                        it,
                        "Digite um valor!",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.show()
            } else {
                val snackbar = Snackbar.make(
                    it,
                    value,
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
        }
    }
}