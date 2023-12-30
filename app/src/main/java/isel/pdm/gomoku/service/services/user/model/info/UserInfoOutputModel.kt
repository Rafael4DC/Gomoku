package isel.pdm.gomoku.service.services.user.model.info

import isel.pdm.gomoku.domain.user.User


/**
 * Represents the output model for user info.
 *
 * @property id The user's id.
 * @property username The user's username.
 */
class UserInfoOutputModel(
    val id: Int,
    val username: String
) {
    constructor(user: User) : this(
        user.id,
        user.username
    )
}
