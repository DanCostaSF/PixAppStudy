package br.com.android.exemplopix.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.android.exemplopix.R
import br.com.android.exemplopix.commons.isVisibility
import br.com.android.exemplopix.databinding.PixmanualContentViewBinding


class PixManualContentView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle) {

    var txv_tittle: Boolean? = null
        set(value) {
            binding.txvTittle.isVisibility(value)
            field = value
        }

    var txv_subtittle: Boolean? = null
        set(value) {
            binding.txvSubtittle.isVisibility(value)
            field = value
        }

    var imageView: Boolean? = null
        set(value) {
            binding.imageView.isVisibility(value)
            field = value
        }

    private val binding =
        PixmanualContentViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setupAttr()
    }

    private fun setupAttr() {
        val style = context.obtainStyledAttributes(
            attrs, R.styleable.PixManualContentView, defStyle, 0
        )
        updateParamValue(style)
    }

    private fun updateParamValue(style: TypedArray) {
        txv_tittle = style.getBoolean(R.styleable.PixManualContentView_txv_tittle, true)
        txv_subtittle = style.getBoolean(R.styleable.PixManualContentView_txv_subtittle, true)
        imageView = style.getBoolean(R.styleable.PixManualContentView_imageview, true)
    }
}