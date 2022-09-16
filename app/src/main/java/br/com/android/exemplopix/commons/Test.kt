package br.com.android.exemplopix.commons

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("isVisible")
fun View.isVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}
