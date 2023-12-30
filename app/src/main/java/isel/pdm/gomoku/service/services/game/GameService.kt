package isel.pdm.gomoku.service.services.game

import com.google.gson.Gson
import isel.pdm.gomoku.service.http.Requests
import okhttp3.OkHttpClient
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

class GameService(
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
): GameServiceInterface, Requests(jsonEncoder, httpClient) {

    override suspend fun startNewGame(gameRules: GameCreateInputModel): GameCreateOutputModel {
        val result = postRequest("/games", gameRules, GameCreateOutputModel::class.java)
        return result.getOrThrow()
    }

    override suspend fun getGameById(gameId: Int): GetGameOutputModel {
        val result = getRequest("/games/$gameId", GetGameOutputModel::class.java)
        return result.getOrThrow()
    }

    override suspend fun getGameByGameRulesAndUserId(gameRules: Int): GetIdGameOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUserGames(): GetGamesOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUserLobbys(): GetLobbysOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerGameTurn(gameId: Int): GetPlayerOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGameRules(): GameRulesListsOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun placeStone(
        gameId: Int,
        stone: PlaceStoneInputModel
    ): PlaceStoneOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromLobby(lobbyId: Int): RemoveOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun removeGame(gameId: Int): RemoveOutputModel {
        TODO("Not yet implemented")
    }
}