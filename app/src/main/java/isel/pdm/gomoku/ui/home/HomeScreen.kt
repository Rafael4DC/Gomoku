package isel.pdm.gomoku.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.gomoku.R
import isel.game.gomoku.ui.screens.shared.components.ButtonInfo
import isel.game.gomoku.ui.screens.shared.components.ButtonSection
import isel.pdm.gomoku.ui.common.theme.Gomoku9Theme

/**
 * Tags used to identify the components of the JokeScreen in automated tests
 * (see app/src/androidTest/java/isel/pdm/chucknorris/JokeScreenTests.kt)
 */
const val FetchItTestTag = "FetchItTestTag"
const val JokeTestTag = "JokeTestTag"
const val JokeScreenTestTag = "JokeScreenTestTag"

/**
 * Root composable for the screen that displays a joke.
 * @param service the service used to fetch jokes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onInfoRequested: () -> Unit = {},
    onLeaderboardRequested: () -> Unit = {},
    onLobbyRequested: () -> Unit = {},
    onProfileRequested: () -> Unit = {}
) {
    Gomoku9Theme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(JokeScreenTestTag),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.TopStart)
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 30.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 30.dp)
                    )
                }
            }

        ) { it ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .paint(painterResource(id = R.drawable.ic_gomoku_background), contentScale = ContentScale.FillBounds)
                    .padding(it),
            ) {

                ButtonSection(buttonInfo =
                listOf(
                    ButtonInfo(
                        text = "Lobby",
                        onClick = onLobbyRequested
                    ),
                    ButtonInfo(
                        text = "Leaderboard",
                        onClick = onLeaderboardRequested
                    ),
                    ButtonInfo(
                        text = "About",
                        onClick = onInfoRequested
                    ),
                    ButtonInfo(
                        text = "Profile",
                        onClick = onProfileRequested
                    )
                ),
                    Modifier
                        .size(300.dp, 80.dp)
                        .padding(10.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

