package br.com.android.exemplopix.commons

fun setCelularFormatado(celular: String): String {
    val cellFormated = celular.substring(0, 0) + "(" +
            celular.substring(0, 2) + ")" +
            celular.substring(2, 7) + "-" +
            celular.substring(7, 11)

    return "TIPO: CELULAR - VALOR: $cellFormated"
}

fun setCpfFormatado(cpf: String): String {
    val cpfFormated = cpf.substring(0, 3) + "." +
            cpf.substring(3, 6) + "." +
            cpf.substring(6, 9) + "-" +
            cpf.substring(9, 11)
    return "TIPO: CPF - VALOR: $cpfFormated"
}

fun setCnpjFormatado(cnpj: String): String {
    val cnpjFormated = cnpj.substring(0, 2) + "." +
            cnpj.substring(2, 5) + "." +
            cnpj.substring(5, 8) + "/" +
            cnpj.substring(8, 12) + "-" +
            cnpj.substring(12, 14)
    return "TIPO: CNPJ - VALOR: $cnpjFormated"
}