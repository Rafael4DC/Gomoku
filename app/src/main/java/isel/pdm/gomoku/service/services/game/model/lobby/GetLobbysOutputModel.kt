package pt.isel.daw.gomokuroyale.http.controller.game.model.lobby

import pt.isel.daw.gomokuroyale.domain.game.Lobby

data class GetLobbysOutputModel(
    val lobbys: List<GetLobbyOutputModel>
)

fun createGetLobbysOutputModel(lobbys: List<Lobby>) = GetLobbysOutputModel(lobbys.map { GetLobbyOutputModel(it) })
