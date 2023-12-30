package isel.pdm.gomoku.service.services.user.model.leaderboard

import isel.pdm.gomoku.domain.user.User


/**
 * Represents the output model for getting the leaderboard.
 *
 * @property rankers The rankers.
 */
data class LeaderboardOutput(
    val rankers: List<Ranker>
)

data class LeaderboardOutputSize(
    val count: Int
)

/**
 * Creates a [LeaderboardOutput] from a list of [User].
 *
 * @param users The users.
 * @return The [LeaderboardOutput].
 */
fun LeaderboardOutput(users: List<User>): LeaderboardOutput {
    return LeaderboardOutput(
        users.map {
            Ranker(it.username, it.mmr, it.gamesWon + it.gamesLost)
        }
    )
}

/**
 * Represents a ranker.
 *
 * @property username The username.
 * @property mmr The mmr.
 * @property totalGames The total games.
 */
data class Ranker(
    val username: String,
    val mmr: Int,
    val totalGames: Int
)
