package isel.pdm.gomoku.service.services.game

import pt.isel.daw.gomokuroyale.http.controller.game.model.create.GameCreateInputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.create.GameCreateOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.gamerules.GameRulesListsOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.getPlayer.GetPlayerOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.getgame.GetGameOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.getgame.GetGamesOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.getgame.GetIdGameOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.lobby.GetLobbysOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.placestone.PlaceStoneInputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.placestone.PlaceStoneOutputModel
import pt.isel.daw.gomokuroyale.http.controller.game.model.remove.RemoveOutputModel

interface GameServiceInterface {

    suspend fun startNewGame(gameRules: GameCreateInputModel): GameCreateOutputModel

    suspend fun getGameById(gameId: Int): GetGameOutputModel

    suspend fun getGameByGameRulesAndUserId(gameRules: Int): GetIdGameOutputModel

    suspend fun getUserGames(): GetGamesOutputModel

    suspend fun getUserLobbys(): GetLobbysOutputModel

    suspend fun getPlayerGameTurn(gameId: Int): GetPlayerOutputModel

    suspend fun getAllGameRules(): GameRulesListsOutputModel

    suspend fun placeStone(gameId: Int, stone: PlaceStoneInputModel): PlaceStoneOutputModel

    suspend fun removeFromLobby(lobbyId: Int): RemoveOutputModel

    suspend fun removeGame(gameId: Int): RemoveOutputModel
}