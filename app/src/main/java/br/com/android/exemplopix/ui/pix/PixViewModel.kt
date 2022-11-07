package br.com.android.exemplopix.ui.pix


import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.android.exemplopix.R

class PixViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)
    var snackBar = MutableLiveData(false)
    var isButtonEnabled = MutableLiveData(false)
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