package br.com.android.exemplopix

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.observeAndNavigateBack
import br.com.android.exemplopix.databinding.FragmentPixManualBinding
import java.util.*

// A toolbar tem que ficar fixa no topo. Remover o ícone de interrogação do topo.
// CADE A FUNCIONALIDADE DE CLICK PARA MOSTRAR O SALDO - Depois tu vai criar um componente centralizado que tu só vai colar no XML depois.
// A formatação da data: Qua 01 Setembro 2022
// Evitar possiblidade de duplicação de abertura do dialog
// Para os itens que tem a setinha pra cima (^). Abrir um bottom sheet que ocupe só o tamanho necessário.
   //        Instituiçao Financeira          Fechar
   //
   // 260 - Nubnak
   // 270 - Sicredi
   // 300 - Itau
// Qdo clicar em algum item, retornar para a tela anterior e passar o valor seleciona no campo. ESSE COMPONENTE TBM NÃO pode ser alterado por digitação.

// Tipo de conta (MESMO LAYOUT DE CIMA ^) : // Corrente // Poupança // Salario
// Titulariadade (MESMO LAYOUT DE CIMA ^) : // Sim // Não

// Quando todos os campos estiverem preenchidos, habilitar o botão.
// O início da tela não terá nenhuma campoi preenchido. MENOS o campo da data que deve carregar do dia atual. Quando abrir o dialog da data, a data selecionada tem que estar selecionada no picker.
//  E tu so vai poder selecionar a data atual até 10 dias pra frente.
// Digitar o nome do cara tbm.
// O valor lá de cima tem que ser um campo de digitação. Se o valor estiver zerado, considerar ele como campo vazio, para desabilitar. 0 tem que desabilitar o botão.

// Criar style pra tudo que tem texto.
class PixManualFragment : BaseFragment<FragmentPixManualBinding>(
    R.layout.fragment_pix_manual
) {

    private val _pixManualViewModel : PixManualViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        binding.edtData.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                binding.edtData.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)
            dpd.show()
        }
    }

    override fun setupViewModel() {
        binding.vm = _pixManualViewModel
    }

    override fun setupObservers() {
        observeAndNavigateBack(_pixManualViewModel.onNavigateBack)
    }
}