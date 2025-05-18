package com.vinod.data.repository

import com.vinod.data.remote.MovieDataSource
import com.vinod.domain.model.response.MovieResponse
import com.vinod.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val dataSource: MovieDataSource
) : MovieRepository {

    override fun fetchMovie(movieName: String): Flow<MovieResponse> = flow {
        emitAll(dataSource.getMovieDetails(movieName))
    }
}