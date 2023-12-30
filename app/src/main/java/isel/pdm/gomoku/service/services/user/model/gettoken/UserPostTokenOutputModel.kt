package isel.pdm.gomoku.service.services.user.model.gettoken


/**
 * Represents the output model for a user token.D
 *
 * @param token The user's token.
 */

data class UserPostTokenOutputModel(
    val token: String,
    val tokenRefresh: String,
    val userId: Int,
    val expiresAt: Long = 0L
)
