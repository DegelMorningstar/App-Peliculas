package com.appmovies.peliculas.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appmovies.peliculas.R
import com.appmovies.peliculas.data.Paths
import com.appmovies.peliculas.databinding.FragmentDetailBinding
import com.appmovies.peliculas.domain.Pelicula
import com.appmovies.peliculas.ui.viewmodel.MoviesViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        binding.lifecycleOwner = this
        val pelicula = arguments?.getParcelable<Pelicula>("id")
        lifecycleScope.launch {
            viewModel.getDetailMovie(pelicula!!.id)
        }

        binding.detailArrowBack.setOnClickListener {
            findNavController().navigate(R.id.fromDetailToMovies)
        }

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            setView(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.loadingWheel.visibility = View.VISIBLE
                binding.contentDetail.visibility = View.GONE
            }else{
                binding.loadingWheel.visibility = View.GONE
                binding.contentDetail.visibility = View.VISIBLE
            }
        })
        viewModel.offline.observe(viewLifecycleOwner, Observer {
            if(it){
                noInternetToast()
                setView(pelicula!!)
                //findNavController().navigateUp()
            }
        })

        return binding.root
    }

    private fun setView(it: Pelicula) {
        binding.titleDetail.text = it.title
        binding.ratingBar.rating = it.vote_average
        binding.resumenDetail.text = it.overview
        binding.releaseDate.text = it.release_date
        val imageView = binding.imageDetail
        val url = Paths.BASE_IMAGE_URL + it.backdrop_path
        Glide
            .with(requireContext())
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.placeholder_img)
            .into(imageView)
    }

    private fun noInternetToast() {
        Toast.makeText(requireContext(), "Sin conexion a internet", Toast.LENGTH_SHORT).show()
    }

}