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
import java.util.*

// Criar componente centralizado.
// A formatação da data: Qua 01 Setembro 2022
// Evitar possiblidade de abertura duplicada dos bottom sheets.
// Quando abrir o bottom sheet, trazer a opção já selecionada anteriormente.
// Descrição é opcional, entao nao entra na regra de habilitar o botão.
// o CPF ou CNPJ tem que estar válido.
// E tu so vai poder selecionar a data atual até 10 dias pra frente.
class PixManualFragment : BaseFragment<FragmentPixManualBinding>(
    R.layout.fragment_pix_manual
), DialogsInterface {

    private val _pixManualViewModel: PixManualViewModel by viewModels()

    private var isDpdOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setupEditTextChangeMoney()
            setupEditTextData()
            setupClickShowMoney()
            setupEditTextFinanceiro()
            setupEditTextTypeAccount()
            setupEditTextTitularidade()
        }
    }

    private fun FragmentPixManualBinding.setupEditTextChangeMoney() {
        val mlocal = Locale("pt", "BR")
        edtMoneyChange.addTextChangedListener(
            MaskMoney(edtMoneyChange, mlocal) { vl ->
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
                requireContext(),
                { _, mYear, mMonth, mDay ->
                    filterDate(mMonth, mDay, mYear)
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

    @SuppressLint("SetTextI18n")
    private fun filterDate(mMonth: Int, mDay: Int, mYear: Int) {
        if (mMonth <= 8 && mDay <= 9) {
            binding.edtData.setText("0$mDay/0${mMonth + 1}/$mYear")
        } else if (mMonth <= 8) {
            binding.edtData.setText("$mDay/0${mMonth + 1}/$mYear")
        } else if (mDay <= 9) {
            binding.edtData.setText("0$mDay/${mMonth + 1}/$mYear")
        } else {
            binding.edtData.setText("$mDay/${mMonth + 1}/$mYear")
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