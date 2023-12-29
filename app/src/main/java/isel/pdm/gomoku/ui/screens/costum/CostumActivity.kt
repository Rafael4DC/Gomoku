package isel.game.gomoku.ui.screens.costum

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import isel.pdm.gomoku.ui.home.TAG
import isel.pdm.gomoku.ui.screens.costum.CostumScreen

class CostumActivity : ComponentActivity() {

    companion object {
    fun navigateTo(origin: Activity) {
        val intent = Intent(origin, CostumActivity::class.java)
        origin.startActivity(intent)
    }
}
    private val viewModel by viewModels<CostumViewModel>()
    //private val service = FakeRankingService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreateR() called")
        setContent {
            CostumScreen(
                gameRules = viewModel.gameRules,
                onChangeVariant = { viewModel.changeVariant(it) },
                onChangeBoardSize = { viewModel.changeBoardSize(it) },
                onChangeOpenRules = { viewModel.changeOpenRules(it) },
                onChangeRoundDuration = { viewModel.changeRoundDuration(it) },
                onStart = {  },
                onBackRequest = { finish() },
            )
        }
    }
    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStartR() called")
    }
}
