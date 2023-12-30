import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import isel.pdm.gomoku.DependenciesContainer
import isel.pdm.gomoku.domain.IOState
import isel.pdm.gomoku.ui.authenticate.LoginScreen
import isel.pdm.gomoku.ui.profile.ProfileActivity
import kotlinx.coroutines.launch
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostTokenOutputModel

class LoginActivity : ComponentActivity() {
    companion object {
        fun navigateTo(ctx: Context) {
            val intent = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    private val vm by viewModels<LoginViewModel> {
        LoginViewModel.factory(
            (application as DependenciesContainer).userService,
            (application as DependenciesContainer).dataStore
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val loginState by vm.loginState.collectAsState(initial = IOState.Idle)
            LoginScreen(
                onLoginRequest = { userPostLoginModel ->
                    vm.login(userPostLoginModel.username, userPostLoginModel.password)
                },
                onCreateRequested = { userCreateInputModel ->
                    vm.createUser(userCreateInputModel.username, userCreateInputModel.password, userCreateInputModel.email)
                },
                onNavigateBackRequested = { finish() },
                signUp = false
            )
        }

        lifecycleScope.launch {
            vm.loginState.collect { state ->
                if (state is IOState.Saved && state.value.isSuccess) {
                    finish()
                }
            }
        }
    }

    private fun navigateToProfileWithUserInfo(userInfo: UserPostTokenOutputModel) {
        ProfileActivity.navigateTo(this, userInfo)
    }
}
