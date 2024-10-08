package isel.pdm.gomoku.ui.game.lobby

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.gomoku.DependenciesContainer
import isel.pdm.gomoku.R
import isel.pdm.gomoku.domain.user.User
import isel.pdm.gomoku.ui.common.ErrorAlert
import isel.pdm.gomoku.ui.common.UserInfoExtra
import isel.pdm.gomoku.ui.common.toUserInfo
import isel.pdm.gomoku.ui.game.play.GamePlayActivity
import isel.pdm.gomoku.ui.profile.ProfileActivity
import kotlinx.coroutines.launch

/**
 * The name of the extra that contains the user information.
 */
private const val USER_INFO_EXTRA = "UserInfo"

/**
 * The activity that hosts the screen that allows the user to select the opponent and start the game.
 */
class LobbyActivity : ComponentActivity() {

    companion object {
        /**
         * Navigates to the [LobbyActivity] activity.
         * @param ctx the context to be used.
         * @param userInfo the user information to be passed onto the activity.
         */
        fun navigateTo(ctx: Context, userInfo: User) {
            ctx.startActivity(createIntent(ctx, userInfo))
        }

        /**
         * Builds the intent used to navigate to the [LobbyActivity] activity.
         * @param ctx the context to be used.
         * @param userInfo the user information to be passed onto the activity.
         */
        fun createIntent(ctx: Context, userInfo: User): Intent {
            val intent = Intent(ctx, LobbyActivity::class.java)
            intent.putExtra(USER_INFO_EXTRA, UserInfoExtra(userInfo))
            return intent
        }
    }

    private val vm by viewModels<LobbyScreenViewModel> {
        LobbyScreenViewModel.factory((application as DependenciesContainer).lobby, userInfoExtra)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.enterLobby()
                try {
                    vm.screenState.collect { state ->
                        if (state is LobbyScreenState.SentChallenge)
                            GamePlayActivity.navigateTo(this@LobbyActivity, state.localPlayer, state.challenge)
                        if (state is LobbyScreenState.IncomingChallenge)
                            GamePlayActivity.navigateTo(this@LobbyActivity, state.localPlayer, state.challenge)
                    }
                }
                finally {
                    vm.leaveLobby()
                }
            }
        }

        setContent {
            val currentState = vm.screenState.collectAsState(LobbyScreenState.EnteringLobby).value
            val playersInLobby =
                if (currentState is LobbyScreenState.InsideLobby) currentState.otherPlayers
                else emptyList()

            LobbyScreen(
                playersInLobby = playersInLobby,
                onPlayerSelected = { vm.sendChallenge(it) },
                onNavigateBackRequested = { finish() },
                onNavigateToPreferencesRequested = { ProfileActivity.navigateTo(this, userInfoExtra) }
            )

            currentState.let {
                if (it is LobbyScreenState.LobbyAccessError)
                    ErrorAlert(
                        title = R.string.failed_to_enter_lobby_dialog_title,
                        message = R.string.failed_to_enter_lobby_dialog_text,
                        buttonText = R.string.failed_to_enter_lobby_dialog_ok_button,
                        onDismiss = { finish() }
                    )
            }
        }
    }

    /**
     * Helper method to get the user info extra from the intent.
     */
    private val userInfoExtra: User by lazy {
        checkNotNull(getUserInfoExtra()).toUserInfo()
    }

    @Suppress("DEPRECATION")
    private fun getUserInfoExtra(): UserInfoExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(USER_INFO_EXTRA, UserInfoExtra::class.java)
        else
            intent.getParcelableExtra(USER_INFO_EXTRA)
}
