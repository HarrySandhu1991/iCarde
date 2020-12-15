package com.aprosoft.icardefragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.aprosoft.icardefragment.Login.LoginActivity
import com.aprosoft.icardefragment.Shared.SharedClass
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userObject = SharedClass().getUserFromSharedPreference(this)
        val myCard = userObject?.getBoolean("MyCard")

//        Toast.makeText(applicationContext,"$myCard",Toast.LENGTH_LONG).show()

        if (myCard==false){
//            Toast.makeText(applicationContext,"$myCard",Toast.LENGTH_LONG).show()
//            addCardAlert()
            intent = Intent(this,AddCardActivity::class.java)
            startActivity(intent)
        }else{
            val fragmentManager:FragmentManager = supportFragmentManager
            val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()
            val homeFragment = HomeFragment()
            fragmentTransaction.add(R.id.frame_main,homeFragment)
            fragmentTransaction.commit()
        }
        btn_fab.setOnClickListener {
            intent = Intent(this,AddCardActivity::class.java)
            startActivity(intent)
        }

        BottomNavClick(this, bottomNav)

    }
    private fun BottomNavClick(context: Context, bottomNav: BottomNavigationView){
        bottomNav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.menu_home ->{
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    true
                }

                R.id.menu_myCard->{
//                    val intent = Intent(context, FlipActivity::class.java)
//                    context.startActivity(intent)
                    true
                }
                R.id.menu_notification ->{
//                    val intent = Intent (context, NotificationActivity::class.java)
//                    context.startActivity(intent)
                    true
                }
                R.id.menu_profile ->{
//                    val intent = Intent(context, MoreActivity::class.java)
//                    context.startActivity(intent)

                    val fragmentManager:FragmentManager = supportFragmentManager
                    val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()
                    val profileFragment = ProfileFragment()
                    fragmentTransaction.replace(R.id.frame_main,profileFragment)
                    fragmentTransaction.commit()


                    true
                }
                R.id.menu_logout ->{
                 logoutAlert()
                    true
                }
                else-> false
            }
        }
    }

//    private fun addCardAlert() {
//        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
//        alertDialog.setTitle("You don't have Business card")
//        alertDialog.setMessage("Create Your Card")
////        alertDialog.setIcon(R.drawable.ic_exit)
//        alertDialog.setPositiveButton("Continue"){ _, _ ->
//
//            intent  = Intent(this,AddCardActivity::class.java)
//            startActivity(intent)
//            this.finish()
//
//        }
//        alertDialog.setNegativeButton("Skip"){ _, _ ->
//            val fragmentManager:FragmentManager = supportFragmentManager
//            val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()
//            val homeFragment = HomeFragment()
//            fragmentTransaction.add(R.id.frame_main,homeFragment)
//            fragmentTransaction.commit()
//        }
//
//        alertDialog.create()
//        alertDialog.show()
//    }




    private fun logoutAlert() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Are you sure ?")
        alertDialog.setMessage("you want Logout")
        alertDialog.setIcon(R.drawable.ic_exit)
        alertDialog.setPositiveButton("Yes"){ _, _ ->

            val preferences =
                getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            finish()

            val spreferences =
                getSharedPreferences("iCardePref", Context.MODE_PRIVATE)
            val editor1 = spreferences.edit()
            editor1.clear()
            editor1.apply()
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("finish", true) // if you are checking for this in your other Activities
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        alertDialog.setNegativeButton("No"){ _, _ ->

        }

        alertDialog.create()
        alertDialog.show()
    }

}