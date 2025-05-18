package com.vinod.di

import com.vinod.data.remote.MovieDataSource
import com.vinod.data.remote.MovieDataSourceImpl
import com.vinod.data.remote.MovieService
import com.vinod.data.repository.MovieRepositoryImpl
import com.vinod.domain.repository.MovieRepository
import com.vinod.interactors.GetMovieUseCase
import org.koin.dsl.module
import retrofit2.Retrofit

val movieModule = module {

    // API
    single<MovieService> { get<Retrofit>().create(MovieService::class.java) }

    // DataSource
    single<MovieDataSource> { MovieDataSourceImpl(get()) }

    // Repository
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    // UseCase
    factory { GetMovieUseCase(get()) }
}