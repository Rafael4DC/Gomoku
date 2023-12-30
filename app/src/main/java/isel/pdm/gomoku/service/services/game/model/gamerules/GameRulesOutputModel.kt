package pt.isel.daw.gomokuroyale.http.controller.game.model.gamerules

import pt.isel.daw.gomokuroyale.domain.game.gamerules.GameRules
import pt.isel.daw.gomokuroyale.domain.game.gamerules.enumclass.OpenRule
import pt.isel.daw.gomokuroyale.domain.game.gamerules.enumclass.Variant

/**
 * Represents a game rules output model.
 *
 * @property boardSize The board's size.
 * @property openRule The board's open rule.
 * @property variant The board's variant.
 * @property secondsPerTurn The board's seconds per turn.
 */
data class GameRulesOutputModel(
    val boardSize: Int,
    val openRule: OpenRule,
    val variant: Variant,
    val secondsPerTurn: Int
) {
    constructor(gameRules: GameRules) : this(
        gameRules.boardSize,
        gameRules.openRule,
        gameRules.variant,
        gameRules.secondsPerTurn
    )
}
