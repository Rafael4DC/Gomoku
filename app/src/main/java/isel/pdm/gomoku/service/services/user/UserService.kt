package isel.pdm.gomoku.service.services.user

import com.google.gson.Gson
import isel.pdm.gomoku.service.http.Requests
import okhttp3.OkHttpClient
import pt.isel.daw.gomokuroyale.http.controller.user.model.create.UserCreateInputModel
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostLoginModel
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostTokenOutputModel
import pt.isel.daw.gomokuroyale.http.controller.user.model.getuser.GetUserOutputModel
import isel.pdm.gomoku.service.services.user.model.info.UserInfoOutputModel
import pt.isel.daw.gomokuroyale.http.controller.user.model.refresh.UserPostRefreshModel

class UserService(
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
): UserServiceInterface, Requests(jsonEncoder, httpClient
) {
    override suspend fun createUser(input: UserCreateInputModel): UserPostTokenOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): GetUserOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(name: String): GetUserOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun userLogin(userLogin: UserPostLoginModel): UserPostTokenOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): String {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(userRefresh: UserPostRefreshModel): UserPostTokenOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(): UserInfoOutputModel {
        TODO("Not yet implemented")
    }

    override suspend fun getLeaderboard(
        skip: Int?,
        limit: Int?,
        search: String?
    ): List<GetUserOutputModel> {
        TODO("Not yet implemented")
    }
}