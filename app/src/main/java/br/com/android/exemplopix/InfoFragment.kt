package br.com.android.exemplopix

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.android.exemplopix.commons.navBack
import br.com.android.exemplopix.commons.navTo
import br.com.android.exemplopix.commons.showAlertDialog
import br.com.android.exemplopix.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val args: InfoFragmentArgs by navArgs()

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
        val lista = list?.joinToString(separator = ", ") {
            if (it.isEmpty()) {
                return@joinToString "Nenhum parametro para listar"
            }
            it
        }

        binding.textView.text = "$lista."

//        Log.i("teste", lista.toString())
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
        binding.toolbar.isEnabled = false
        showAlertDialog(
            getString(R.string.dialog_info),
            null,
            getString(R.string.sim),
            { navBack() },
            getString(R.string.nao),
            { binding.toolbar.isEnabled = true },
            true
        )
    }
}