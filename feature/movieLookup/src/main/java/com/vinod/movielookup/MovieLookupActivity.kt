package com.vinod.movielookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vinod.domain.model.response.MovieResponse
import org.koin.androidx.compose.koinViewModel

class MovieLookupActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MovieScreen()
                }
            }
        }
    }
}

@Composable
fun MovieScreen(viewModel: MovieLookupViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 16.dp)
    ) {
        // 🔍 Search Input
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Enter movie name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ▶️ Search Button
        Button(
            onClick = { viewModel.loadMovie(query) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔁 State Handling
        when (state) {
            is DataState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is DataState.Success -> {
                val movie = (state as DataState.Success<MovieResponse>).data
                MovieContent(movie)
            }

            is DataState.Error -> {
                val message = (state as DataState.Error).message
                Text(
                    text = "Error: $message",
                    color = MaterialTheme.colorScheme.error
                )
            }

            DataState.None -> {}
        }
    }
}

@Composable
fun MovieContent(movie: MovieResponse) {
    Column {
        if (movie.response != "False") {
            Text(text = movie.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Released: ${movie.released}")
            Text(text = "Genre: ${movie.genre}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie.plot)
        } else {
            Text(
                text = "Movie Not Found",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}