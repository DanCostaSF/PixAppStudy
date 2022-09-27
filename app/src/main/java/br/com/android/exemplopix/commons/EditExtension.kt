package br.com.android.exemplopix.commons

import com.google.android.material.textfield.TextInputEditText

fun List<TextInputEditText>.validateField() : Boolean {
    this.forEach {
        if (it.text.isNullOrEmpty() || it.text.toString() == "Nenhum selecionado") {
            return false
        }
    }
    return true
}