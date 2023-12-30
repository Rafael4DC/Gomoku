package isel.pdm.gomoku.ui.screens.ranking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import isel.game.gomoku.ui.screens.ranking.RankingScreen
import isel.pdm.gomoku.DependenciesContainer
import isel.pdm.gomoku.domain.app.FakeRankingService
import pt.isel.daw.gomokuroyale.http.controller.user.model.getuser.GetUserOutputModel

class RankingActivity : ComponentActivity() {
    private val vm by viewModels<RankingViewModel> {
        HomeScreenViewModel.factory((application as DependenciesContainer).userService,
            (application as DependenciesContainer).dataStore)
    }
    companion object {
    fun navigateTo(origin: Activity, orNull: List<GetUserOutputModel>) {
        val intent = Intent(origin, RankingActivity::class.java)
        origin.startActivity(intent)
    }
}
    private val viewModel by viewModels<RankingViewModel>()
    private val service = FakeRankingService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchRankers(service)
        setContent {
            RankingScreen(
                rankers = viewModel.rankers,
                onBackRequest = { finish() },
            )
        }
    }

    override fun onStart() {
        super.onStart()
    }
}
