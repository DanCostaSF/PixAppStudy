package br.com.android.exemplopix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar

class PixFragment : Fragment() {

    private var _binding: FragmentPixBinding? = null
    private val binding get() = _binding!!

    private var type = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPixBinding.inflate(inflater, container, false)

        binding.apply {

            nextButton.setOnClickListener {
                val value = binding.editText.text
                if (value.isNullOrEmpty()) {
                    val snackbar =
                        Snackbar.make(
                            it,
                            "Digite um valor!",
                            Snackbar.LENGTH_SHORT
                        )
                    snackbar.show()
                } else {
                    val snackbar = Snackbar.make(
                        it,
                        value,
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
            }

            txtvToqueVizualizar.setOnClickListener {
                type += 1
                if (type == 1) {
                    binding.txtvSaldoLimite.visibility = View.GONE
                    binding.txtValor.visibility = View.VISIBLE
                } else if (type != 1) {
                    binding.txtvSaldoLimite.visibility = View.VISIBLE
                    binding.txtValor.visibility = View.GONE
                    type *= 0
                }
            }

            backButton.setOnClickListener {
                val snackbar = Snackbar.make(
                    it,
                    "Clicou",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
        }
        return binding.root
    }
}