package pt.isel.daw.gomokuroyale.http.controller.game.model.board

import pt.isel.daw.gomokuroyale.domain.game.Board
import pt.isel.daw.gomokuroyale.domain.point.toPlayerColor
import pt.isel.daw.gomokuroyale.http.controller.game.model.point.PointOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.position.PositionOutputModel

/**
 * Represents a board output model.
 *
 * @property moveList The board's move list.
 */
data class BoardOutputModel(
    val moveList: List<PointOutputModel>
) {
    constructor(board: Board) : this(
        board.moveList.map { PointOutputModel(PositionOutputModel(it.position), it.toPlayerColor()) }
    )
}
