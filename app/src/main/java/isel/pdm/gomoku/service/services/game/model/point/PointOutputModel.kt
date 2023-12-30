package pt.isel.daw.gomokuroyale.http.controller.game.model.point

import pt.isel.daw.gomokuroyale.domain.game.PlayerColor
import pt.isel.daw.gomokuroyale.http.controller.game.model.position.PositionOutputModel

/**
 * Represents a point output model.
 *
 * @property position The point's position.
 * @property playerColor The point's player color.
 */
data class PointOutputModel(
    val position: PositionOutputModel,
    val playerColor: PlayerColor
)
