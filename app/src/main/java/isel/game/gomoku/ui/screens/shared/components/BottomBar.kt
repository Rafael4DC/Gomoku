package isel.game.gomoku.ui.screens.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.game.gomoku.R

/**
 * Used to aggregate [TopBar] navigation handlers. If a handler is null, the corresponding
 * navigation element is not displayed.
 *
 * @property onBackRequested the callback invoked when the user clicks the back button.
 * @property onInfoRequested the callback invoked when the user clicks the info button.
 */
data class NavigationHandlers(
    val onBackRequested: (() -> Unit)? = null,
    val onInfoRequested: (() -> Unit)? = null,
)

// Test tags for the TopBar navigation elements
const val NavigateBackTestTag = "NavigateBack"
const val NavigateToInfoTestTag = "NavigateToInfo"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navigation: NavigationHandlers = NavigationHandlers()) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (navigation.onBackRequested != null) {
                IconButton(
                    onClick = navigation.onBackRequested,
                    modifier = Modifier.testTag(NavigateBackTestTag)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.top_bar_go_back)
                    )
                }
            }
        },
        actions = {
            if (navigation.onInfoRequested != null) {
                IconButton(
                    onClick = navigation.onInfoRequested,
                    modifier = Modifier.testTag(NavigateToInfoTestTag)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id = R.string.top_bar_navigate_to_about)
                    )
                }
            }
        }
    )}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(function: List<BottomBarOption>) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        function.forEach {
            this.NavigationBarItem(
                selected = false,
                onClick = it.onClick,
                icon = {
                    Icon(imageVector = it.icon,
                        contentDescription =it.name,
                        modifier= Modifier.sizeIn(50.dp, 50.dp, 50.dp, 50.dp)
                    )
                }
            )
        }
    }
}
data class BottomBarOption(val name: String, val icon: ImageVector, val onClick: () -> Unit)




/*
@Preview
@Composable
private fun TopBarPreviewInfoAndHistory() {
    Gomoku9Theme {
        TopBar(
            navigation = NavigationHandlers(onInfoRequested = { })
        )
    }
}

@Preview
@Composable
private fun TopBarPreviewBackAndInfo() {
    Gomoku9Theme {
        TopBar(navigation = NavigationHandlers(onBackRequested = { }, onInfoRequested = { }))
    }
}

@Preview
@Composable
private fun TopBarPreviewBack() {
    Gomoku9Theme {
        TopBar(navigation = NavigationHandlers(onBackRequested = { }))
    }
}*/
