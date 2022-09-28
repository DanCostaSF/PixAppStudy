package br.com.android.exemplopix.commons

import androidx.core.text.isDigitsOnly
import androidx.core.util.PatternsCompat

fun isCelularValid(document: String): Boolean {
    if (!document.isDigitsOnly()) return false
    val regex = Regex("""^\d{2}[7-9][7-9]\d{7}${'$'}""")
    return regex.containsMatchIn(document)
}

fun isEmailValid(document: String): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(document).matches()
}

fun isCnpjValid(document: String): Boolean {

    if (document.isEmpty()) return false

    if (!document.isDigitsOnly()) return false

    val numbers = document.filter { it.isDigit() }.map {
        it.toString().toInt()
    }

    if (numbers.size != 14) return false

    if (numbers.all { it == numbers[0] }) return false

    return true
}

fun isCpfValid(document: String): Boolean {

    if (document.isEmpty()) return false

    if (!document.isDigitsOnly()) return false

    val numbers = document.filter { it.isDigit() }.map {
        it.toString().toInt()
    }

    if (numbers.size != 11) return false

    if (numbers.all { it == numbers[0] }) return false

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }

    return numbers[9] == dv1 && numbers[10] == dv2
}

enum class ValidType {
    CPF,
    CNPJ,
    EMAIL,
    CELULAR,
    NON_VALID
}