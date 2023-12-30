package pt.isel.daw.gomokuroyale.http.controller.game.model.position

import pt.isel.daw.gomokuroyale.domain.point.Position

/**
 * Represents a position output model.
 *
 * @property x The position's x.
 * @property y The position's y.
 */
data class PositionOutputModel(
    val x: Int,
    val y: Int
) {
    constructor(position: Position) : this(
        position.x,
        position.y
    )
}
