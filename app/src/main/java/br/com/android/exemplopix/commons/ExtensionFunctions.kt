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
//    @StringRes
    title: String? = null,
//    @StringRes
    message: String? = null,
//    @StringRes
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

@BindingAdapter("isVisible")
fun View.isVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
