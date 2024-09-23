package com.victorhbo.technicalchallengecatspictures.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.victorhbo.technicalchallengecatspictures.R
import com.victorhbo.technicalchallengecatspictures.models.Image
import com.victorhbo.technicalchallengecatspictures.views.ImageDetailActivity

class ImageAdapter(private val images: MutableList<Image>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(image: Image) {
            Picasso.get().load(image.link).into(imageView, object : Callback {
                override fun onSuccess() {
                    Log.d("Picasso", "Imagem carregada com sucesso: ${image.link}")
                }

                override fun onError(e: Exception?) {
                    Log.e("Picasso", "Erro ao carregar imagem: ${e?.printStackTrace()}")
                }
            })

            // Configurar o clique na imagem
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ImageDetailActivity::class.java)
                intent.putExtra("IMAGE_URL", image.link)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount() = images.size
}
