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
                binding.textView2.text = "$mDay/$mMonth/$mYear"
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