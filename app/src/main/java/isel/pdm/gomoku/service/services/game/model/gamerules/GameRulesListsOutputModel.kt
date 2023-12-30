package pt.isel.daw.gomokuroyale.http.controller.game.model.gamerules

import pt.isel.daw.gomokuroyale.domain.game.gamerules.enumclass.OpenRule
import pt.isel.daw.gomokuroyale.domain.game.gamerules.enumclass.Variant
import pt.isel.daw.gomokuroyale.services.utils.GameRulesLists

/**
 * Represents the output model for getting the game rules lists.
 *
 * @property boardSize The board size list.
 * @property openRule The open rule list.
 * @property variant The variant list.
 * @property secondsPerTurn The seconds per-turn list.
 */
data class GameRulesListsOutputModel(
    val boardSize: List<Int>,
    val openRule: List<OpenRule>,
    val variant: List<Variant>,
    val secondsPerTurn: List<Int>
) {
    constructor(gameRulesLists: GameRulesLists) : this(
        gameRulesLists.boardSize,
        gameRulesLists.openRule,
        gameRulesLists.variant,
        gameRulesLists.secondsPerTurn
    )
}
