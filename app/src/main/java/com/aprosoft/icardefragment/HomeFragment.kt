package com.aprosoft.icardefragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aprosoft.icardefragment.Adapters.MyCardsAdapters
import com.aprosoft.icardefragment.Retrofit.ApiClient
import com.aprosoft.icardefragment.Shared.SharedClass
import com.wajahatkarim3.easyflipview.EasyFlipView
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var recyclerView: RecyclerView
var loader:AVLoadingIndicatorView? = null
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView =view.findViewById(R.id.rv_mycards)
        recyclerView.setHasFixedSize(true)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        //recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.itemAnimator = DefaultItemAnimator()
         loader= view.findViewById(R.id.avi)
        myCards()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun myCards(){
        loader?.visibility = View.VISIBLE
        val userObject = SharedClass().getUserFromSharedPreference(context!!)
        val uuid = userObject?.getString("Uuid")

        val myCardsParams = HashMap<String,String>()
        myCardsParams["userId"]= uuid.toString()

        val call:Call<ResponseBody> = ApiClient.getClient.myCards(myCardsParams)
        call.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                loader?.visibility = View.GONE
                val res = response.body()?.string()
                val jsonArray  = JSONArray(res)
                val success = jsonArray.getJSONObject(0).getBoolean("success")
                if (success){
                    val cardsAdapters = MyCardsAdapters(context!!,jsonArray)
                    recyclerView.adapter= cardsAdapters
                    Toast.makeText(context,"$success",Toast.LENGTH_LONG).show()
                }else{
                    loader?.visibility = View.GONE
                    Toast.makeText(context,"$success",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("error","$t")
                loader?.visibility = View.GONE
            }
        })
    }


    




}