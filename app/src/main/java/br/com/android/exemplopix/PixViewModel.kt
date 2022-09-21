package br.com.android.exemplopix


import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PixViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)
    var snackBar = MutableLiveData(false)
    var isButtonEnabled = MutableLiveData(false)
    val onTextView = MutableLiveData(true)
    val onTextView2 = MutableLiveData(false)
    val alertDialog = MutableLiveData(false)

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    fun onButtonDisable(value: String) {
        if (value != "0.00") {
            isButtonEnabled.postValue(true)
        } else {
            isButtonEnabled.postValue(false)
        }
    }

    fun onTextViewVisibility() {
        if(onTextView.value == false) {
            onTextView.postValue(true)
        } else {
            onTextView.postValue(false)
        }

        if(onTextView2.value == false) {
            onTextView2.postValue(true)
        } else {
            onTextView2.postValue(false)
        }
    }

    fun onMenuItemClick(item: MenuItem) : Boolean {
        if (item.itemId == R.id.info_button) {
            alertDialog.postValue(true)
        }
        return false
    }

    fun doneInfoButtonClick() {
        alertDialog.postValue(false)
    }

    fun showSnackBar() {
        snackBar.postValue(true)
    }

    fun doneSnackBar() {
        snackBar.postValue(false)
    }
}