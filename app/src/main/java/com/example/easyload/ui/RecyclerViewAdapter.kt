package com.example.easyload.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.easyload.R
import com.xu.easyload.EasyLoad

/**
 * @author 言吾許
 */
class RecyclerViewAdapter(private val data: List<String>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.showImage(data[position], context!!, position)
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var img: ImageView = view.findViewById(R.id.img_item)
        fun showImage(url: String, context: Context, position: Int) {
            val iLoadService = EasyLoad.initLocal().inject(img)
            Glide.with(context)
                    .load(url)
                    .addListener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            iLoadService.showSuccess()
                            println("加载成功$position")
                            return false
                        }

                    })
                    .into(img)
        }
    }
}