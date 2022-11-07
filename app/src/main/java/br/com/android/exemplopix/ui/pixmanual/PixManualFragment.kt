package br.com.android.exemplopix.ui.pixmanual

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import br.com.android.exemplopix.R
import br.com.android.exemplopix.commons.*
import br.com.android.exemplopix.ui.components.bottomsheet.SelectItemBottomSheetFragment
import br.com.android.exemplopix.ui.components.bottomsheet.TypeDialog
import br.com.android.exemplopix.databinding.FragmentPixManualBinding
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class PixManualFragment : BaseFragment<FragmentPixManualBinding>(
    R.layout.fragment_pix_manual
), DialogsInterface {

    private val bottomSheetTag = "BottomSheet"

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
            MaskMoney(
                edtMoneyChange,
                mlocal
            ) { vl ->
                val value = vl.toString()
                valor = value
            })
    }

    private fun FragmentPixManualBinding.setupEditTextBottomSheet() {
        openBottomSheet(
            edtTitularidade,
            TypeDialog.TITULARIDADE,
            listOf("Sim", "Não"),
            _pixManualViewModel.titularity
        ) {

            _pixManualViewModel.setTitularity(it!!)
        }

        openBottomSheet(
            edtTypeConta,
            TypeDialog.TYPE_ACCOUNT,
            listOf("Corrente", "Salário", "Poupança"),
            _pixManualViewModel.typeAccount,
        ) { typeAccount ->
            typeAccount?.let {
                _pixManualViewModel.setTypeAccount(it)
            }
        }

        val listaFinanceiro = listOf("260 - Nubank", "270 - Sicredi", "300 - Itaú")

        openBottomSheet(
            edtInstFinanceiro,
            TypeDialog.FINANCEIRO,
            listaFinanceiro,
            _pixManualViewModel.financeiro
        ) { finance ->
            finance?.let {
                _pixManualViewModel.setFincanceiro(it)
            }
        }
    }

    private fun openBottomSheet(
        editText: TextInputEditText,
        typeBS: TypeDialog,
        list: List<String?>,
        liveData: LiveData<String>,
        typeDialog: (String?) -> Unit
    ) {
        editText.setOnClickListener {
            val botsheet = SelectItemBottomSheetFragment(
                typeBS, list, liveData.value
            ) {
                typeDialog.invoke(it)
            }
            botsheet.show(requireActivity().supportFragmentManager, bottomSheetTag)
        }
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
                requireContext(), { _, mYear, mMonth, mDay ->
                    binding.edtData.setText(filterDate(mMonth, mDay, mYear))
                    day = mDay
                    month = mMonth
                    year = mYear
                    isDpdOpen = false
                }, year, month, day
            )
            val dat = Date()
            dpd.datePicker.minDate = dat.time
            dpd.datePicker.maxDate = dat.time + tenDaysInMiliSeconds
            dpd.show()
        }
        isDpdOpen = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterDate(
        mMonth: Int,
        mDay: Int,
        mYear: Int
    ): String {
        val data = LocalDate.of(mYear, mMonth + 1, mDay)
        val simplaDateFormat = DateTimeFormatter.ofPattern(
            "E dd LLLL yyyy",
            Locale("pt", "BR")
        )
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

    private fun getAllFields() = listOf(
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

