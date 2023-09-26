package com.becker.hw_2_custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnSliderTouchListener

class CombinedCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val imagesLinks = listOf(
        "https://m.media-amazon.com/images/M/MV5BN2M5YWFjN2YtYzU2YS00NzBlLTgwZWUtYWQzNWFhNDkyYjg3XkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg",
        "https://assets-prd.ignimgs.com/2020/09/16/mandalorian-button-1600277980032.jpg",
        "https://m.media-amazon.com/images/M/MV5BMTEzNDc3MDQ2NzNeQTJeQWpwZ15BbWU4MDYzMzUwMDIx._V1_FMjpg_UX1000_.jpg",
        "https://prd-rteditorial.s3.us-west-2.amazonaws.com/wp-content/uploads/2023/05/25100858/700Return.jpg",
        "https://flxt.tmsimg.com/assets/p17848831_b_h8_ac.jpg"
    )

    private val rainbowDrumView: RainbowDrumView
    private val sizeSlider: Slider
    private val textView: TextView
    private val imageView: ImageView
    private val resetButton: Button

    init {
        val root = inflate(context, R.layout.view_combined, this)
        rainbowDrumView = root.findViewById(R.id.rainbowDrumView)
        sizeSlider = root.findViewById(R.id.sizeSld)
        textView = root.findViewById(R.id.textView)
        imageView = root.findViewById(R.id.imageView)
        resetButton = root.findViewById(R.id.resetBtn)

        sizeSlider.addOnSliderTouchListener(object : OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                rainbowDrumView.setCustomSize(slider.value.toInt())
            }
        })

        rainbowDrumView.setCustomViewListener(object : CustomViewListener {
            override fun onDataReceived(data: String) {
                when (data) {
                    "red color",
                    "yellow color",
                    "blue color",
                    "light blue color" -> {
                        imageView.isVisible = false
                        textView.isVisible = true
                        textView.text = data
                    }

                    "image 1",
                    "image 2",
                    "image 3" -> {
                        textView.isVisible = false
                        imageView.isVisible = true
                        imageView.load(imagesLinks.random())
                    }
                }
            }
        })

        resetButton.setOnClickListener {
            textView.text = ""
            imageView.load("")
            rainbowDrumView.setCustomSize(50)
            sizeSlider.value = 50F
        }
    }
}