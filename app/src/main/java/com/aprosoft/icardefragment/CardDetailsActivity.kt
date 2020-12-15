package com.aprosoft.icardefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_card_details.*
import org.json.JSONArray
import org.json.JSONObject

class CardDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)

        val cardString= intent.getStringExtra("cardObject")

        val obj = JSONObject(cardString)
        tv_bName.text = obj.getString("businessName")
        tv_userName.text = obj.getString("name")
        tv_userDesignation.text = obj.getString("designation")
        tv_userEmail.text = obj.getString("email")
        tv_userPhone.text = obj.getString("phone")
        tv_userLandline.text = obj.getString("landline")
        tv_useraddress.text = obj.getString("address")

        iv_backArrow.setOnClickListener {
            this.finish()
        }






    }
}