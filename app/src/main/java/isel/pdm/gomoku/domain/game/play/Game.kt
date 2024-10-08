package isel.pdm.gomoku.domain.game.play

/**
 * Sum type for the Tic-Tac-Toe game state.
 */
sealed interface Game {

    /**
     * The game board, which is common to all game states.
     */
    val board: Board

    /**
     * Represents ongoing games.
     * @property turn the marker of the player to move next
     * @property board the game board
     */
    data class Ongoing(val turn: Marker, override val board: Board) : Game

    /**
     * Represents finished games.
     */
    sealed interface Finished : Game

    /**
     * Represents finished games with no winner.
     * @property board the game board
     */
    data class Draw(override val board: Board) : Finished

    /**
     * Represents finished games with a winner. Note that the winner can be declared
     * even if the game is not finished, if the opponent forfeits.
     * @property winner the marker of the winner
     * @property board the game board
     * @property wasForfeited true if the game was forfeited, false otherwise
     */
    data class HasWinner(
        val winner: Marker,
        override val board: Board,
        val wasForfeited: Boolean = false
    ) : Finished
}

/**
 * Starts a new game.
 * @param turn the marker of the player to move first
 */
fun startGame(turn: Marker = Marker.firstToMove) = Game.Ongoing(turn, Board())

/**
 * Makes a move on this [Game], returning a new instance that reflects the new
 * game state.
 * @param at the coordinates where the move is to be made.
 * @throws IllegalStateException if the game is not ongoing
 * @throws IllegalArgumentException if the move is illegal
 */
fun Game.makeMove(at: Coordinate): Game {
    check(this is Game.Ongoing)
    val game = copy(
        turn = turn.other,
        board = board.placeMarker(turn, at)
    )

    return when {
        game.board.hasWon(turn) -> Game.HasWinner(turn, game.board)
        game.board.isTied() -> Game.Draw(game.board)
        else -> game
    }
}

/**
 * Forfeits the current game, returning a new instance that reflects the new
 * game state.
 * @param forfeitMarker the marker of the player who forfeited the game, or null
 * if the forfeit is to be attributed to the current player.
 */
fun Game.forfeit(forfeitMarker: Marker? = null): Game.HasWinner {
    check(this is Game.Ongoing)
    return Game.HasWinner(
        winner = (forfeitMarker ?: turn).other,
        board = board,
        wasForfeited = true
    )
}

/**
 * Extension function that checks whether this board represents a tied game or not
 * @return true if the board is a tied game, false otherwise
 */
fun Board.isTied(): Boolean =
    flatten().all { it != null } && !hasWon(Marker.CIRCLE) && !hasWon(Marker.CROSS)

/**
 * Extension function that checks whether the given marker has won the game
 * @return true if the player with the given marker has won, false otherwise
 */
fun Board.hasWon(marker: Marker): Boolean =
    getRows().any { row -> row.all { it == marker } } ||
        getColumns().any { column -> column.all { it == marker } } ||
            getDiagonals().toList().any { diagonal -> diagonal.all { it == marker } }


/**
 * Utility function that checks whether it's the local player turn or not.
 */
fun Game.isLocalPlayerTurn(localPlayerMarker: Marker): Boolean =
    this is Game.Ongoing && turn == localPlayerMarker