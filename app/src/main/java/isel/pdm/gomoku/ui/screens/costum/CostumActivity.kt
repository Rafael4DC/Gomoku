package isel.game.gomoku.ui.screens.costum

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import isel.pdm.gomoku.ui.screens.costum.CostumScreen
import isel.pdm.gomoku.ui.screens.costum.CostumViewModel

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
    }
}
