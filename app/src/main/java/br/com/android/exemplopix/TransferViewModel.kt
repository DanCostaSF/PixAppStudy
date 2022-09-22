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
                    true
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