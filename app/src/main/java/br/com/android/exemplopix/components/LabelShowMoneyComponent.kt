package br.com.android.exemplopix.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.android.exemplopix.R
import br.com.android.exemplopix.databinding.LabelShowMoneyBinding


@SuppressLint("SetTextI18n")
class LabelShowMoneyComponent @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = LabelShowMoneyBinding.inflate(LayoutInflater.from(context), this, true)

    var money: Float? = null
        set(value) {
            binding.txtValor.text = "R$ $value"
            field = value
        }

    init {
        setupAttr()
    }

    @SuppressLint("SetTextI18n")
    private fun setupAttr() {
        val style = context.obtainStyledAttributes(
            attrs, R.styleable.LabelShowMoneyComponent, defStyle, 0
        )
        updateParamValue(style)
        onContainerClick()
    }

    private fun updateParamValue(style: TypedArray) {
        money = style.getFloat(R.styleable.LabelShowMoneyComponent_money, 0f)
    }

    private fun onContainerClick() {
        binding.root.setOnClickListener {
            logicVisibility()
        }
    }

    private fun logicVisibility() {
        if (binding.txtValor.visibility == GONE) {
            binding.txtValor.visibility = VISIBLE
            binding.txtvToqueVizualizar.visibility = INVISIBLE
        } else {
            binding.txtValor.visibility = GONE
            binding.txtvToqueVizualizar.visibility = VISIBLE
        }
    }
}