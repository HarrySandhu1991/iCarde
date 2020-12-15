package com.aprosoft.icardefragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.aprosoft.icardefragment.Retrofit.ApiClient
import com.aprosoft.icardefragment.Shared.SharedClass
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        val iv_backarrow = v.findViewById<ImageView>(R.id.iv_backArrowButton)

        iv_backarrow.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        // Inflate the layout for this fragment
        profile()
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private  fun profile(){

//        avi.visibility = View.VISIBLE
        val userObject = SharedClass().getUserFromSharedPreference(context!!)
        val uuid = userObject?.getString("Uuid")
        Toast.makeText(context,uuid,Toast.LENGTH_LONG).show()

        val profileParams = HashMap<String,String>()
        profileParams["Uuid"] = uuid.toString()

        val call:Call<ResponseBody> = ApiClient.getClient.profile(profileParams)
        call.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//               avi.visibility = View.GONE
                val res = response.body()?.string()
                val jsonArray = JSONArray(res)
                val jsonObject= jsonArray.getJSONObject(0)
                val success = jsonObject.getBoolean("Success")
                if (success){
                    tv_userName.text = jsonObject.getString("Name")
                    tv_userPhone.text= jsonObject.getString("Phone")
                    tv_userEmail.text = jsonObject.getString("Email")
//                    tv_designation.text = jsonObject.getString("")
//                    Toast.makeText(context,name, Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                avi.visibility = View.GONE
                Log.d("error","$t")
            }

        })

    }
}