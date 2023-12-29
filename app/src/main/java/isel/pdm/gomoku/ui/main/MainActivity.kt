package isel.pdm.gomoku.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import isel.pdm.gomoku.DependenciesContainer
import isel.pdm.gomoku.R
import isel.pdm.gomoku.domain.IOState.Idle
import isel.pdm.gomoku.domain.IOState.Loaded
import isel.pdm.gomoku.domain.getOrNull
import isel.pdm.gomoku.domain.idle
import isel.pdm.gomoku.domain.user.UserInfo
import isel.pdm.gomoku.ui.common.ErrorAlert
import isel.pdm.gomoku.ui.game.lobby.LobbyActivity
import isel.pdm.gomoku.ui.preferences.UserPreferencesActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * The application's main activity, that hosts the main screen.
 */
class MainActivity : ComponentActivity() {

    private val vm by viewModels<MainScreenViewModel> {
        MainScreenViewModel.factory((application as DependenciesContainer).userInfoRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            vm.userInfo.collectLatest {
                if (it is Loaded && it.value.isSuccess) {
                    //doNavigation(userInfo = it.getOrNull())
                    vm.resetToIdle()
                }
            }
        }

        setContent {
            val userInfo by vm.userInfo.collectAsState(initial = idle())
            MainScreen(
                onPlayEnabled = userInfo is Idle,
                onPlayRequested = { vm.fetchUserInfo() }
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
