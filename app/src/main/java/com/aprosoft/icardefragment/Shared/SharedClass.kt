package com.aprosoft.icardefragment.Shared

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

class SharedClass {

    val prefName = "iCardePref"
    val userPref = "UserPref"

    fun setSharedPreference(context: Context, jsonObject: JSONObject){
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(userPref,jsonObject.toString()).apply()
    }

    fun getUserFromSharedPreference(context: Context):JSONObject?{
        val sharedPreferences = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
        return if (sharedPreferences.contains(userPref)){
            val userString=sharedPreferences.getString(userPref,"")
            if (userString.equals("",true)){
                null
            }else{
                JSONObject(userString)
            }
        }else{
            null
        }
    }
}