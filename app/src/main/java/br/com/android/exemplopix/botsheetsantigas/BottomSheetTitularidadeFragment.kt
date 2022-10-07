package br.com.android.exemplopix.botsheetsantigas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.android.exemplopix.commons.DialogsInterface
import br.com.android.exemplopix.databinding.BottomSheetTitularityBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTitularidadeFragment(private val dialogsInterface: DialogsInterface) : BottomSheetDialogFragment(){

    private var _binding: BottomSheetTitularityBinding? = null
    private val binding get() = _binding!!

    private val _bottomSheetViewModel: BotSheetTitularidadeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetTitularityBinding.inflate(inflater, container, false)
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

        _bottomSheetViewModel.checkNo.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.titularity("Não" )
        }

        _bottomSheetViewModel.checkYes.observe(viewLifecycleOwner) {
            if (it) dialogsInterface.titularity("Sim")
        }
    }
}