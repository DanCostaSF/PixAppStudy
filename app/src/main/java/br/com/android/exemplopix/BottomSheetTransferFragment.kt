package br.com.android.exemplopix

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.android.exemplopix.databinding.CustomBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


//        binding.pixManual.setOnClickListener { showBottomSheetDialog() }
//    private fun showBottomSheetDialog() {
//        val botSheet = BottomSheetTransferFragment()
//        botSheet.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
//    }


class BottomSheetTransferFragment : BottomSheetDialogFragment() {

    private var _binding: CustomBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val _bottomSheetViewModel: BottomSheetTransferViewModel by viewModels()

    private lateinit var botSheetAdapter: BottomSheetTransferAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.layout.minHeight =
            (Resources.getSystem().displayMetrics.heightPixels * 0.80).toInt()

        setupRecycler()

    }

    private fun setupRecycler() {
        binding.recycler.run {
            setHasFixedSize(true)
            botSheetAdapter = BottomSheetTransferAdapter()
            adapter = botSheetAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupViewModel() {
        binding.vm = _bottomSheetViewModel
    }

    private fun setupObservers() {
        _bottomSheetViewModel.closeButton.observe(viewLifecycleOwner) {
            if (it) dialog?.dismiss()
        }

        _bottomSheetViewModel.contacts.observe(viewLifecycleOwner) { persons ->
            botSheetAdapter.setData(persons)
        }
    }
}