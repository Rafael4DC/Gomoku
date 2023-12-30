package pt.isel.daw.gomokuroyale.http.controller.user.model.create

/**
 * Represents the input model for creating a user.
 *
 * @property username The username.
 * @property email The email.
 * @property password The password.
 */
data class UserCreateInputModel(
    val username: String,
    val email: String,
    val password: String
)
