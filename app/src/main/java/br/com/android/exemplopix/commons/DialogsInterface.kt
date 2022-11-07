package br.com.android.exemplopix.commons


interface DialogsInterface {
    fun titularity(text: String)
    fun instFinanceira(text: TypeBank)
    fun typeAccount(text: String)
}

enum class TypeBank(val text: String) {
    NUBANK("260 - Nubank"),
    SICREDI("270 - Sicredi"),
    ITAU("300 - Itaú")
}

var type = ""