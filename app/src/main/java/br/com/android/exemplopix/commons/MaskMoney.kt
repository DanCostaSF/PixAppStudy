package br.com.android.exemplopix.commons

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class MaskMoney(
    editText: EditText?,
    locale: Locale?,
    val onAfterTextChanged: (BigDecimal) -> Unit,
) : TextWatcher {
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
        val parse: BigDecimal = parseToBigDecimal(editable.toString(), locale)
        onAfterTextChanged(parse)
        parsed = parse
        val formatted: String = NumberFormat.getCurrencyInstance(locale).format(parse)
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

    companion object {
        var numero: String? = "R\$ 0,00"
        var parsed: BigDecimal = BigDecimal(0)
    }
}