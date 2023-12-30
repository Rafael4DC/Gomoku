package pt.isel.daw.gomokuroyale.http.controller.game.model.getgame

import pt.isel.daw.gomokuroyale.domain.game.Game
import pt.isel.daw.gomokuroyale.domain.game.Outcome
import pt.isel.daw.gomokuroyale.http.controller.game.model.board.BoardOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.gamerules.GameRulesOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.player.PlayerOutputModel

/**
 * Represents the output model for getting a game.
 *
 * @property id The game id.
 * @property gameRules The game rules.
 * @property blackPlayer The black player.
 * @property whitePlayer The white player.
 * @property state The game state.
 * @property board The board.
 * @property outcome The outcome.
 */
data class GetGameOutputModel(
    val id: Int,
    val gameRules: GameRulesOutputModel,
    val blackPlayer: PlayerOutputModel,
    val whitePlayer: PlayerOutputModel,
    val state: Game.GameState,
    val board: BoardOutputModel,
    val outcome: Outcome
) {
    constructor(game: Game) : this(
        game.id,
        GameRulesOutputModel(game.gameRules),
        PlayerOutputModel(game.blackPlayer),
        PlayerOutputModel(game.whitePlayer),
        game.state,
        BoardOutputModel(game.board),
        game.outcome
    )
}
