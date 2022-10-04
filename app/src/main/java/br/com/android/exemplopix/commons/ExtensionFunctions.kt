package br.com.android.exemplopix.commons

import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

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

fun View.showSnackBar(sla: String) {
    Snackbar.make(
        this,
        sla,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.observeAndNavigateBack(livedata: LiveData<Boolean>) {
    livedata.observe(viewLifecycleOwner) {
        if (it == true) navBack()
    }
}

@BindingAdapter("isVisibleOrGone")
fun View.isVisibleOrGone(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("isVisibility")
fun View.isVisibility(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.INVISIBLE
}

