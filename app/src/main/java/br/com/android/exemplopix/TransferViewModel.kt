package br.com.android.exemplopix

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import java.util.regex.Pattern

class TransferViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)
    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    val editText = MutableLiveData("")

    val snackBarString = MutableLiveData("")

    val isEnabled: LiveData<Boolean> = editText.map {
        when (it.length) {
            11 -> {
                try {
                    it.toDouble()
                    val cpf = it.substring(0, 3) + "." +
                            it.substring(3, 6) + "." +
                            it.substring(6, 9) + "-" +
                            it.substring(9, 11)
                    snackBarString.value = "TIPO: CPF - VALOR: $cpf"
                    isCPF(it)
                } catch (e: NumberFormatException) {
                    snackBarString.value = "TIPO: EMAIL - VALOR:$it"
                    emailVerify(it)
                }
            }
            14 -> {
                try {
                    it.toDouble()
                    val cnpj = it.substring(0, 2) + "." +
                            it.substring(2, 5) + "." +
                            it.substring(5, 8) + "/" +
                            it.substring(8, 12) + "-" +
                            it.substring(12, 14)
                    snackBarString.value = "TIPO: CNPJ - VALOR: $cnpj"
                    true
                } catch (e: NumberFormatException) {
                    snackBarString.value = "TIPO: EMAIL - VALOR:$it"
                    emailVerify(it)
                }
            }
            else -> {
                snackBarString.value = "TIPO: EMAIL - VALOR:$it"
                emailVerify(it)
            }
        }
    }

    private fun isCPF(document: String): Boolean {
        if (document.isEmpty()) return false

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

    private fun emailVerify(email: String) : Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    val contacts = MutableLiveData(
        listOf(
            ContactsPixModel(
                "Alice Medeiros da Silva",
                1
            ),
            ContactsPixModel(
                "Caroline Vieira Cardozo",
                2
            ),
            ContactsPixModel(
                "Eduardo Costa da Costa",
                3
            ),
            ContactsPixModel(
                "Felipe Lima Moreira",
                4
            )
        )
    )
}