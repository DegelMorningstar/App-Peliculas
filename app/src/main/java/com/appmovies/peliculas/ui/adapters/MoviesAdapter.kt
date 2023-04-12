package com.appmovies.peliculas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appmovies.peliculas.R
import com.appmovies.peliculas.data.Paths
import com.appmovies.peliculas.databinding.MovieListItemBinding
import com.appmovies.peliculas.domain.Pelicula
import com.bumptech.glide.Glide

class MoviesAdapter(val context: Context): ListAdapter<Pelicula,MoviesAdapter.ViewHolder>(
    DiffCallBack
) {
    companion object DiffCallBack: DiffUtil.ItemCallback<Pelicula>(){
        override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private lateinit var onItemClickListener: ((pelicula: Pelicula) -> Unit)

    fun setOnItemClickListener(onItemClickListener: (pelicula: Pelicula) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelicula = getItem(position)
        holder.bind(pelicula)
    }

    inner class ViewHolder(private val binding: MovieListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pelicula: Pelicula) {
            binding.titleItem.text = pelicula.title
            binding.valueRating.text = pelicula.vote_average.toString()
            val imageView = binding.imageItem
            val url = Paths.BASE_IMAGE_URL + pelicula.poster_path
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_img)
                .into(imageView)

            //TODO agregar la imagen
            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(pelicula)
                }
            }
        }
    }




}