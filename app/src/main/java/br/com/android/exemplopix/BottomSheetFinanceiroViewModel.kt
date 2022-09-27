package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomSheetFinanceiroViewModel : ViewModel() {

    val closeButton = MutableLiveData(false)

    val checkNubank = MutableLiveData(false)
    val checkItau = MutableLiveData(false)
    val checkSicredi = MutableLiveData(false)

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

    fun setupSicrediClick() {
        checkNubank.postValue(false)
        checkItau.postValue(false)
        checkSicredi.postValue(true)
    }
}