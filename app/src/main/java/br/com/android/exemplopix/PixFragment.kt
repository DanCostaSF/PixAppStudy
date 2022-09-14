package br.com.android.exemplopix

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import br.com.android.exemplopix.commons.navTo
import br.com.android.exemplopix.databinding.FragmentPixBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

// Separar onCreateView e onViewCreated (X)
// Separar os comportamentos em novas funções (X)
// Se o valor estiver vazio, desabilitar o botão Próximo, portanto tu pode remover o teste de o valor estar vazio no show toast.( )
// Ambos os valores que são exibidos, formatar para BRL - R$ 10.899,00(X)
// Toque para visualizar - Ele mesmo seja alterados, no caso.(X)
// Click no <, voltar para a tela anterior. No fim, eu quero que ele feche o app pq é a primeira tela.(X)
// Quando clicar no (?) mostrar um dialog.(X)
// Titulo: Dúvida? Mensagem: Tem alguma dúdiva? Dois botões: Sim e Não.(X)
// Clicar no Não: fecha o dialog. Clicou no sim: Navegar para um novo fragment.(X)
// Você chegou no fragment dúvidas. Titulo: Dúvida Pix. (<) Quando clicar no voltar, voltar para a tela anterior.(X)
// Trocar a toolbar para <com.google.android.material.appbar.AppBarLayout e dentro dele <com.google.android.material.appbar.MaterialToolbar(X)
// Scroll view direto embaixo do Toolbar(X)
class PixFragment : Fragment() {

    private var _binding: FragmentPixBinding? = null
    private val binding get() = _binding!!

    private var numero : String? = "R\$ 0,00"
    // Legal no onCreateView() é ter apenas uma função, que é inflar o layout (O que na verdade nem vamos mais fazer pq vai estar no BaseFragment)

    // Agora para adicionar comportamentos, o legal é utilizar o onViewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mlocal = Locale("pt", "BR")
        binding.editText.addTextChangedListener(MaskMoney(binding.editText, mlocal))

        binding.apply {

            setupToolbar()

            setupNextButton()

            setupToqueButton()
        }
    }

    private fun FragmentPixBinding.setupToolbar() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.back_button -> {
                    this@PixFragment.requireActivity().finishAffinity()
                    true
                }
                R.id.info_button -> {
                    setupInfoButton()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupInfoButton() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Dúvida?")
        builder.setMessage("Tem alguma dúvida?")
        builder.setPositiveButton("Sim") { _, _ ->
            navTo(PixFragmentDirections.actionPixFragmentToInfoFragment())
        }
        builder.setNegativeButton("Não") { _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun FragmentPixBinding.setupToqueButton() {
        txtvToqueVizualizar.setOnClickListener {
            binding.txtvToqueVizualizar.visibility = View.GONE
            binding.txtValor.visibility = View.VISIBLE
        }

        txtValor.setOnClickListener {
            binding.txtvToqueVizualizar.visibility = View.VISIBLE
            binding.txtValor.visibility = View.GONE
        }
    }

    private fun FragmentPixBinding.setupNextButton() {
        nextButton.setOnClickListener {
            val snackbar = Snackbar.make(
                it,
                numero.toString(),
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }
    }

    inner class MaskMoney(editText: EditText?, locale: Locale?) : TextWatcher {
        private val editTextReferenceWeak: WeakReference<EditText>
        private val locale: Locale

        init {
            editTextReferenceWeak = WeakReference<EditText>(editText)
            this.locale = locale ?: Locale.getDefault()
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable) {

            val editText: EditText = editTextReferenceWeak.get() ?: return
            editText.removeTextChangedListener(this)
            val parsed: BigDecimal = parseToBigDecimal(editable.toString(), locale)
            val formatted: String = NumberFormat.getCurrencyInstance(locale).format(parsed)
            editText.setText(formatted)
            numero = formatted

            editText.setSelection(formatted.length)
            editText.addTextChangedListener(this)
        }

        private fun parseToBigDecimal(value: String, locale: Locale): BigDecimal {

            val replaceable = java.lang.String.format(
                "[%s,.\\s]",
                NumberFormat.getCurrencyInstance(locale).currency?.symbol
            )
            val cleanString = value.replace(replaceable.toRegex(), "")
            return BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR
            ).divide(
                BigDecimal(100), BigDecimal.ROUND_FLOOR
            )
        }
    }
}