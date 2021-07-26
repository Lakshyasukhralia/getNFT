package com.getnft.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.getnft.R
import com.getnft.data.model.response.Nft
import com.getnft.data.util.GlideApp
import com.getnft.data.util.setBackgroundFromPalette
import com.getnft.databinding.ItemNftBinding
import kotlin.math.roundToInt

class NftAdapter(val checkoutListener: CheckoutListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = listOf<Nft>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun submitData(data: List<Nft>) {
        itemList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NftViewHolder(ItemNftBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NftViewHolder -> {
                holder.bind(itemList[position])
            }
        }
    }

    inner class NftViewHolder(private val binding: ItemNftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Nft) {

            binding.name.text = item.name

            if(item.price!=null){
                binding.price.text = "$".plus(item.price.toDouble().roundToInt().toString())
                binding.price.isVisible = true
            }else{
                binding.price.isVisible = false
            }

            binding.btnCheckout.setOnClickListener {
                item.externalUrl?.let { url -> checkoutListener.onCheckoutClick(url) }
            }

            GlideApp.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.ic_loading)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setBackgroundFromPalette(resource,binding.clContainer)
                        return false
                    }

                })
                .into(binding.image)

        }
    }

}