package pt.isel.daw.gomokuroyale.http.controller.game.model.getgame

import pt.isel.daw.gomokuroyale.domain.game.Game

data class GetGamesOutputModel(
    val games: List<GetGameOutputModel>
)

fun createGetGamesOutputModel(games: List<Game>) = GetGamesOutputModel(games.map { GetGameOutputModel(it) })
