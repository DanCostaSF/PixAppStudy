package br.com.android.exemplopix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.DialogsInterface
import br.com.android.exemplopix.databinding.BottomSheetTypeAccountBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTypeFragment(private val dialogsInterface: DialogsInterface) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetTypeAccountBinding? = null
    private val binding get() = _binding!!

    private val _bottomSheetViewModel: BottomSheetTypeCViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetTypeAccountBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        binding.vm = _bottomSheetViewModel
    }

    private fun setupObservers() {
        _bottomSheetViewModel.closeButton.observe(viewLifecycleOwner) {
            if (it) dialog?.dismiss()
        }

        _bottomSheetViewModel.checkCorrente.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.typeAccount("Conta Corrente")
        }

        _bottomSheetViewModel.checkPoupanca.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.typeAccount("Conta Poupança")
        }

        _bottomSheetViewModel.checkSalario.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.typeAccount("Conta Salário")
        }

    }
}