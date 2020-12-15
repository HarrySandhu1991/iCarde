package com.aprosoft.icardefragment.Login

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.aprosoft.icardefragment.MainActivity
import com.aprosoft.icardefragment.R
import com.aprosoft.icardefragment.Retrofit.ApiClient
import com.aprosoft.icardefragment.Shared.SharedClass
import com.aprosoft.icardefragment.Signup.SignupActivity
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.avi
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val vibrate: Vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


        if (SharedClass().getUserFromSharedPreference(this)== null){

        }else{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }


        tv_goToRegister.setOnClickListener {
            intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
            this.finish()
        }


        btn_login.setOnClickListener {

            when {
                et_UserEmail.text.toString()=="" -> {
                    et_UserEmail.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_UserEmail.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)

                }
                et_UserPassword.text.toString()=="" -> {
                    et_UserPassword.startAnimation(shakeError())
                    vibrate.vibrate(100)
                    et_UserPassword.background.setColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }
                else -> {
                    login()
                }
            }


        }
    }

    fun shakeError(): TranslateAnimation? {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7F)

        return shake
    }

    private fun login(){

//        avi.show()
        avi.visibility = View.VISIBLE
        val loginParams = HashMap<String,String>()
        loginParams["Email"] = et_UserEmail.text.toString()
        loginParams["Password"] = et_UserPassword.text.toString()
        loginParams["PlayerId"] ="123456"

        val call:Call<ResponseBody> = ApiClient.getClient.login(loginParams)
        call.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                avi.hide()
                avi.visibility = View.GONE
                val res= response.body()?.string()
                val jsonArray = JSONArray(res)
                val jsonObject = jsonArray.getJSONObject(0)
                val success = jsonObject.getBoolean("Success")
                if (success){
                    SharedClass().setSharedPreference(this@LoginActivity,jsonObject)
                    intent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                    this@LoginActivity.finish()
                }else{
//                    avi.hide()
                    avi.visibility = View.GONE
                    Toast.makeText(applicationContext,"Invalid Login",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("error","$t")
//                avi.hide()
                avi.visibility = View.GONE
            }

        })


    }


}