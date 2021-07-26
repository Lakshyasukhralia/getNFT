package com.getnft.data.util

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette

fun setBackgroundFromPalette(drawable: Drawable?, view: View){

    drawable?.let {
        val bitmap = drawable.toBitmap()
        Palette.from(bitmap).generate { palette: Palette? ->
            palette?.let {
                val dominantColor = palette.getLightMutedColor(0x000000)
                if(dominantColor!=0)
                    view.setBackgroundColor(dominantColor)
            }
//            val vibrantLight = palette.getLightVibrantColor(0x000000)
//            val vibrantDark = palette.getDarkVibrantColor(0x000000)
//            val muted = palette.getMutedColor(0x000000)
//            val mutedLight = palette.getLightMutedColor(0x000000)
//            val mutedDark = palette.getDarkMutedColor(0x000000)
        }
    }

}