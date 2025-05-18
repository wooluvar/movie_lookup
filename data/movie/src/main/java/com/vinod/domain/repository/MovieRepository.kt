package com.vinod.domain.repository

import com.vinod.domain.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovie(movieName: String): Flow<MovieResponse>
}