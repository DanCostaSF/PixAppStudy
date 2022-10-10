package br.com.android.exemplopix

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.botsheetsantigas.*
import br.com.android.exemplopix.commons.*
import br.com.android.exemplopix.components.bottomsheet.BottomSheetFragment
import br.com.android.exemplopix.components.bottomsheet.TypeDialog
import br.com.android.exemplopix.databinding.FragmentPixManualBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

//centralizar as bottom sheets
//Criar componente centralizado.
// Evitar possiblidade de abertura duplicada dos bottom sheets.

class PixManualFragment : BaseFragment<FragmentPixManualBinding>(
    R.layout.fragment_pix_manual
), DialogsInterface {

    private var titularity = ""
    private var financeiro = ""
    private var typeAccount = ""

    private val _pixManualViewModel: PixManualViewModel by viewModels()

    private var isDpdOpen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setupEditTextChangeMoney()
            setupEditTextData()
            setupEditTextBottomSheet()
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

    private fun FragmentPixManualBinding.setupEditTextBottomSheet() {
        edtTitularidade.setOnClickListener {
            openBottomSheet(
                TypeDialog.TITULARIDADE,
                mutableListOf("Sim", "Não"),
                titularity
            ) {
                edtTitularidade.setText(it)
                _pixManualViewModel.setTitularity(it!!)
            }
        }

        edtTypeConta.setOnClickListener {
            openBottomSheet(
                TypeDialog.TYPE_ACCOUNT,
                mutableListOf("Corrente", "Salário", "Poupança"),
                typeAccount
            ) {
                edtTypeConta.setText(it)
                _pixManualViewModel.setTypeAccount(it!!)
            }
        }

        edtInstFinanceiro.setOnClickListener {
            openBottomSheet(
                TypeDialog.FINANCEIRO,
                mutableListOf("260 - Nubank", "270 - Sicredi", "300 - Itaú"),
                financeiro
            ) {
                edtInstFinanceiro.setText(it)
                _pixManualViewModel.setFincanceiro(it!!)
            }
        }
    }

    private fun openBottomSheet(
        typeBS: TypeDialog, list: List<String>, text: String, typeDialog: (String?) -> Unit
    ) {
        val botsheet = BottomSheetFragment(
            typeBS,
            list,
            text
        ) {
            typeDialog.invoke(it)
        }
        botsheet.show(requireActivity().supportFragmentManager, "BottomSheet")
    }

    @SuppressLint("NewApi")
    private fun FragmentPixManualBinding.setupEditTextData() {
        if (isDpdOpen) return
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        binding.edtData.setText(filterDate(month, day, year))

        edtData.setOnClickListener {
            isDpdOpen = true
            val dpd = DatePickerDialog(
                requireContext(),
                { _, mYear, mMonth, mDay ->
                    binding.edtData.setText(filterDate(mMonth, mDay, mYear))
                    day = mDay
                    month = mMonth
                    year = mYear
                    isDpdOpen = false
                },
                year,
                month,
                day
            )
            val dat = Date()
            dpd.datePicker.minDate = dat.time
            dpd.datePicker.maxDate = dat.time + tenDaysInMiliSeconds
            dpd.show()
        }
        isDpdOpen = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterDate(mMonth: Int, mDay: Int, mYear: Int): String {
        val data = LocalDate.of(mYear, mMonth + 1, mDay)
        val simplaDateFormat = DateTimeFormatter.ofPattern("E dd LLLL yyyy", Locale("pt", "BR"))
        return data.format(simplaDateFormat)
    }

    override fun setupViewModel() {
        binding.vm = _pixManualViewModel
    }

    override fun setupObservers() {
        observeAndNavigateBack(_pixManualViewModel.onNavigateBack)

        var btn = false
        _pixManualViewModel.validCpf.observe(viewLifecycleOwner) {
            btn = it
        }

        _pixManualViewModel.buttonEnabled.observe(viewLifecycleOwner) {
            if (btn && valor != "0.00" && getAllFields()) {
                _pixManualViewModel.buttonOn()
            } else {
                _pixManualViewModel.buttonOff()
            }
        }

        _pixManualViewModel.titularity.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) titularity = it
        }

        _pixManualViewModel.financeiro.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) financeiro = it
        }

        _pixManualViewModel.typeAccount.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) typeAccount = it
        }
    }

    override fun titularity(text: String) {
        binding.edtTitularidade.setText(text)
    }

    override fun instFinanceira(text: TypeBank) {
        binding.edtInstFinanceiro.setText(text.text)
        type = text.text
    }

    override fun typeAccount(text: String) {
        binding.edtTypeConta.setText(text)
    }

    private fun getAllFields() =
        listOf(
            binding.edtData,
            binding.edtBeneficiario,
            binding.edtInstFinanceiro,
            binding.edtAgencia,
            binding.edtConta,
            binding.edtTypeConta,
            binding.edtTitularidade
        ).validateField()

    companion object {
        private var valor = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        type = ""
    }
}

