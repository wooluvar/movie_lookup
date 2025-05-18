package com.vinod.movielookup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinod.domain.model.response.MovieResponse
import com.vinod.interactors.GetMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieLookupViewModel(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DataState<MovieResponse>>(DataState.None)
    val state: StateFlow<DataState<MovieResponse>> = _state.asStateFlow()

    fun loadMovie(name: String) {
        viewModelScope.launch {
            _state.value = DataState.Loading
            getMovieUseCase(name)
                .catch { e ->
                    _state.value = DataState.Error(e.message ?: "Unknown error")
                }
                .collect { movie ->
                    _state.value = DataState.Success(movie)
                }
        }
    }
}