package br.com.android.exemplopix

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.android.exemplopix.commons.*
import java.util.regex.Pattern

// Centralizar mensagem do click do botão.
// Passar validação do CELULAR pro outro arquivo
// Quebrar em dois arquivos as validações em um arquivo e formatações em outro aqui.
class TransferViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)
    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    val editText = MutableLiveData("")

    private val validType = editText.map {
        when {
            isCpfValid(it) -> ValidType.CPF
            isCnpjValid(it) -> ValidType.CNPJ
            isEmailValid(it) -> ValidType.EMAIL
            isCelularValid(it) -> ValidType.CELULAR
            else -> ValidType.NON_VALID
        }
    }

    fun isCelularValid(document: String): Boolean {
        if (!document.isDigitsOnly()) return false
        val regex = Regex("""^\d{2}[7-9][7-9]\d{7}${'$'}""")
        return regex.containsMatchIn(document)
    }

    val isEnabled = validType.map {
        it != ValidType.NON_VALID
    }

    val snackBarInsertString: LiveData<String> = validType.map {
        when (it) {
            ValidType.CPF -> setCpfFormatado(editText.value.toString())
            ValidType.CNPJ -> setCnpjFormatado(editText.value.toString())
            ValidType.EMAIL -> "TIPO: EMAIL - VALOR ${editText.value.toString()}"
            ValidType.CELULAR -> setCelularFormatado(editText.value.toString())
            else -> "" // null
        }
    }

    fun setCelularFormatado(celular: String): String {
        val cellFormated = celular.substring(0, 0) + "(" +
                celular.substring(0, 2) + ")" +
                celular.substring(2, 7) + "-" +
                celular.substring(7, 11)

        return "TIPO: CELULAR - VALOR: $cellFormated"
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


