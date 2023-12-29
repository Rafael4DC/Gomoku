package isel.game.gomoku.ui.screens.ranking.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import isel.pdm.gomoku.domain.game.app.Ranker

@Composable
fun RankingView(rankers: List<Ranker>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add vertical spacing between items
    ) {
        for (ranker in rankers) {
            RankerItem(ranker)
        }
    }
}

@Composable
fun RankerItem(ranker: Ranker) {
    Row(
    ) {
        Text(text = "Username: ${ranker.username}")
        Text(text = "Score: ${ranker.score}")
        Text(text = "Total Games: ${ranker.totalGames}")
    }
}


/*

@Preview(showBackground = true)
@Composable
private fun JokeViewPreview() {
    Gomoku9Theme {
        RankingView(NoOpRankService.getRankers(3))
    }
}*/

