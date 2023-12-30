package pt.isel.daw.gomokuroyale.http.controller.game.model.player

import pt.isel.daw.gomokuroyale.domain.game.Player

/**
 * Represents a player output model.
 *
 * @property id The player's id.
 */
data class PlayerOutputModel(
    val id: Int
) {
    constructor(player: Player) : this(
        player.id
    )
}
