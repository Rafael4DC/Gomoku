import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import isel.pdm.gomoku.ui.screens.about.AboutActivity
import isel.pdm.gomoku.DependenciesContainer
import isel.pdm.gomoku.domain.IOState
import isel.pdm.gomoku.domain.IOState.Loaded
import isel.pdm.gomoku.domain.getOrNull
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostTokenOutputModel
import isel.pdm.gomoku.ui.game.lobby.LobbyActivity
import isel.pdm.gomoku.ui.home.HomeScreen
import isel.pdm.gomoku.ui.profile.ProfileActivity
import isel.pdm.gomoku.ui.screens.ranking.RankingActivity
import kotlinx.coroutines.launch
import pt.isel.daw.gomokuroyale.http.controller.user.model.getuser.GetUserOutputModel

const val TAG = "GOMOKU_TAG"

class HomeActivity : ComponentActivity() {
    private val vm by viewModels<HomeScreenViewModel> {
        HomeScreenViewModel.factory(
            (application as DependenciesContainer).userService,
            (application as DependenciesContainer).dataStore
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate() called")

        setContent {
            val userInfo by vm.userInfo.collectAsState(initial = IOState.Idle)
            val leaderboardInfo by vm.leaderboards.collectAsState(initial = IOState.Idle)
            HomeScreen(
                onInfoRequested = { AboutActivity.navigateTo(this) },
                onLeaderboardRequested = { getLeaderboardAndNavigate(leaderboardInfo) },
                onLobbyRequested = { checkLobbyAndNavigate(userInfo) },
                onProfileRequested = { checkProfileAndNavigate(userInfo) }
            )
        }
    }

    private fun getLeaderboardAndNavigate(userInfo: IOState<List<GetUserOutputModel>?>) {
        lifecycleScope.launch{
            vm.getLeaderboards()
            if (userInfo is Loaded && userInfo.getOrNull() != null) {
                RankingActivity.navigateTo(this@HomeActivity,userInfo.getOrNull()!!)
            }
            vm.resetToIdle()
        }
    }

    private fun checkLobbyAndNavigate(userInfo: IOState<UserPostTokenOutputModel?>) {
        lifecycleScope.launch {
            vm.checkUser()
            if (userInfo is Loaded && userInfo.getOrNull() != null) {
                LobbyActivity.navigateTo(this@HomeActivity,userInfo.getOrNull()!!)
            } else {
                LoginActivity.navigateTo(this@HomeActivity)
            }
            vm.resetToIdle()
        }
    }

    private fun checkProfileAndNavigate(userInfo: IOState<UserPostTokenOutputModel?>) {
        lifecycleScope.launch {
            vm.checkUser()
            if (userInfo is Loaded && userInfo.getOrNull() != null) {
                ProfileActivity.navigateTo(this@HomeActivity,userInfo.getOrNull()!!)
            } else {
                LoginActivity.navigateTo(this@HomeActivity)
            }
            vm.resetToIdle()
        }
    }
}
