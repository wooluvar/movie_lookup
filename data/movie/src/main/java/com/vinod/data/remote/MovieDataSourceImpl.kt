package com.vinod.data.remote

import com.vinod.core.Constant.API_KEY
import com.vinod.domain.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDataSourceImpl(
    private val apiService: MovieService
) : MovieDataSource {

    override fun getMovieDetails(movieName: String): Flow<MovieResponse> = flow {
        try {
            val response = apiService.getMovie(API_KEY, movieName)
            emit(response)
        } catch (e: Exception) {
            throw e
        }
    }
}