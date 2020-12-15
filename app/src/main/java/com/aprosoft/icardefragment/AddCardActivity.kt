package com.aprosoft.icardefragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.aprosoft.icardefragment.Retrofit.ApiClient
import com.aprosoft.icardefragment.Shared.SharedClass
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_add_card.avi
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val vibrate: Vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val userObject = SharedClass().getUserFromSharedPreference(this)
        val uuid = userObject?.getString("Uuid")


        iv_backArrow.setOnClickListener {
            this.finish()
        }

        btn_addCard.setOnClickListener {

            when {
                et_Bname.text.toString()=="" -> {
                    et_Bname.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_Bname.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)

                }
                et_personName.text.toString() =="" -> {
                    et_personName.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_personName.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_personEmail.text.toString()=="" -> {
                    et_personEmail.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_personEmail.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_personPhone.text.toString() == "" -> {
                    et_personPhone.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_personPhone.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_PhoneLandline.text.toString() =="" -> {
                    et_PhoneLandline.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_PhoneLandline.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_personAddress.text.toString() == "" -> {
                    et_personAddress.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_personAddress.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_personDesignation.text.toString()== "" -> {
                    et_personDesignation.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_personDesignation.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                else -> {
                    addCardDetails(uuid)
                }
            }
        }
    }

    private fun shakeError(): TranslateAnimation? {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7F)

        return shake
    }


    private fun addCardDetails(uuid: String?) {

        avi.visibility = View.VISIBLE
        val addCardParams = HashMap<String,String>()
        addCardParams["userId"] = uuid.toString()
        addCardParams["businessName"] =  et_Bname.text.toString()
        addCardParams["name"] =et_personName.text.toString()
        addCardParams["email"] =et_personEmail.text.toString()
        addCardParams["phone"] =  et_personPhone.text.toString()
        addCardParams["landline"] =et_PhoneLandline.text.toString()
        addCardParams["address"] = et_personAddress.text.toString()
        addCardParams["designation"] = et_personDesignation.text.toString()

        val call:Call<ResponseBody> = ApiClient.getClient.createCard(addCardParams)
        call.enqueue(object:Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                avi.visibility = View.GONE
                val res = response.body()?.string()
                val jsonArray = JSONArray(res)
                val jsonObject = jsonArray.getJSONObject(0)
                val success =jsonObject.getBoolean("success")
                val msg = jsonObject.getString("msg")
                if (success){
                    Toast.makeText(applicationContext,msg, Toast.LENGTH_LONG).show()
                    intent= Intent(this@AddCardActivity,MainActivity::class.java)
                    startActivity(intent)
                    this@AddCardActivity.finish()

                }else{
                    avi.visibility = View.GONE
                    Toast.makeText(applicationContext,msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                avi.visibility = View.GONE
                Log.d("error", "$t")
            }

        })








    }
}