package com.aprosoft.icardefragment.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aprosoft.icardefragment.CardDetailsActivity
import com.aprosoft.icardefragment.R
import com.wajahatkarim3.easyflipview.EasyFlipView
import org.json.JSONArray
import java.lang.String
import java.util.*


class MyCardsAdapters(context: Context,var jsonArray: JSONArray): RecyclerView.Adapter<MyCardsAdapters.MyVH>() {

    val context:Context? = context
    class MyVH(view: View) :RecyclerView.ViewHolder(view){
        private val backSide: RelativeLayout = view.findViewById(R.id.backLayout)
        private val frontSide: RelativeLayout = view.findViewById(R.id.frontLayout)
        private val flipView: EasyFlipView = view.findViewById(R.id.easyFlipView)

        val share = view.findViewById<ImageView>(R.id.iv_share)
        val location = view.findViewById<ImageView>(R.id.iv_location)
        val website = view.findViewById<ImageView>(R.id.iv_website)
        val phone = view.findViewById<ImageView>(R.id.iv_phone)
        val photos = view.findViewById<ImageView>(R.id.iv_imageGallery)
        val video = view.findViewById<ImageView>(R.id.iv_video)
        val backsideBusinessName = view.findViewById<TextView>(R.id.tv_businessName)
        val tv_quickAccess= view.findViewById<TextView>(R.id.tv_quickAccess)


        init {

            backSide.setOnClickListener {
                flipView.flipDuration = 1000
                flipView.flipTheView()
            }
            frontSide.setOnClickListener {
                flipView.flipDuration = 1000
                flipView.flipTheView()
            }
        }
        var bname = view.findViewById<TextView>(R.id.tv_bName)
        var name = view.findViewById<TextView>(R.id.tv_userName)
        var designation = view.findViewById<TextView>(R.id.tv_userDesignation)
        var iv_detailsArrow = view.findViewById<ImageView>(R.id.iv_detailsArrow)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardsAdapters.MyVH {

        val v : View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_view_item, parent, false)
        return MyVH(v!!)
    }

    override fun onBindViewHolder(holder: MyCardsAdapters.MyVH, position: Int) {

        val jsonObject =this.jsonArray.getJSONObject(position)
        holder.bname.text = jsonObject.getString("businessName")
        holder.backsideBusinessName.text = jsonObject.getString("businessName")
        holder.name.text = jsonObject.getString("name")
        holder.designation.text = jsonObject.getString("designation")

        holder.iv_detailsArrow.tag = position
        holder.iv_detailsArrow.setOnClickListener {


            val cardobject = jsonArray.getJSONObject(position)
            Log.d("object","$cardobject")
//            val cardId = jsonObject.getString("uuid")
//            Toast.makeText(context,cardId,Toast.LENGTH_LONG).show()
            val intent  = Intent(context, CardDetailsActivity::class.java)
            intent.putExtra("cardObject","$cardobject")
            context?.startActivity(intent)
        }

        holder.share.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type ="text/plain"
            val shareBody = "Name"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Report")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            context?.startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
        holder.location.setOnClickListener {

        }
        holder.website.setOnClickListener {
            val url = "http://www.Google.com"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context!!.startActivity(i)

        }
        holder.phone.setOnClickListener {

            val  intent = Intent(Intent.ACTION_DIAL)
            intent.data =  Uri.parse("tel:" + 7837176356)
            context?.startActivity(intent)

        }
        holder.photos.setOnClickListener {

        }
        holder.video.setOnClickListener {

        }
    }


    override fun getItemCount(): Int {
        return jsonArray.length()
    }
}