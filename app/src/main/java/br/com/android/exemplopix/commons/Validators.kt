package br.com.android.exemplopix.commons

import androidx.core.text.isDigitsOnly
import androidx.core.util.PatternsCompat

fun setCpfFormatado(cpf: String): String {
    val cpfFormated = cpf.substring(0, 3) + "." +
            cpf.substring(3, 6) + "." +
            cpf.substring(6, 9) + "-" +
            cpf.substring(9, 11)
    return "TIPO: CPF - VALOR: $cpfFormated"
}

fun setCnpjFormatado(cnpj: String): String {
    val cnpjFormated = cnpj.substring(0, 2) + "." +
            cnpj.substring(2, 5) + "." +
            cnpj.substring(5, 8) + "/" +
            cnpj.substring(8, 12) + "-" +
            cnpj.substring(12, 14)
    return "TIPO: CNPJ - VALOR: $cnpjFormated"
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