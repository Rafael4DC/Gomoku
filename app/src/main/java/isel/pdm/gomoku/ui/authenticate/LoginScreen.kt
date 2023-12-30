package isel.pdm.gomoku.ui.authenticate

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.gomoku.ui.common.NavigationHandlers
import isel.pdm.gomoku.ui.common.TopBar
import isel.pdm.gomoku.ui.common.theme.Gomoku9Theme
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostLoginModel
import pt.isel.daw.gomokuroyale.http.controller.user.model.create.UserCreateInputModel
import kotlin.math.min

/**
 * The screen that allows the user to specify its username and moto, to be
 * used in the lobby and during the game.
 * @param userInfo The user info to be displayed in the screen, if any.
 * @param onLoginRequest The callback to be invoked when the user requests to save the user info.
 * @param onNavigateBackRequested The callback to be invoked when the user requests to navigate back.
 */
@Composable
fun LoginScreen(
    onLoginRequest: (UserPostLoginModel) -> Unit,
    onCreateRequested: (UserCreateInputModel) -> Unit,
    onNavigateBackRequested: () -> Unit,
    signUp :Boolean = false
) {
    Gomoku9Theme {
        var isSignupMode by rememberSaveable { mutableStateOf(signUp) }
        if (isSignupMode) {
            SignupModeScreen(
                onCreateRequested = onCreateRequested,
                onNavigateBackRequested = { isSignupMode = false }
            )
        }
        else {
            LoginModeScreen(
                onLoginRequest = onLoginRequest,
                onNavigateBackRequested = onNavigateBackRequested ,
                onSignUpRequested = { isSignupMode = true }
            )
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun SignupModeScreen(
    onCreateRequested: (UserCreateInputModel) -> Unit,
    onNavigateBackRequested: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(NavigationHandlers(onNavigateBackRequested)) },
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Button(
                onClick = { onCreateRequested(UserCreateInputModel(username, password, email)) },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Sign Up")
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun LoginModeScreen(
    onLoginRequest: (UserPostLoginModel) -> Unit,
    onSignUpRequested: () -> Unit,
    onNavigateBackRequested: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(NavigationHandlers(onSignUpRequested)) },
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            Button(
                onClick = { onLoginRequest(UserPostLoginModel(username, password)) },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Login")
            }
            Button(
                onClick = { onNavigateBackRequested() },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("back")
            }
        }
    }
}


@Composable
fun SwitchToEditModeFab(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .defaultMinSize(minWidth = 72.dp, minHeight = 72.dp)
    ){
        Icon(Icons.Default.Edit, contentDescription = "")
    }
}

@Composable
private fun SaveFab(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = CircleShape,
        modifier = Modifier
            .defaultMinSize(minWidth = 72.dp, minHeight = 72.dp)
    ){
        Icon(Icons.Default.SaveAlt, contentDescription = "")
    }
}

private const val MAX_INPUT_SIZE = 50
private fun ensureInputBounds(input: String) =
    input.also {
        it.substring(range = 0 until min(it.length, MAX_INPUT_SIZE))
    }
