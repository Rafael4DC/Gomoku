package isel.game.gomoku.ui.screens.shared.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun IntSlider(min:Int,max:Int,onChangeBoardSize:((Int)->Unit)) {
    var value by remember { mutableStateOf(min) }
    Slider(
        value = value.toFloat(),
        onValueChange = { newValue ->
            onChangeBoardSize(value) // Call the function with the Int value
            value = newValue.toInt() // Convert the Float to Int
        },
        valueRange = min.toFloat()..max.toFloat(),
        modifier = Modifier
            .width(200.dp)
    )
    Text(text = value.toString())

}