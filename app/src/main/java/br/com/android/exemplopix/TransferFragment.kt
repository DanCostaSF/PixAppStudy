package br.com.android.exemplopix

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.navBack
import br.com.android.exemplopix.databinding.FragmentTransferBinding

// LayoutManager no XML direto
// um divisor no itens
// Usar apply ou run no init
// Renomear fun init.
// _transferViewModel.setData() renover. Já deixar a variável LiveData com a lista declarado direto
// criar extended fun para fragment fun Fragment.observeAndNavigateBack(liveData: LiveData<Boolean>) {}
// Dentro dele eu quero que tu centraliza po código abaixo ali dentro e atualize os outros framgent que temnav back
//         _transferViewModel.onNavigateBack.observe(viewLifecycleOwner) { mustNavigate ->
//            if (mustNavigate) navBack()
//        }

// XML
// Quando clicar no container do Pix manual
   // Mostrar um efeito riple de toque.
   // BottomSheet que ocupe 90% da tela da tela. Que tenha um botão X no canto superior direito.
   // Qdo clicar no X, fechar a bottom sheet. Usuario tbm pode descer a bottom segurando e arrastando e ela.

// Adicionar o botão (>) desabilitado.

// Campo de texto
// Se tem 11 dígitos. É um CPF valido. Se tem 14 digitos, é um CNPJ valido.
// Validar email. Patterns. Ele tem uma forma de validar email.


// QUando for válido, habilitar o botão (>). Quando clicar no botão, Exibir o tipo da chave pix valida.
// Tipos: CPF, CNPJ ou EMAIL. Exibir uma snackbar.
// Agora, quando exibir o tipo da chave, exibir ele formatado. Exemplo de CPF "123.123.232-89"
// No fim, a snackbar vai ficar: "TIPO: CPF - VALOR: 123.123.232-89"

// Criar um View.showSnackBar(){}
class TransferFragment : BaseFragment<FragmentTransferBinding>(
    R.layout.fragment_transfer
) {

    private val _transferViewModel: TransferViewModel by viewModels()

    private lateinit var transferAdapter: TransferAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        _transferViewModel.setData()
    }

    private fun init() {
        binding.recycler.setHasFixedSize(true)
        transferAdapter = TransferAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = transferAdapter
    }

    override fun setupViewModel() {
        binding.vm = _transferViewModel
    }

    override fun setupObservers() {
        _transferViewModel.onNavigateBack.observe(viewLifecycleOwner) { mustNavigate ->
            if (mustNavigate) navBack()
        }

        _transferViewModel.contacts.observe(viewLifecycleOwner) { persons ->
            transferAdapter.setData(persons)
        }
    }
}