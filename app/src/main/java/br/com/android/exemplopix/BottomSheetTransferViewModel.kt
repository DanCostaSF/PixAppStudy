package br.com.android.exemplopix

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomSheetTransferViewModel : ViewModel() {

    val closeButton = MutableLiveData(false)

    fun onMenuItemClick(item: MenuItem) : Boolean {
        if (item.itemId == R.id.close_button) {
            closeButton.postValue(true)
        }
        return false
    }

    val contacts = MutableLiveData(
        listOf(
            ContactsPixModel(
                "Alice Medeiros da Silva",
                1
            ),
            ContactsPixModel(
                "Caroline Vieira Cardozo",
                2
            ),
            ContactsPixModel(
                "Eduardo Costa da Costa",
                3
            ),
            ContactsPixModel(
                "Felipe Lima Moreira",
                4
            ),
            ContactsPixModel(
                "Zeca Urubu",
                5
            ),
            ContactsPixModel(
                "Paulo Plinio",
                6
            ),
            ContactsPixModel(
                "Jose Rico",
                7
            ),
            ContactsPixModel(
                "Milionario",
                8
            )
        )
    )
}