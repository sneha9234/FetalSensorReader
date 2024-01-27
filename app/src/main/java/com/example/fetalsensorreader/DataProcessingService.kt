package com.example.fetalsensorreader

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@Composable
fun DataProcessingService(
    context: Context,
    onError: (String) -> Unit,
    onUpdateText: suspend (List<Double>) -> Unit
) {
    val scope = rememberCoroutineScope()

    DisposableEffect(true) {
        val job = scope.launch {
            try {
                val samples = readDataFromRawResource(context)
                val channelValues = mutableListOf<Double>()

                samples.forEachIndexed { index, sample ->
                    // Simulate processing time
                    delay(1) // Adjust delay as needed

                    // Process every 100th sample
                    if (index % 100 == 0) {
                        val processedSample = processSample(sample)
                        channelValues.addAll(processedSample)
                        onUpdateText(channelValues.toList())
                        channelValues.clear()
                    }
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }

        onDispose {
            job.cancel()
        }
    }
}

fun readDataFromRawResource(context: Context): List<String> {
    val samples = mutableListOf<String>()
    try {
        val inputStream: InputStream = context.resources.openRawResource(R.raw.input_sattva)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        var line: String? = bufferedReader.readLine()
        while (line != null) {
            samples.add(line)
            line = bufferedReader.readLine()
        }

        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return samples
}

fun processSample(sample: String): List<Double> {
    val regex = Regex("""!(.*?)!""")
    val hexValuesList = mutableListOf<Double>()

    regex.findAll(sample).forEach { matchResult ->
        val extractedString = matchResult.groupValues[1]
        val hexValues = extractHexValues(extractedString)
        hexValuesList.addAll(hexValues)
    }

    return hexValuesList

}

fun extractHexValues(inputString: String): List<Double> {
    val hexValuesRegex = Regex("""[0-9A-Fa-f]{6}""")
    val hexMatches = hexValuesRegex.findAll(inputString).toList()

    val hexValues = mutableListOf<Double>()
    hexMatches.takeLast(4).forEach { hexMatch ->
        hexValues.add(hexToDouble(hexMatch.value))
    }

    return hexValues
}

fun hexToDouble(hexValue: String): Double {
    val bigIntegerValue = java.math.BigInteger(hexValue, 16)
    return bigIntegerValue.toDouble()
}