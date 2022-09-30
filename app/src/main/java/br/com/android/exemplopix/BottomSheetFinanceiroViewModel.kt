package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomSheetFinanceiroViewModel : ViewModel() {

    val closeButton = MutableLiveData(false)

    val checkNubank = MutableLiveData(false)
    val checkItau = MutableLiveData(false)
    val checkSicredi = MutableLiveData(false)
    val teste = MutableLiveData(type)

    fun closeDialog() {
        closeButton.postValue(true)
    }

    fun setupNubankClick() {
        checkNubank.postValue(true)
        checkItau.postValue(false)
        checkSicredi.postValue(false)
    }

    fun setupItauClick() {
        checkNubank.postValue(false)
        checkItau.postValue(true)
        checkSicredi.postValue(false)
    }

    fun trueNubank() {
        checkNubank.postValue(true)
    }

    fun trueItau() {
        checkItau.postValue(true)
    }

    fun falseAll() {
        checkItau.postValue(false)
        checkSicredi.postValue(false)
        checkNubank.postValue(false)
    }

    fun trueSicredi() {
        checkSicredi.postValue(true)
    }

    fun setupSicrediClick() {
        checkNubank.postValue(false)
        checkItau.postValue(false)
        checkSicredi.postValue(true)
    }
}