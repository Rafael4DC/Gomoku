package isel.pdm.gomoku.domain.game.play

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

const val DEFAULT_BOARD_SIZE = 15
const val DEFAULT_SECONDS_PER_TURN = 30L

data class GameRules(
    val boardSize: Int = DEFAULT_BOARD_SIZE,
    val openRule: OpenRule = OpenRule.DEFAULT,
    val variant: Variant = Variant.STANDARD,
    val secondsPerTurn: Duration = DEFAULT_SECONDS_PER_TURN.seconds
)

enum class OpenRule {
    DEFAULT,
    PRO,
    LONGPRO,
    SWAP,
    SWAP2
}

enum class Variant {
    STANDARD,
    RENJU,
    PENTE,
    GOMOKU
}
