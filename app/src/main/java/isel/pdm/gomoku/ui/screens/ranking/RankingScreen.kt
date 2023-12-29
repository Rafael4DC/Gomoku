package isel.game.gomoku.ui.screens.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.gomoku.domain.game.app.Ranker
import isel.game.gomoku.ui.screens.about.AboutScreenTestTag
import isel.game.gomoku.ui.screens.ranking.components.RankingView
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
fun RankingScreen(
    rankers: List<Ranker>? = null,
    onBackRequest: (() -> Unit) = {},
) {
    Gomoku9Theme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(AboutScreenTestTag),
            bottomBar = { Column {
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
                Text(text = "Ranking", fontSize = 30.sp, modifier = Modifier.padding(10.dp))
                if(rankers == null)
                    Text(text = "Loading Rankers", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
                else
                    RankingView(rankers = rankers)
            }
        }
    }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JokeScreenPreview() {
    JokeScreen()
}
*/

