package br.com.android.exemplopix.commons

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.navBack() = findNavController().navigateUp()
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)

fun Fragment.showAlertDialog(
    title: String? = null,
    message: String? = null,
    positiveButtonLabel: String? = null,
    positiveButtonClickListener: () -> Unit = {},
    negativeButtonLabel: String? = null,
    negativeButtonClickListener: () -> Unit = {},
    cancelable: Boolean = true
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonLabel) { dialog, _ -> dialog.dismiss(); positiveButtonClickListener() }
        .setNegativeButton(negativeButtonLabel) { dialog, _ -> dialog.dismiss(); negativeButtonClickListener() }
        .setCancelable(cancelable)
        .create().also { it.show() }
}
