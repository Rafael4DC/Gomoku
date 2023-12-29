package isel.game.gomoku.ui.screens.about

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import isel.game.gomoku.ui.screens.about.components.Author
import isel.game.gomoku.ui.screens.about.components.AuthorInfo
import isel.pdm.gomoku.ui.screens.shared.components.NavigationHandlers
import isel.pdm.gomoku.ui.screens.shared.components.TopBar
import isel.pdm.gomoku.ui.common.theme.Gomoku9Theme



/**
 * Tags used to identify the components of the AboutScreen in automated tests
 */
const val AboutScreenTestTag = "AboutScreenTestTag"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onSendEmailRequested: (String) -> Unit = { },
    onOpenUrlRequested: (Uri) -> Unit,
    onBackRequest: (() -> Unit)?,
    authors: List<AuthorInfo>
) {
    Gomoku9Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize().testTag(AboutScreenTestTag),
            topBar = { TopBar(NavigationHandlers(onBackRequested = onBackRequest)) },
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
            ) {
                for(author in authors){
                    Author(author,onSendEmailRequested = onSendEmailRequested,onOpenUrlRequested = onOpenUrlRequested)
                }
            }
        }
    }
}



/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InfoScreenPreview() {
    AboutScreen(authors = authorInfo)
}*/
