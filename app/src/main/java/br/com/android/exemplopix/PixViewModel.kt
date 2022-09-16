package br.com.android.exemplopix


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PixViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)
    var onTextView = MutableLiveData(true)
    var snackBar = MutableLiveData(false)
    var buttonDisable = MutableLiveData(false)

    fun onNavigationClick(){
        onNavigateBack.value = true
    }

    fun onTextViewVisibility() {
        if (onTextView.value == false) {
            onTextView.postValue(true)
        }else {
            onTextView.postValue(false)
        }
    }

    fun onButtonDisable(value: String) {
        if (value != "0.00") {
            buttonDisable.postValue(true)
        } else {
            buttonDisable.postValue(false)
        }
    }

    fun showSnackBar() {
        snackBar.postValue(true)
    }

    fun doneSnackBar() {
        snackBar.postValue(false)
    }
}