package br.com.android.exemplopix.botsheetsantigas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BotSheetTitularidadeViewModel : ViewModel() {

    val closeButton = MutableLiveData(false)

    val checkYes = MutableLiveData(false)
    val checkNo = MutableLiveData(false)

    fun closeDialog() {
        closeButton.postValue(true)
    }

    fun setupClickYes() {
        checkYes.postValue(true)
        checkNo.postValue(false)
    }

    fun setupClickNo() {
        checkYes.postValue(false)
        checkNo.postValue(true)
    }
}