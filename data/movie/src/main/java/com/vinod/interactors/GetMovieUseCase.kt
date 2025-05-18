package com.vinod.interactors

import com.vinod.domain.model.response.MovieResponse
import com.vinod.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(movieName: String): Flow<MovieResponse> = repository.fetchMovie(movieName)
}