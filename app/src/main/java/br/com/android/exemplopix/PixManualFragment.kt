package br.com.android.exemplopix

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.*
import br.com.android.exemplopix.databinding.FragmentPixManualBinding
import com.google.android.material.textfield.TextInputEditText
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
// Qdo clicar em algum item, retornar para a tela anterior e passar o valor seleciona no campo.(X)
// ESSE COMPONENTE TBM NÃO pode ser alterado por digitação.(X)

// Tipo de conta (MESMO LAYOUT DE CIMA ^) : // Corrente // Poupança // Salario(X)
// Titulariadade (MESMO LAYOUT DE CIMA ^) : // Sim // Não(X)

// Quando todos os campos estiverem preenchidos, habilitar o botão.(X)
// O início da tela não terá nenhuma campoi preenchido.(X)
// MENOS o campo da data que deve carregar do dia atual.(X)
// Quando abrir o dialog da data, a data selecionada tem que estar selecionada no picker.(X)
//  E tu so vai poder selecionar a data atual até 10 dias pra frente.(X)
// Digitar o nome do cara tbm.(X)
// O valor lá de cima tem que ser um campo de digitação.(X)
// Se o valor estiver zerado, considerar ele como campo vazio, para desabilitar. 0 tem que desabilitar o botão.(X)

// Criar style pra tudo que tem texto.(X)
class PixManualFragment : BaseFragment<FragmentPixManualBinding>(
    R.layout.fragment_pix_manual
), DialogsInterface {

    private val _pixManualViewModel: PixManualViewModel by viewModels()

    private var isDpdOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val color = activity?.let { ContextCompat.getColorStateList(it.applicationContext, R.color.white) }
        ViewCompat.setBackgroundTintList(binding.edtMoneyChange, color)
        setupEditTextChangeMoney()

        binding.apply {
            setupEditTextData()
            setupClickShowMoney()
            setupEditTextFinanceiro()
            setupEditTextTypeAccount()
            setupEditTextTitularidade()
        }
    }

    private fun setupEditTextChangeMoney() {
        val mlocal = Locale("pt", "BR")
        binding.edtMoneyChange.addTextChangedListener(
            MaskMoney(binding.edtMoneyChange, mlocal) { vl ->
                val value = vl.toString()
                valor = value
            }
        )
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

    @SuppressLint("SetTextI18n")
    private fun FragmentPixManualBinding.setupEditTextData() {
        if (isDpdOpen) return
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        binding.edtData.setText("$day/0${month + 1}/$year")

        edtData.setOnClickListener {
            isDpdOpen = true
            val dpd = DatePickerDialog(
                requireContext(), { _, mYear, mMonth, mDay ->
                    if (mMonth <= 8 && mDay <= 9) {
                        binding.edtData.setText("0$mDay/0${mMonth + 1}/$mYear")
                    } else if (mMonth <= 8) {
                        binding.edtData.setText("$mDay/0${mMonth + 1}/$mYear")
                    } else if (mDay <= 9) {
                        binding.edtData.setText("0$mDay/${mMonth + 1}/$mYear")
                    } else {
                        binding.edtData.setText("$mDay/${mMonth + 1}/$mYear")
                    }

                    day = mDay
                    month = mMonth
                    year = mYear
                    isDpdOpen = false
                },
                year,
                month,
                day
            )
            dpd.datePicker
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
            if (valor == "0.00") {
                _pixManualViewModel.buttonIsEnabled(false)
            } else {
                _pixManualViewModel.buttonIsEnabled(getAllFields())
            }
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
            binding.edtCpfCnpj,
        ).validateField()

    companion object {
        private var valor = ""
    }
}