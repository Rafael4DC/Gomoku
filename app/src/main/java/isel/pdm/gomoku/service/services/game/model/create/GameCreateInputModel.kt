package pt.isel.daw.gomokuroyale.http.controller.game.model.create

import pt.isel.daw.gomokuroyale.domain.game.gamerules.enumclass.OpenRule
import pt.isel.daw.gomokuroyale.domain.game.gamerules.enumclass.Variant
import pt.isel.daw.gomokuroyale.repository.jdbi.mappers.createGameRules

/**
 * Represents the input model for creating a game.
 *
 * @property boardSize The board size.
 * @property openRule The open rule.
 * @property variant The variant.
 * @property secondsPerTurn The seconds per turn.
 */
data class GameCreateInputModel(
    val boardSize: Int,
    val openRule: String,
    val variant: String,
    val secondsPerTurn: Int
)

fun createGameRulesFromModel(gameRulesModel: GameCreateInputModel) =
    createGameRules(
        gameRulesModel.boardSize,
        OpenRule.valueOf(gameRulesModel.openRule),
        Variant.valueOf(gameRulesModel.variant),
        gameRulesModel.secondsPerTurn
    )
