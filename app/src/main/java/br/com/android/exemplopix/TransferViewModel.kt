package br.com.android.exemplopix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransferViewModel : ViewModel() {

    val onNavigateBack = MutableLiveData(false)

    val contacts = MutableLiveData<List<ContactsPixModel>>()

    fun onNavigationClick() {
        onNavigateBack.value = true
    }

    fun setData() {
        val alice = ContactsPixModel(
            "Alice Medeiros da Silva",
            1
        )
        val caroline = ContactsPixModel(
            "Caroline Vieira Cardozo",
            2
        )
        val eduardo = ContactsPixModel(
            "Eduardo Costa da Costa",
            3
        )
        val felipe = ContactsPixModel(
            "Felipe Lima Moreira",
            4
        )

        val persons = mutableListOf(alice, caroline, eduardo, felipe)
        contacts.postValue(persons)
    }

}