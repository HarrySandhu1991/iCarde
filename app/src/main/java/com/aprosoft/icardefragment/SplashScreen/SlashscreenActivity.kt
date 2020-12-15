package com.aprosoft.icardefragment.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aprosoft.icardefragment.MainActivity
import com.aprosoft.icardefragment.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_slashscreen.*

class SlashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slashscreen)

        btn_getstarted.setOnClickListener {
            intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        Glide.with(this)
            .load(R.drawable.man_giving_visiting)
            .circleCrop()
            .into(iv_manGivingCard)

    }
}