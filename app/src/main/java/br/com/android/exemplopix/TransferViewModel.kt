package br.com.android.exemplopix

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.android.exemplopix.commons.*
import java.util.regex.Pattern

// Separar em novas private funs a validação de cpf, cnpj, isValidEmail . Removendo try catch e trabalhabndo com digitsOnly.
// A função de validaçõa de CPF, CNPJ e EMAIL colocar em uma outra classe. O enum tbm. As funções de formatação tbm, pode por tudo no mesmo arquivo.
// Criar um novo tipo que é celular. Criar validação se o celular é valido. E criar formatação tbm.


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
        return return false
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

        return "TIPO: CNPJ - VALOR: $cellFormated"
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


