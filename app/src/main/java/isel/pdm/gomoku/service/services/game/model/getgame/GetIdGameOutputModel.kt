package pt.isel.daw.gomokuroyale.http.controller.game.model.getgame

import pt.isel.daw.gomokuroyale.domain.game.Game

data class GetIdGameOutputModel(
    val gameId: Int
) {
    constructor(game: Game) : this(
        game.id
    )
}
