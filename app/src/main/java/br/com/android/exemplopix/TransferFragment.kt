package br.com.android.exemplopix

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.android.exemplopix.commons.*
import br.com.android.exemplopix.databinding.CustomBottomSheetBinding
import br.com.android.exemplopix.databinding.FragmentTransferBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

// LayoutManager no XML direto(X)
// um divisor no itens(X)
// Usar apply ou run no init(X)
// Renomear fun init.(X)
// _transferViewModel.setData() renover. Já deixar a variável LiveData com a lista declarado direto(X)


// criar extended fun para fragment fun Fragment.observeAndNavigateBack(liveData: LiveData<Boolean>) {}(X)
// Dentro dele eu quero que tu centraliza po código abaixo ali dentro e atualize os outros framgent que temnav back
//         _transferViewModel.onNavigateBack.observe(viewLifecycleOwner) { mustNavigate ->
//            if (mustNavigate) navBack()
//        }(X)

// XML
// Quando clicar no container do Pix manual(X)
// Mostrar um efeito riple de toque.(X)
// BottomSheet que ocupe 90% da tela da tela. Que tenha um botão X no canto superior direito.(X)
// Qdo clicar no X, fechar a bottom sheet. Usuario tbm pode descer a bottom segurando e arrastando e ela.(X)

// Adicionar o botão (>) desabilitado.(X)

// Campo de texto(X)
// Se tem 11 dígitos. É um CPF valido. Se tem 14 digitos, é um CNPJ valido.(X)
// Validar email. Patterns. Ele tem uma forma de validar email.(X)


// QUando for válido, habilitar o botão (>). Quando clicar no botão, Exibir o tipo da chave pix valida.(X)
// Tipos: CPF, CNPJ ou EMAIL. Exibir uma snackbar.(X)
// Agora, quando exibir o tipo da chave, exibir ele formatado. Exemplo de CPF "123.123.232-89"(X)
// No fim, a snackbar vai ficar: "TIPO: CPF - VALOR: 123.123.232-89"(X)

// Criar um View.showSnackBar(){}(X)

class TransferFragment : BaseFragment<FragmentTransferBinding>(
    R.layout.fragment_transfer
) {

    private val _transferViewModel: TransferViewModel by viewModels()

    private lateinit var transferAdapter: TransferAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()

        binding.pixManual.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

        val sheetBinding: CustomBottomSheetBinding =
            CustomBottomSheetBinding.inflate(layoutInflater, null, false)

        sheetBinding.closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    private fun setupRecycler() {
        binding.recycler.run {
            setHasFixedSize(true)
            transferAdapter = TransferAdapter()
            adapter = transferAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun setupViewModel() {
        binding.vm = _transferViewModel
    }

    override fun setupObservers() {
        observeAndNavigateBack(_transferViewModel.onNavigateBack)

        _transferViewModel.snackBarString.observe(viewLifecycleOwner) { text ->
            binding.fab.setOnClickListener {
                it.showSnackBar(text)
            }
        }

        _transferViewModel.contacts.observe(viewLifecycleOwner) { persons ->
            transferAdapter.setData(persons)
        }
    }
}

