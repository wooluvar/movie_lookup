package com.vinod.data.remote

import com.vinod.domain.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    fun getMovieDetails(movieName: String): Flow<MovieResponse>
}