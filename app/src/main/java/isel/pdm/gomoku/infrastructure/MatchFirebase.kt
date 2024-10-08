package isel.pdm.gomoku.infrastructure

import isel.pdm.gomoku.domain.game.lobby.Challenge
import isel.pdm.gomoku.domain.game.lobby.PlayerInfo
import isel.pdm.gomoku.domain.game.lobby.getMarkerFor
import isel.pdm.gomoku.domain.game.play.Board
import isel.pdm.gomoku.domain.game.play.Coordinate
import isel.pdm.gomoku.domain.game.play.Game
import isel.pdm.gomoku.domain.game.play.Marker
import isel.pdm.gomoku.domain.game.play.Match
import isel.pdm.gomoku.domain.game.play.MatchEvent
import isel.pdm.gomoku.domain.game.play.forfeit
import isel.pdm.gomoku.domain.game.play.hasWon
import isel.pdm.gomoku.domain.game.play.isLocalPlayerTurn
import isel.pdm.gomoku.domain.game.play.isTied
import isel.pdm.gomoku.domain.game.play.makeMove
import isel.pdm.gomoku.domain.game.play.startGame
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
//import kotlinx.coroutines.tasks.await

/**
 * Implementation of the Game's match using Firebase's Firestore.
 * @property db The Firestore instance
 */
class MatchFirebase(private val db: Any) : Match {

    /**
     * The current match, if any.
     */
    private lateinit var matchState: MatchState

    override val localPlayerMarker: Marker?
        get() = if(::matchState.isInitialized) matchState.localPlayerMarker else null


    override fun start(localPlayer: PlayerInfo, challenge: Challenge): Flow<MatchEvent> {
        check(::matchState.isInitialized.not()) { "A match instance cannot be reused " }

        matchState = MatchState(
            gameId = challenge.challenger.id.toString(),
            game = startGame(challenge.getMarkerFor(challenge.challenger)),
            localPlayerMarker = challenge.getMarkerFor(localPlayer)
        )

        var gameStateSubscription: Any? = null
        return callbackFlow {
            try {
                if (localPlayer == challenge.challenged)
                    //publishGame(matchState.game, matchState.gameId)

                gameStateSubscription = subscribeGameStateUpdated(
                    localPlayerMarker = matchState.localPlayerMarker,
                    gameId = matchState.gameId,
                    flow = this
                )
            }
            catch (e: Throwable) {
                close(e)
            }

            awaitClose {
                //gameStateSubscription?.remove()
            }
        }
    }

    override suspend fun makeMove(at: Coordinate) {
        check(::matchState.isInitialized) { "The match has not been started" }
        check(matchState.game.isLocalPlayerTurn(matchState.localPlayerMarker)) {
            "It's not the local player turn"
        }

        val updatedGame = matchState.game.makeMove(at)
        //updateGame(updatedGame, matchState.gameId)
    }

    override suspend fun forfeit() {
        check(::matchState.isInitialized) { "The match has not been started" }
        checkNotNull(matchState.game as? Game.Ongoing, lazyMessage = { "The game is not ongoing" })

       /* db.collection(ONGOING)
            .document(matchState.gameId)
            .update(FORFEIT_FIELD, matchState.localPlayerMarker)
            .await()*/

        matchState = matchState.copy(game = matchState.game.forfeit(matchState.localPlayerMarker))
    }

    override suspend fun close() {
        if (::matchState.isInitialized) {
            if (matchState.game is Game.Ongoing) forfeit()

           /* db.collection(ONGOING)
                .document(matchState.gameId)
                .delete()
                .await()*/
        }
    }

    private fun subscribeGameStateUpdated(
        localPlayerMarker: Marker,
        gameId: String,
        flow: ProducerScope<MatchEvent>
    ):Any = TODO()
        /*db.collection(ONGOING)
            .document(gameId)
            .addSnapshotListener { snapshot, error ->
                when {
                    error != null -> flow.close(error)
                    snapshot != null -> {
                        val updatedGame = snapshot.toGameOrNull()
                        if (updatedGame == null) flow.close(IllegalStateException("Game not found"))
                        else {
                            matchState = matchState.copy(game = updatedGame)
                            when (updatedGame) {
                                is Game.HasWinner -> flow.trySend(MatchEvent.Ended(updatedGame, updatedGame.winner))
                                is Game.Draw -> flow.trySend(MatchEvent.Ended(updatedGame, winner = null))
                                is Game.Ongoing ->
                                    if (updatedGame.board.isEmpty()) flow.trySend(MatchEvent.Started(updatedGame))
                                    else flow.trySend(MatchEvent.MoveMade(updatedGame))
                            }
                        }
                    }
                    else -> flow.close()
                }
            }*/

    /*private suspend fun publishGame(game: Game, gameId: String) {
        db.collection(ONGOING)
            .document(gameId)
            .set(game.toDocumentContent())
            .await()
    }

    private suspend fun updateGame(game: Game, gameId: String) {
        db.collection(ONGOING)
            .document(gameId)
            .update(game.toDocumentContent())
            .await()
    }*/
}

/**
 * Represents the current state of a match.
 * @property gameId The game identifier
 * @property game The game state
 * @property localPlayerMarker The local player marker
 */
private data class MatchState(val gameId: String, val game: Game, val localPlayerMarker: Marker)

/**
 * The following declarations are used to convert the domain objects to/from the Firestore DB documents.
 * They should only be accessible from this file, but are declared as public to allow testing
 * In a multi-module project we would use the "internal" modifier instead and declare the tests in the same module
 */

/**
 * The name of the collection for ongoing games
 */
const val ONGOING = "ongoing"

/**
 * The name of the field containing the game's turn
 */
const val TURN_FIELD = "turn"

/**
 * The name of the field containing the game's board
 */
const val BOARD_FIELD = "board"

/**
 * The name of the field containing the marker of the player that forfeited the game
 * (if any)
 */
const val FORFEIT_FIELD = "forfeit"

/**
 * [Board] extension function used to convert an instance to a map of key-value
 * pairs containing the object's properties
 */
fun Game.toDocumentContent(): Map<String, String> = buildMap {
    put(isel.pdm.gomoku.infrastructure.BOARD_FIELD, board.flatten().joinToString(separator = "") {
        when (it) {
            isel.pdm.gomoku.domain.game.play.Marker.CROSS -> "X"
            isel.pdm.gomoku.domain.game.play.Marker.CIRCLE -> "O"
            null -> "-"
        }
    })

    when (this@toDocumentContent) {
        is Game.Ongoing -> put(isel.pdm.gomoku.infrastructure.TURN_FIELD, turn.name)
        is Game.HasWinner ->
            if (wasForfeited) put(isel.pdm.gomoku.infrastructure.FORFEIT_FIELD, winner.other.name)
        else -> { }
    }
}

/**
 * Extension function to convert documents stored in the Firestore DB to Game instances.
 */
/*
private fun DocumentSnapshot.toGameOrNull(): Game? = data?.let {

    val board = Board.fromMovesList((it[BOARD_FIELD] as String).toMovesList())
    val turn = Marker.valueOf(it[TURN_FIELD] as String)
    val forfeit = (it[FORFEIT_FIELD] as String?)?.let { field -> Marker.valueOf(field) }

    when {
        board.isTied() -> Game.Draw(board)
        forfeit != null -> Game.HasWinner(winner = forfeit.other, board = board, wasForfeited = true)
        board.hasWon(turn.other) -> Game.HasWinner(winner = turn.other, board = board)
        board.hasWon(turn) -> Game.HasWinner(winner = turn, board = board)
        else -> Game.Ongoing(turn, board)
    }
}
*/

/**
 * Converts this string to a list of moves in the board
 */
private fun String.toMovesList(): List<Marker?> = map {
    when (it) {
        'X' -> Marker.CROSS
        'O' -> Marker.CIRCLE
        else -> null
    }
}
