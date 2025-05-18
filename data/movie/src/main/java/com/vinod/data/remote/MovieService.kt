package com.vinod.data.remote

import com.vinod.domain.model.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET(".")
    suspend fun getMovie(
        @Query("apikey") apiKey: String, @Query("t") title: String
    ): MovieResponse
}