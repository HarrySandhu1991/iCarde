package com.aprosoft.icardefragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wajahatkarim3.easyflipview.EasyFlipView
import com.wajahatkarim3.easyflipview.EasyFlipView.OnFlipAnimationListener

class FlipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flip)


        val easyFlipView :EasyFlipView = findViewById(R.id.easyFlip)
        easyFlipView.onFlipListener =
            OnFlipAnimationListener { flipView, newCurrentSide ->
                // ...
                // Your code goes here
                // ...
            }
    }
}