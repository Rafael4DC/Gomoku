package isel.pdm.gomoku.ui.screens.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun <T>DropdownMenu(onChange:((T) -> Unit),current:T,items: Array<T>, width: Dp = 200.dp, height: Dp = 50.dp) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(current) }

    Column(
        modifier = Modifier
            .width(width)
            .background(Color.Blue)
            .height(height) // Specify the height for the Box
            .clickable(onClick = { expanded = true }),
        horizontalAlignment = Alignment.CenterHorizontally // Align content within the Box
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .background(Color.Gray)
                .height(height) // Specify the height for the Box
                .clickable(onClick = { expanded = true }),
            contentAlignment = Alignment.Center // Align content within the Box
        ) {
            Text(
                selected.toString(),
                textAlign = TextAlign.Center,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.Red).width(width)
        ) {
            items.forEach { s ->
                DropdownMenuItem(
                    text = { Text(text = s.toString(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    onClick = {
                        selected = s
                        onChange(s)
                        expanded = false
                    }
                )
            }
        }

    }
}