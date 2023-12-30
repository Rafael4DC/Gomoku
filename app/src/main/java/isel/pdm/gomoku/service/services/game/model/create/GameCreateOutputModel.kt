package pt.isel.daw.gomokuroyale.http.controller.game.model.create

/**
 * Represents the output model for creating a game or lobby.
 *
 * @property gameId The game id.
 * @property lobbyId The lobby id.
 */
data class GameCreateOutputModel(
    val gameId: Int?,
    val lobbyId: Int?
)
