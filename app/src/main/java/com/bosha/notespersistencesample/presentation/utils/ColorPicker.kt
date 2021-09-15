package com.bosha.notespersistencesample.presentation.utils

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.forEach
import com.bosha.notespersistencesample.R



class ColorPicker() {

    /**
     *  view's id and color's id
     */
    private val mMap = mapOf(
        R.id.color1 to R.color.color1,
        R.id.color2 to R.color.color2,
        R.id.color3 to R.color.color3,
        R.id.color4 to R.color.color4,
        R.id.color5 to R.color.color5,
        R.id.color6 to R.color.color6,
        R.id.color7 to R.color.color7,
        R.id.color8 to R.color.color8,
        R.id.color9 to R.color.color9,
        R.id.color10 to R.color.color10,
        R.id.color11 to R.color.color11,
        R.id.color12 to R.color.color12,
        R.id.color13 to R.color.color13,
        R.id.color14 to R.color.color14,
        R.id.color15 to R.color.color15,
        R.id.color16 to R.color.color16,
    )

    fun getPickedColor(checkedView: View) =  mMap[checkedView.id]

    companion object{
        fun LinearLayout.doOnColorClick(action: (View) -> Unit){
            forEach {
                it.setOnClickListener { view ->
                    action(view)
                }
            }
        }
    }
}