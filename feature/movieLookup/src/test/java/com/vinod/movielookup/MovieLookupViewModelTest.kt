package com.vinod.movielookup

import com.vinod.domain.model.response.MovieResponse
import com.vinod.interactors.GetMovieUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieLookupViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getMovieUseCase: GetMovieUseCase = mockk()
    private lateinit var viewModel: MovieLookupViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieLookupViewModel(getMovieUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadMovieSuccess() = runTest {
        val movie = MovieResponse("Inception", "A mind-bending thriller", "2010")
        val flow = flowOf(movie)

        coEvery { getMovieUseCase("Inception") } returns flow

        viewModel.loadMovie("Inception")
        advanceUntilIdle() // process coroutine

        val state = viewModel.state.value
        assertTrue(state is DataState.Success)
        assertEquals(movie, (state as DataState.Success).data)
    }

    @Test
    fun testLoadMovieException() = runTest {
        val exception = RuntimeException("Network error")

        coEvery { getMovieUseCase("Inception") } returns flow {
            throw exception
        }

        viewModel.loadMovie("Inception")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is DataState.Error)
        assertEquals("Network error", (state as DataState.Error).message)
    }
}
