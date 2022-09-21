package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class InfoViewModel : ViewModel() {

    val typedText = MutableLiveData("")

    val formattedText = typedText.map { typedText ->
        if (typedText.isNotEmpty()) "$typedText  - Texto complementar" else ""
    }

}