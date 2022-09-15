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

// Se o valor estiver vazio, desabilitar o botão Próximo, portanto tu pode remover o teste de o valor estar vazio no show toast.( )
// Replicar botão do navigation E quando clicar em voltar, perguntar em um novo dialog se usuário quer voltar mesmo. Se clicar em não, continua na tela de dúvidas. Se clicar em sim, fechar a tela atual.
// Colocar MaskMoney em outro package common
// O valor/TOque para visualizar seja alterado de acordo com o container
// Controlar quando clicar no dialog para não permitir abrir duas vezes o dialog
// Colocar click fora para fechar todos os dialogs.
// Criar um parametro para passar para o fragment de dúvidas. Parametro: List<String>?. Quando tu chegar na tela de dúvidas, formatar as Strings em uma String só no seguinte formato:
// Aleluia, Aiaiai, Danilo é brabo. Se passar apenas uma, tem que ter apenas o ponto. Ex: Danilo. Se passar uma lista sem items ou o parametro nulo, exibir mensagem default: Nenhum parametro para listar.
// Na primeira tela, os campos não podem se sobrepor.
// Na segunda tela, a frase "Voce chegou..." precisa estar centralizada.
// Colocar um valor máximo de 99.999,99
//
class PixFragment : Fragment() {

    private var _binding: FragmentPixBinding? = null
    private val binding get() = _binding!!

    private var numero: String? = "R\$ 0,00"
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
            setupNavigationListener()
            setupNextButton()
            setupToqueButton()
        }
    }

    private fun FragmentPixBinding.setupNavigationListener() {
        toolbar.setNavigationOnClickListener {
            requireActivity().finishAffinity()
        }
    }

    private fun FragmentPixBinding.setupToolbar() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.info_button) {
                setupInfoButton()
            }
            true
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

        nextButton.isEnabled = false
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
            binding.nextButton.isEnabled = parsed > BigDecimal(0)
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