package com.victorhbo.technicalchallengecatspictures.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorhbo.technicalchallengecatspictures.R
import com.victorhbo.technicalchallengecatspictures.models.Image
import com.victorhbo.technicalchallengecatspictures.utils.AlertDialogUtil
import com.victorhbo.technicalchallengecatspictures.utils.Constants
import com.victorhbo.technicalchallengecatspictures.utils.NetworkUtil
import com.victorhbo.technicalchallengecatspictures.views.ImageDetailActivity

class ImageAdapter : ListAdapter<Image, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(image: Image) {
            Glide.with(itemView.context)
                .load(image.link)
                .into(imageView)

            val context = itemView.context
            itemView.setOnClickListener {
                if (NetworkUtil.isInternetAvailable(context)) {
                    val intent = Intent(context, ImageDetailActivity::class.java).apply {
                        putExtra(Constants.EXTRAS.NAME, image.link)
                    }
                    context.startActivity(intent)
                } else {
                    AlertDialogUtil.showErrorAlertDialog(context,
                        context.getString(R.string.check_your_internet))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}