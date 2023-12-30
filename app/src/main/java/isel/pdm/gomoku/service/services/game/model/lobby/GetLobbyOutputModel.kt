package pt.isel.daw.gomokuroyale.http.controller.game.model.lobby

import pt.isel.daw.gomokuroyale.domain.game.Lobby
import pt.isel.daw.gomokuroyale.http.controller.game.model.gamerules.GameRulesOutputModel

data class GetLobbyOutputModel(
    val id: Int,
    val gameRuleId: GameRulesOutputModel,
    val userId: Int,
    val createdAt: String
) {
    constructor(lobby: Lobby) : this(
        lobby.id,
        GameRulesOutputModel(lobby.gameRules),
        lobby.userId,
        lobby.createdAt.toString()
    )
}
