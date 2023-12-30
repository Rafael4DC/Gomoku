package pt.isel.daw.gomokuroyale.http.controller.game.model.getPlayer

import pt.isel.daw.gomokuroyale.domain.game.Game
import pt.isel.daw.gomokuroyale.services.GameTurnAndStatus

/**
 * Represents the output model for getting a player.
 *
 * @property turn The turn.
 * @property status The game status.
 */
data class GetPlayerOutputModel(
    val turn: Int,
    val status: Game.GameState
) {
    constructor(game: GameTurnAndStatus) : this(
        game.turn,
        game.status
    )
}
