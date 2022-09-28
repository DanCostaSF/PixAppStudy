package br.com.android.exemplopix

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.BaseFragment
import br.com.android.exemplopix.commons.DialogsInterface
import br.com.android.exemplopix.commons.observeAndNavigateBack
import br.com.android.exemplopix.commons.validateField
import br.com.android.exemplopix.databinding.FragmentPixManualBinding
import java.util.*

// A toolbar tem que ficar fixa no topo. Remover o ícone de interrogação do topo.(X)
// CADE A FUNCIONALIDADE DE CLICK PARA MOSTRAR O SALDO - Depois tu vai criar um componente centralizado que tu só vai colar no XML depois.(X)
// A formatação da data: Qua 01 Setembro 2022
// Evitar possiblidade de duplicação de abertura do dialog(X)
// Para os itens que tem a setinha pra cima (^). Abrir um bottom sheet que ocupe só o tamanho necessário.(X)
//        Instituiçao Financeira          Fechar
//
// 260 - Nubnak
// 270 - Sicredi
// 300 - Itau
// Qdo clicar em algum item, retornar para a tela anterior e passar o valor seleciona no campo. ESSE COMPONENTE TBM NÃO pode ser alterado por digitação.(X)

// Tipo de conta (MESMO LAYOUT DE CIMA ^) : // Corrente // Poupança // Salario(X)
// Titulariadade (MESMO LAYOUT DE CIMA ^) : // Sim // Não(X)

// Quando todos os campos estiverem preenchidos, habilitar o botão.(X)
// O início da tela não terá nenhuma campoi preenchido. MENOS o campo da data que deve carregar do dia atual. Quando abrir o dialog da data, a data selecionada tem que estar selecionada no picker.
//  E tu so vai poder selecionar a data atual até 10 dias pra frente.
// Digitar o nome do cara tbm.(X)
// O valor lá de cima tem que ser um campo de digitação. Se o valor estiver zerado, considerar ele como campo vazio, para desabilitar. 0 tem que desabilitar o botão.

// Criar style pra tudo que tem texto.(X)
class PixManualFragment : BaseFragment<FragmentPixManualBinding>(
    R.layout.fragment_pix_manual
), DialogsInterface {

    private val _pixManualViewModel: PixManualViewModel by viewModels()

    private var isDpdOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setupEditTextData()
            setupClickShowMoney()
            setupEditTextFinanceiro()
            setupEditTextTypeAccount()
            setupEditTextTitularidade()
        }
    }

    private fun FragmentPixManualBinding.setupEditTextTitularidade() {
        edtTitularidade.setOnClickListener { showBottomSheetTitularity() }
    }

    private fun showBottomSheetTitularity() {
        val botSheet = BottomSheetTitularidadeFragment(this)
        botSheet.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
    }

    private fun FragmentPixManualBinding.setupEditTextTypeAccount() {
        edtTypeConta.setOnClickListener { showBottomSheetDialogTypeAccount() }
    }

    private fun showBottomSheetDialogTypeAccount() {
        val botSheet = BottomSheetTypeFragment(this)
        botSheet.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
    }

    private fun FragmentPixManualBinding.setupEditTextFinanceiro() {
        edtInstFinanceiro.setOnClickListener { showBottomSheetDialogFinanceiro() }
    }

    private fun showBottomSheetDialogFinanceiro() {
        val botSheet = BottomSheetFinanceiroFragment(this)
        botSheet.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
    }

    private fun FragmentPixManualBinding.setupClickShowMoney() {
        contentShowMoney.setOnClickListener {
            _pixManualViewModel.onTextViewVisibility()
        }
    }

    private fun FragmentPixManualBinding.setupEditTextData() {
        if (isDpdOpen) return
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.edtData.setText("$day/0${month+1}/$year")

        edtData.setOnClickListener {
            isDpdOpen = true
            val dpd = DatePickerDialog(
                requireContext(), { _, mYear, mMonth, mDay ->
                    binding.edtData.setText("$mDay/0${mMonth+1}/$mYear")
                    isDpdOpen = false
                },
                year,
                month,
                day
            )
            dpd.datePicker.maxDate = "1665220328000".toLong()
            dpd.show()
        }
    }

    override fun setupViewModel() {
        binding.vm = _pixManualViewModel
    }

    override fun setupObservers() {
        observeAndNavigateBack(_pixManualViewModel.onNavigateBack)

        _pixManualViewModel.buttonEnabled.observe(viewLifecycleOwner) {
            _pixManualViewModel.buttonIsEnabled(getAllFields())
        }
    }

    override fun titularity(text: String) {
        binding.edtTitularidade.setText(text)
    }

    override fun instFinanceira(text: String) {
        binding.edtInstFinanceiro.setText(text)
    }

    override fun typeAccount(text: String) {
        binding.edtTypeConta.setText(text)
    }

    private fun getAllFields() =
        listOf(
            binding.edtData,
            binding.edtDescript,
            binding.edtInstFinanceiro,
            binding.edtAgencia,
            binding.edtConta,
            binding.edtTypeConta,
            binding.edtTitularidade,
            binding.edtCpfCnpj
        ).validateField()
}