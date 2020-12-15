package com.aprosoft.icardefragment.Signup

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aprosoft.icardefragment.Login.LoginActivity
import com.aprosoft.icardefragment.R
import com.aprosoft.icardefragment.Retrofit.ApiClient
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.avi
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val vibrate:Vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        btn_Register.setOnClickListener {
            when {
                et_FullName.text.toString() =="" -> {
                    et_FullName.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_FullName.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_Email.text.toString() =="" -> {
                    et_Email.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_Email.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_Phone.text.toString() =="" -> {
                    et_Phone.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_Phone.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                et_Password.text.toString()=="" -> {
                    et_Password.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_Password.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                else -> {
                    signUp()
                }
            }
        }

        tv_goToLogin.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun shakeError(): TranslateAnimation? {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7F)

        return shake
    }

    private fun signUp(){

        avi.visibility = View.VISIBLE
        val signupParams = HashMap<String, String>()
        signupParams["Name"] = et_FullName.text.toString()
        signupParams["Email"] = et_Email.text.toString()
        signupParams["Phone"] = et_Phone.text.toString()
        signupParams["Password"] = et_Password.text.toString()
        signupParams["Playerid"] = "123456"
        signupParams["UserImage"] = "Image"

        val call:Call<ResponseBody> = ApiClient.getClient.signUp(signupParams)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                avi.visibility = View.GONE
                val res = response.body()?.string()
                val jsonArray = JSONArray(res)
                val jsonObject = jsonArray.getJSONObject(0)
                val success = jsonObject.getBoolean("Success")
                if (success) {
                    intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    this@SignupActivity.finish()
                }else{
                    Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_LONG).show()
                    avi.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("error", "$t")
                avi.visibility = View.GONE
            }
        })





    }
}