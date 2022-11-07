package br.com.android.exemplopix.ui.pixmanual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.android.exemplopix.commons.isCnpjValid
import br.com.android.exemplopix.commons.isCpfValid

class PixManualViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)
    val buttonEnabled = MutableLiveData(false)

    val editTextCpf = MutableLiveData("")

    val validCpf = editTextCpf.map {
        when {
            isCpfValid(it) -> true
            isCnpjValid(it) -> true
            else -> false
        }
    }

    val titularity = MutableLiveData<String>()

    fun setTitularity(type: String) {
        titularity.postValue(type)
    }

    val financeiro = MutableLiveData<String>()

    fun setFincanceiro(type: String) {
        financeiro.postValue(type)
    }

    val typeAccount = MutableLiveData<String>()

    fun setTypeAccount(type: String) {
        typeAccount.postValue(type)
    }

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    fun buttonOn() {
        buttonEnabled.postValue(true)
    }

    fun buttonOff() {
        buttonEnabled.postValue(false)
    }
}