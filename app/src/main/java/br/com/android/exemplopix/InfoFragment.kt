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
            it
        }

        Log.i("teste", lista.toString())
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
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Dúvida?")
        builder.setMessage("Tem alguma dúvida?")
        builder.setPositiveButton("Sim") { _, _ ->
            navBack()
        }
        builder.setNegativeButton("Não") { _, _ ->
            binding.toolbar.isEnabled = true
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }
}