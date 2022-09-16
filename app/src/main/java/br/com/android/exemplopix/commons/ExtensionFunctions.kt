package br.com.android.exemplopix.commons

import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.navBack() = findNavController().navigateUp()
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)

fun Fragment.showAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int?,
    @StringRes positiveButtonLabel: Int,
    positiveButtonClickListener: () -> Unit = {},
    @StringRes negativeButtonLabel: Int,
    onDismiss: () -> Unit = {},
    cancelable: Boolean = true
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setPositiveButton(positiveButtonLabel) { dialog, _ -> dialog.dismiss(); positiveButtonClickListener() }
        .setNegativeButton(negativeButtonLabel, null)
        .setCancelable(cancelable)
        .setOnDismissListener { onDismiss() }
        .create().apply {
            message?.let { setMessage(getString(it)) }
            show()
        }
}
