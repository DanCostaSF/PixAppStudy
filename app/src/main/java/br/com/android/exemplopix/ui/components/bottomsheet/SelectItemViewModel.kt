package br.com.android.exemplopix.ui.components.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BottomSheetBaseViewModel : ViewModel() {

    val closeDialog = MutableLiveData(false)

    fun onCloseDialog() {
        closeDialog.postValue(true)
    }

    fun doneCloseDialog() {
        closeDialog.postValue(false)
    }
}

class SelectItemViewModel : BottomSheetBaseViewModel()