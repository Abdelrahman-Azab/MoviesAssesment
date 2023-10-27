package com.azab.bestmovies.presentation.main.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azab.bestmovies.R
import com.azab.bestmovies.data.remote.model.Movie
import com.azab.bestmovies.databinding.FragmentMovieBinding
import com.azab.bestmovies.presentation.main.movie.adapter.MoviesAdapter
import com.azab.bestmovies.presentation.main.movie.adapter.SetMovieClickListener
import com.azab.bestmovies.presentation.main.movie.adapter.StateLoadAdapter
import com.azab.bestmovies.utils.extensions.ConnectivityExtensions
import com.azab.bestmovies.utils.extensions.hideView
import com.azab.bestmovies.utils.extensions.showErrorMsg
import com.azab.bestmovies.utils.extensions.showView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MovieFragment : Fragment(), SetMovieClickListener {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var loadStateAdapter: StateLoadAdapter
    private val connectivityExtensions: ConnectivityExtensions by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesRecyclerView()
        initView()
    }

    private fun initView(){
        checkConnectionToFetchData()

    }
    private fun checkConnectionToFetchData(){
        if (connectivityExtensions.isConnected())
            fetchData()
        else
            requireActivity().showErrorMsg(getString(R.string.no_internet))
    }

    override fun onResume() {
        super.onResume()
        checkLikesCounter()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("SetTextI18n")
    private fun checkLikesCounter() {
        if (viewModel.likesCounter == 0) {
            binding.counterOfLikesTv.hideView()
        } else {
            binding.counterOfLikesTv.showView()
            binding.counterOfLikesTv.text = getString(R.string.counter_likes)+" "+viewModel.likesCounter.toString()
        }
    }



    private fun initMoviesRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            moviesAdapter = MoviesAdapter(this@MovieFragment)
            adapter = moviesAdapter
        }
    }

    private fun fetchData() {
        loadStateAdapter = StateLoadAdapter { moviesAdapter.retry() }
        moviesAdapter.addLoadStateListener { loadState ->
            val updatedLoadState = when {
                loadState.refresh is LoadState.Loading -> LoadState.NotLoading(
                    endOfPaginationReached = false
                )

                loadState.append is LoadState.Loading -> LoadState.Loading
                else -> LoadState.NotLoading(endOfPaginationReached = false)
            }

            loadStateAdapter.loadState = updatedLoadState
            if (loadState.refresh is LoadState.NotLoading && moviesAdapter.itemCount == 0) {
                binding.recyclerView.hideView()
                binding.noDataTv.showView()
            } else {
                binding.noDataTv.hideView()
                binding.recyclerView.showView()
                binding.progressBar.hideView()
            }

            if (loadState.source.refresh is LoadState.Error) {
                val errorState = loadState.source.refresh as LoadState.Error
                val errorMessage = errorState.error.message
                if (errorMessage != null) {
                    requireActivity().showErrorMsg(errorMessage)
                    binding.noDataTv.showView()
                }
            }
        }

        lifecycleScope.launch {
            try {
                val pagingData = viewModel.getMovies()
                pagingData.collectLatest {
                    moviesAdapter.submitData(it)
                }
            } catch (e: Exception) {
                e.message?.let { requireActivity().showErrorMsg(it) }
            }
        }

    }



    override fun onMovieClicked(item: Movie, position: Int) {
        if (item.liked == true) viewModel.likesCounter++ else if (viewModel.likesCounter > 0) viewModel.likesCounter--
        checkLikesCounter()
    }


}