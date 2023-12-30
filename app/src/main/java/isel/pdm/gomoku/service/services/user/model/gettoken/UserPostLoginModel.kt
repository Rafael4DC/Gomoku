package isel.pdm.gomoku.service.services.user.model.gettoken

/**
 * Represents the input model for a user login.
 *
 * @property username The username.
 * @property password The password.
 */
data class UserPostLoginModel(
    val username: String,
    val password: String
)
