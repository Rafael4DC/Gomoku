package isel.pdm.gomoku.domain.game.lobby

import kotlinx.coroutines.flow.Flow

/**
 * Sum type used to describe events occurring while the player is in the lobby.
 */
sealed interface LobbyEvent {
    /**
     * Signals that the roster of players in the lobby has changed.
     * @property players the new roster of players
     */
    data class RosterUpdated(val players: List<PlayerInfo>) : LobbyEvent

    /**
     * Signals that a challenge was received by the local player.
     * @property challenge the challenge information
     */
    data class ChallengeReceived(val challenge: Challenge) : LobbyEvent
}

/**
 * Exception thrown when the lobby is unreachable.
 */
class UnreachableLobbyException : Exception()

/**
 * Abstraction that characterizes the game's lobby.
 */
interface Lobby {
    /**
     * Enters the lobby and subscribes to events that occur in the lobby.
     * It cannot be entered again until left.
     * @return the flow of lobby events
     * @throws IllegalStateException    if the lobby was already entered
     * @throws UnreachableLobbyException if the lobby is unreachable
     */
    suspend fun enter(localPlayer: PlayerInfo): Flow<LobbyEvent>

    /**
     * Issues a challenge to the given player. In order to issue a challenge,
     * the player must have previously entered the lobby. The player that issues
     * the challenge exits the lobby.
     * @param   [to]  the player to which the challenge will be issued
     * @return  the challenge information
     * @throws IllegalStateException    if the lobby was not yet entered
     */
    suspend fun issueChallenge(to: PlayerInfo): Challenge

    /**
     * Leaves the lobby, ending the flow of lobby events.
     * @throws IllegalStateException    if the lobby was not yet entered
     * @throws UnreachableLobbyException if the lobby is unreachable
     */
    suspend fun leave()
}
