package isel.game.gomoku.ui.screens.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


data class ButtonInfo(val onClick: () -> Unit, val text: String)
@Composable
fun ButtonSection(buttonInfo: List<ButtonInfo>,buttonModifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = Color.Transparent)
    ) {
        for (button in buttonInfo){
            Button(
                modifier = buttonModifier,
                onClick = button.onClick
            ) {
                Text(text = button.text, fontSize = 20.sp)
            }
        }
    }
}