package br.com.android.exemplopix.botsheetsantigas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomSheetTypeCViewModel : ViewModel() {

    val closeButton = MutableLiveData(false)

    val checkCorrente = MutableLiveData(false)
    val checkPoupanca = MutableLiveData(false)
    val checkSalario = MutableLiveData(false)

    fun closeDialog() {
        closeButton.postValue(true)
    }

    fun setupCorrenteClick() {
        checkCorrente.postValue(true)
        checkPoupanca.postValue(false)
        checkSalario.postValue(false)
    }

    fun setupPoupancaClick() {
        checkCorrente.postValue(false)
        checkPoupanca.postValue(true)
        checkSalario.postValue(false)
    }

    fun setupSalarioClick() {
        checkCorrente.postValue(false)
        checkPoupanca.postValue(false)
        checkSalario.postValue(true)
    }
}