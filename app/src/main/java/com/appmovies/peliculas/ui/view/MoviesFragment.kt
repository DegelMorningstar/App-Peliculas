package com.appmovies.peliculas.ui.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmovies.peliculas.R
import com.appmovies.peliculas.databinding.FragmentMoviesBinding
import com.appmovies.peliculas.domain.Pelicula
import com.appmovies.peliculas.ui.adapters.MoviesAdapter
import com.appmovies.peliculas.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel:MoviesViewModel by viewModels()

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var topRatedMoviesList: MutableList<Pelicula>
    private lateinit var nowPlayingMoviesList: MutableList<Pelicula>
    private lateinit var adapterTopMovies:MoviesAdapter
    private lateinit var adapterNowPlayingMovies:MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movies,container,false)
        binding.lifecycleOwner = this

        binding.etSearch.addTextChangedListener {
            filtrarTopRatedMovies(it)
            filtrarNowPlayingMovies(it)
        }

        initTopRatedMoviesRecyclerView()

        initNowPlayingMoviesRecyclerView()



        adapterTopMovies.setOnItemClickListener {
            onNavigateToDetail(it)
        }

        adapterNowPlayingMovies.setOnItemClickListener {
            onNavigateToDetail(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Log.d("****loading","loading value ${viewModel.isLoading.value}")
            //TODO loading
        })
        viewModel.offline.observe(viewLifecycleOwner, Observer {
            if(it){
                noInternetToast()
            }
        })
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            topRatedMoviesList = it.toMutableList()
            adapterTopMovies.submitList(topRatedMoviesList)
        })
        viewModel.nowPlayingMoviesList.observe(viewLifecycleOwner, Observer {
            nowPlayingMoviesList = it.toMutableList()
            adapterNowPlayingMovies.submitList(nowPlayingMoviesList)
        })

        return binding.root
    }

    private fun noInternetToast() {
        Toast.makeText(requireContext(), "Sin conexion a internet", Toast.LENGTH_SHORT).show()
    }

    private fun filtrarTopRatedMovies(it: Editable?) {
        if (it.toString().isNotEmpty()) {
            val moviesFiltered =
                topRatedMoviesList.filter { movies ->
                    movies.title.lowercase().contains(it.toString().lowercase())
                }
            adapterTopMovies.submitList(moviesFiltered)
        } else {
            adapterTopMovies.submitList(topRatedMoviesList)
        }
    }
    private fun filtrarNowPlayingMovies(it: Editable?) {
        if (it.toString().isNotEmpty()) {
            val moviesFiltered =
                nowPlayingMoviesList.filter { movies ->
                    movies.title.lowercase().contains(it.toString().lowercase())
                }
            adapterNowPlayingMovies.submitList(moviesFiltered)
        } else {
            adapterNowPlayingMovies.submitList(nowPlayingMoviesList)
        }
    }

    private fun initTopRatedMoviesRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTopMovies.layoutManager = layoutManager
        adapterTopMovies = MoviesAdapter(requireContext())
        binding.rvTopMovies.adapter = adapterTopMovies
    }
    private fun initNowPlayingMoviesRecyclerView(){
        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowMovies.layoutManager = layoutManager2
        adapterNowPlayingMovies = MoviesAdapter(requireContext())
        binding.rvNowMovies.adapter = adapterNowPlayingMovies
    }

    private fun onNavigateToDetail(it:Pelicula){
        val bundle = Bundle()
        bundle.putParcelable("id",it)
        findNavController().navigate(R.id.fromMoviesToDetail,bundle)
    }
}