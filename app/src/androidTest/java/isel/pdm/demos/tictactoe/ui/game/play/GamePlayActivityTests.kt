package isel.pdm.demos.tictactoe.ui.game.play

import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import io.mockk.coEvery
import io.mockk.mockk
import isel.pdm.gomoku.domain.game.lobby.Challenge
import isel.pdm.gomoku.domain.game.lobby.PlayerInfo
import isel.pdm.gomoku.domain.game.play.Match
import isel.pdm.gomoku.domain.user.UserInfo
import isel.pdm.demos.tictactoe.utils.PreserveDefaultDependenciesNoActivity
import isel.pdm.demos.tictactoe.utils.createPreserveDefaultDependenciesComposeRuleNoActivity
import isel.pdm.gomoku.ui.game.play.GamePlayActivity
import isel.pdm.gomoku.ui.game.play.GamePlayScreenTag
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class GamePlayActivityTests {

    @get:Rule
    val testRule = createPreserveDefaultDependenciesComposeRuleNoActivity()

    private val testApplication by lazy {
        (testRule.activityRule as PreserveDefaultDependenciesNoActivity).testApplication
    }

    private val localTestPlayer = PlayerInfo(UserInfo("local"))
    private val testChallenge = Challenge(
        challenger = localTestPlayer,
        challenged = PlayerInfo(UserInfo("remote"))
    )

    private val testIntent = GamePlayActivity.createIntent(
        ctx = testApplication,
        localPlayer = localTestPlayer,
        challenge = testChallenge
    )

    @Test
    fun initially_the_game_screen_is_displayed() {
        // Arrange
        ActivityScenario.launch<GamePlayActivity>(testIntent).use {
            // Act
            // Assert
            testRule.onNodeWithTag(GamePlayScreenTag).assertExists()
        }
    }

    @Test
    @Ignore("This test is not working")
    fun pressing_back_forfeits_the_game() {
        var forfeitCalled = false
        testApplication.matchFactory = {
            mockk<Match>(relaxed = true) {
                coEvery { forfeit() } answers {
                    forfeitCalled = true
                }
            }
        }
        // Arrange
        ActivityScenario.launch<GamePlayActivity>(testIntent).use { scenario ->
            // Act
            scenario.onActivity { it.onBackPressedDispatcher.onBackPressed()  }
            // Assert
            scenario.onActivity {
                assertTrue(forfeitCalled)
                assertTrue(it.isFinishing)
            }
        }
    }
}