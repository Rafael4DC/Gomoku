package pt.isel.daw.gomokuroyale.http.controller.user.model.getuser

import isel.pdm.gomoku.domain.user.User

/**
 * Represents a get user output model.
 *
 * @property id The user's id.
 * @property username The user's username.
 * @property email The user's email.
 * @property mmr The user's mmr.
 * @property gamesLost The user's games lost.
 * @property gamesWon The user's games won.
 */
data class GetUserOutputModel(
    val id: Int,
    val username: String,
    val email: String,
    val mmr: Int,
    val gamesLost: Int,
    val gamesWon: Int
) {
    constructor(user: User) : this(
        user.id,
        user.username,
        user.email,
        user.mmr,
        user.gamesLost,
        user.gamesWon
    )
}
