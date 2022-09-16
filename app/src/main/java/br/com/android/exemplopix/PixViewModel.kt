package br.com.android.exemplopix


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PixViewModel : ViewModel() {

    val isVisible = MutableLiveData(true)

    val onNavigateBack = MutableLiveData(false)

    var snackBar = MutableLiveData(false)
    var isButtonEnabled = MutableLiveData(false)

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    fun onBalanceContainerClick() {
        isVisible.value = false
    }

    fun onButtonDisable(value: String) {
        if (value != "0.00") {
            isButtonEnabled.postValue(true)
        } else {
            isButtonEnabled.postValue(false)
        }
    }

    fun showSnackBar() {
        snackBar.postValue(true)
    }

    fun doneSnackBar() {
        snackBar.postValue(false)
    }
}