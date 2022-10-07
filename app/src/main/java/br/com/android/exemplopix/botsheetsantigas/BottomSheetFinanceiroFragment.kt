package br.com.android.exemplopix.botsheetsantigas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.DialogsInterface
import br.com.android.exemplopix.databinding.BottomSheetFinanceiroBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFinanceiroFragment(
    private val dialogsInterface: DialogsInterface
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFinanceiroBinding? = null
    private val binding get() = _binding!!

    private val _bottomSheetViewModel: BottomSheetFinanceiroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFinanceiroBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
    }
//        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        binding.layout.minHeight =
//            (Resources.getSystem().displayMetrics.heightPixels * 0.80).toInt()

    private fun setupViewModel() {
        binding.vm = _bottomSheetViewModel
    }

    private fun setupObservers() {
        _bottomSheetViewModel.closeButton.observe(viewLifecycleOwner) {
            if (it) dialog?.dismiss()
        }

        _bottomSheetViewModel.teste.observe(viewLifecycleOwner) {
            when (it) {
                TypeBank.ITAU.text -> {
                    _bottomSheetViewModel.trueItau()
                }
                TypeBank.SICREDI.text -> {
                    _bottomSheetViewModel.trueSicredi()
                }
                TypeBank.NUBANK.text -> {
                    _bottomSheetViewModel.trueNubank()
                }
                else -> {
                    _bottomSheetViewModel.falseAll()
                }
            }
        }

        _bottomSheetViewModel.checkItau.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.instFinanceira(TypeBank.ITAU)
        }

        _bottomSheetViewModel.checkNubank.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.instFinanceira(TypeBank.NUBANK)
        }

        _bottomSheetViewModel.checkSicredi.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.instFinanceira(TypeBank.SICREDI)
        }
    }
}

enum class TypeBank(val text: String) {
    NUBANK("260 - Nubank"),
    SICREDI("270 - Sicredi"),
    ITAU("300 - Itaú")
}

var type = ""