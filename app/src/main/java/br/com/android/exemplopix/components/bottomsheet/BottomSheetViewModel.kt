package br.com.android.exemplopix.components.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomSheetViewModel : ViewModel() {

    val closeDialog = MutableLiveData(false)

    fun onCloseDialog() {
        closeDialog.postValue(true)
    }

    fun doneCloseDialog() {
        closeDialog.postValue(false)
    }

}