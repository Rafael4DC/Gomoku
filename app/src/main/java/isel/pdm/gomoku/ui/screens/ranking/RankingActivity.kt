package isel.pdm.gomoku.ui.screens.ranking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import isel.pdm.gomoku.ui.home.TAG
import isel.game.gomoku.ui.screens.ranking.RankingScreen
import isel.pdm.gomoku.domain.app.FakeRankingService

class RankingActivity : ComponentActivity() {

    companion object {
    fun navigateTo(origin: Activity) {
        val intent = Intent(origin, RankingActivity::class.java)
        origin.startActivity(intent)
    }
}
    private val viewModel by viewModels<RankingViewModel>()
    private val service = FakeRankingService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchRankers(service)
        Log.v(TAG, "onCreateR() called")
        setContent {
            RankingScreen(
                rankers = viewModel.rankers,
                onBackRequest = { finish() },
            )
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStartR() called")
    }
}
