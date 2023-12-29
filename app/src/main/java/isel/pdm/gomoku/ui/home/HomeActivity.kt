package isel.pdm.gomoku.ui.home

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
import isel.pdm.gomoku.R
import isel.pdm.gomoku.domain.IOState.Loaded
import isel.pdm.gomoku.domain.getOrNull
import isel.pdm.gomoku.domain.idle
import isel.pdm.gomoku.domain.user.UserInfo
import isel.pdm.gomoku.ui.common.ErrorAlert
import isel.pdm.gomoku.ui.game.lobby.LobbyActivity
import isel.pdm.gomoku.ui.main.MainScreenViewModel
import isel.pdm.gomoku.ui.preferences.UserPreferencesActivity
import isel.pdm.gomoku.ui.screens.ranking.RankingActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val TAG = "GOMOKU_TAG"

/**
 * Lecture #5 script
 *
 * Step 1 - Lets briefly review the application's structure
 * Step 2 - Describe the application's UX (navigation)
 * Step 3 - Lets create the About screen, using the same structure as before
 *     Step 3.1 - Create the AboutActivity.
 *     Step 3.2 - Create the AboutScreen. Empty, at first.
 * Step 4 - Add a top bar to both screens.
 *     Step 4.1 - Start by describing the top bar composable and the possible actions.
 *     Step 4.2 - Add the top bar to the Joke Screen with navigation to the About screen.
 *     Step 4.3 - Add the top bar to the About Screen with back navigation.
 * Step 5 - Implement the AboutScreen.
 * Step 6 - Refactor the main screen so that it uses the RefreshFab composable.
 *     Step 5.1 - Describe the RefreshFab composable.
 *     Step 5.2 - Use it on the MainScreen.
 * Step 7 - Add a delay to the joke fetching and observe the consequences of a reconfiguration.
 * Step 8 - Lets add a ViewModel to the MainScreen.
 */
class HomeActivity : ComponentActivity() {
    private val vm by viewModels<MainScreenViewModel> {
        MainScreenViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate() called")

        lifecycleScope.launch {
            vm.userInfo.collectLatest {
                if (it is Loaded && it.value.isSuccess) {
                    doNavigation(userInfo = it.getOrNull())
                    vm.resetToIdle()
                }
            }
        }

        setContent {
            val userInfo by vm.userInfo.collectAsState(initial = idle())
            HomeScreen(
                onInfoRequested = { AboutActivity.navigateTo(this) },
                onLeaderboardRequested = { RankingActivity.navigateTo(this) },
                onLobbyRequested = { },
                onProfileRequested = {  }
            )

            userInfo.let {
                if (it is Loaded && it.value.isFailure)
                    ErrorAlert(
                        title = R.string.failed_to_read_preferences_error_dialog_title,
                        message = R.string.failed_to_read_preferences_error_dialog_text,
                        buttonText = R.string.failed_to_read_preferences_error_dialog_ok_button,
                        onDismiss = { vm.resetToIdle() }
                    )
            }
        }
    }

    /**
     * Navigates to the appropriate activity, depending on whether the
     * user information has already been provided or not.
     * @param userInfo the user information.
     */
    private fun doNavigation(userInfo: UserInfo?) {
        if (userInfo == null)
            UserPreferencesActivity.navigateTo(this)
        else
            LobbyActivity.navigateTo(this, userInfo)
    }
}
