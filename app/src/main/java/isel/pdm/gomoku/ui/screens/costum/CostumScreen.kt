package isel.pdm.gomoku.ui.screens.costum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.game.gomoku.ui.screens.about.AboutScreenTestTag
import isel.pdm.gomoku.ui.screens.shared.components.DropdownMenu
import isel.game.gomoku.ui.screens.shared.components.IntSlider
import isel.pdm.gomoku.domain.game.play.GameRules
import isel.pdm.gomoku.domain.game.play.OpenRule
import isel.pdm.gomoku.domain.game.play.Variant
import isel.pdm.gomoku.ui.common.theme.Gomoku9Theme

/**
 * Tags used to identify the components of the JokeScreen in automated tests
 * (see app/src/androidTest/java/isel/pdm/chucknorris/JokeScreenTests.kt)
 */
//const val FetchItTestTag = "FetchItTestTag"
//const val JokeTestTag = "JokeTestTag"
//const val JokeScreenTestTag = "JokeScreenTestTag"

/**
 * Root composable for the screen that displays a joke.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumScreen(
    gameRules: GameRules = GameRules(),
    onChangeVariant: ((Variant) -> Unit) = {},
    onChangeBoardSize : ((Int)->Unit) = { },
    onChangeOpenRules : ((OpenRule)->Unit) = {},
    onChangeRoundDuration : ((Int)->Unit) = { },
    onStart: (() -> Unit) = {},
    onBackRequest: (() -> Unit) = {},
) {
    Gomoku9Theme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(AboutScreenTestTag),
            topBar = { Column {
                Button(
                    onClick = onBackRequest
                ) {
                    Text(text = "Back")
                }
            } },
        ) { innerPadding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text(text = "Create Game", fontSize = 30.sp, modifier = Modifier.padding(10.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Board Size", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
                IntSlider(min = 15, max = 19, onChangeBoardSize = onChangeBoardSize)

                Text(text = "Variant", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
                DropdownMenu(onChangeVariant,gameRules.variant,Variant.values())

                Text(text = "Opener Rules", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
                DropdownMenu(onChangeOpenRules,gameRules.openRule,OpenRule.values())

                Text(text = "Turn Seconds", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
                IntSlider(min = 15, max = 45, onChangeBoardSize = onChangeRoundDuration)

                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onStart,
                    modifier = Modifier
                        .height(50.dp)
                        .width(250.dp)
                ) {
                    Text(text = "Start")
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateScreenPreview() {
    CostumScreen()
}


