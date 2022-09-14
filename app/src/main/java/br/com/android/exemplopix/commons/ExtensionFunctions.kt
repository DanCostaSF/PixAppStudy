package br.com.android.exemplopix.commons

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navBack() = findNavController().navigateUp()
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)

