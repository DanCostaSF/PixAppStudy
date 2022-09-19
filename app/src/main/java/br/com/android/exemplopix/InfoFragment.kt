package br.com.android.exemplopix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.android.exemplopix.commons.navBack
import br.com.android.exemplopix.commons.showAlertDialog
import br.com.android.exemplopix.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val args: InfoFragmentArgs by navArgs()

    private var isDialogOpen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = args.nomes?.toMutableList()
        val list2 = list?.filter {
            it.isNotEmpty()
        }

        val lista = list2?.joinToString(separator = ", ") {
                it
            }

        binding.textView.text = if (list2.isNullOrEmpty() || list2.contains(" ") || list2.contains("  "))  {
            "Nenhum parâmetro para listar."
        } else {
            "$lista."
        }

        binding.apply {
            setupNavigationListener()
        }
    }

    private fun FragmentInfoBinding.setupNavigationListener() {
        toolbar.setNavigationOnClickListener {
            setupBackButton()
        }
    }

    private fun setupBackButton() {
        if (isDialogOpen) return

        isDialogOpen = true

        showAlertDialog(
            R.string.dialog_info,
            null,
            R.string.sim,
            { navBack() },
            R.string.nao,
            { isDialogOpen = false },
            true
        )
    }
}