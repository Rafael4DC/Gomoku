package isel.game.gomoku.ui.screens.daily

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import isel.game.gomoku.service.game.FakeJokesService
import isel.game.gomoku.ui.screens.about.AboutActivity
import isel.game.gomoku.ui.screens.shared.components.BottomBar
import isel.game.gomoku.ui.screens.shared.components.BottomBarOption
import kotlin.system.exitProcess

const val TAG = "JOKES_TAG"

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
class MainActivity : ComponentActivity() {

    private lateinit var bottomBarOption: List<BottomBarOption>
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate() called")
        setContent {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(JokeScreenTestTag),
                bottomBar = { BottomBar(bottomBarOption) },
            ){it->
                val a = it
                JokeScreen(
                    service = FakeJokesService(),
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        createOptionsMenu()
        Log.v(TAG, "onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy() called")
    }

    fun createOptionsMenu() {
        bottomBarOption = listOf(
            BottomBarOption(
                name = "Home",
                icon = Icons.Default.Home
            ) { finish() },
            BottomBarOption(
                name = "About",
                icon = Icons.Default.Info
            ) { AboutActivity.navigateTo(this) },
            BottomBarOption(
                name = "Exit",
                icon = Icons.Default.Close
            ) { exitProcess(1) },
        )
    }
}
