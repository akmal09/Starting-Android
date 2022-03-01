package com.example.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class MyButton : AppCompatButton {

    private var enabledBackGround: Drawable? = null
    private var disabledBackGround: Drawable? = null
    private var txtColor: Int = 0

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        background = when{
            isEnabled -> enabledBackGround
            else -> disabledBackGround
        }

        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = when{
            isEnabled -> "Submit"
            else -> "Isi Dulu"
        }
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackGround = ContextCompat.getDrawable(context, R.drawable.bg_button)
        disabledBackGround = ContextCompat.getDrawable(context, R.drawable.bg_button_disable)
    }
}