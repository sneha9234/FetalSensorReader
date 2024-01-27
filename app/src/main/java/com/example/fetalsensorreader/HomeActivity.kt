package com.example.fetalsensorreader

import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeatherAndroidTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        VideoPlayer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        GreetingMessage(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingMessage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Use MutableState for channel text to allow reassignment
    var channel1Text by remember { mutableStateOf("Channel 1: 0.0") }
    var channel2Text by remember { mutableStateOf("Channel 2: 0.0") }
    var channel3Text by remember { mutableStateOf("Channel 3: 0.0") }
    var channel4Text by remember { mutableStateOf("Channel 4: 0.0") }


    // Start background service for text processing
    DataProcessingService(
        context,
        onError = { Log.i("error processing:", "error processing: $it") },
        onUpdateText = { processedText ->
            // Update the corresponding text based on the channel index
            if (processedText.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    channel1Text = "Channel 1: ${processedText[0]}"
                    channel2Text = "Channel 2: ${processedText[1]}"
                    channel3Text = "Channel 3: ${processedText[2]}"
                    channel4Text = "Channel 4: ${processedText[3]}"
                }
            }
        }
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = channel1Text)
        Text(text = channel2Text)
        Text(text = channel3Text)
        Text(text = channel4Text)
    }
}

@Composable
fun VideoPlayer(modifier: Modifier = Modifier) {

    AndroidView(
        factory = { context ->
            VideoView(context).apply {
                setVideoPath("android.resource://${context.packageName}/${R.raw.promotional_video}")
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                    mediaPlayer.playbackParams = mediaPlayer.playbackParams.setSpeed(1.0.toFloat())
                    start()
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FeatherAndroidTasksTheme {
        GreetingMessage()
    }
}