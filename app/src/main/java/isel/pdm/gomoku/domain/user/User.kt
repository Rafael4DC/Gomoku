package isel.pdm.gomoku.domain.user

/**
 * Represents a user.
 *
 * @property id The user's id.
 * @property username The user's username.
 * @property email The user's email.
 * @property passwordValidation The user's password validation info.
 * @property mmr The user's mmr.
 * @property gamesLost The user's games lost.
 * @property gamesWon The user's games won.
 */
class User(
    val id: Int,
    val username: String="",
    val email: String ="",
    val mmr: Int = 0,
    val gamesLost: Int = 0,
    val gamesWon: Int = 0,
    val tokenRefresh: String,
    val token: String,
)
