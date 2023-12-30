package isel.pdm.gomoku.service.services.user

import pt.isel.daw.gomokuroyale.http.controller.user.model.create.UserCreateInputModel
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostLoginModel
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostTokenOutputModel
import pt.isel.daw.gomokuroyale.http.controller.user.model.getuser.GetUserOutputModel
import isel.pdm.gomoku.service.services.user.model.info.UserInfoOutputModel
import pt.isel.daw.gomokuroyale.http.controller.user.model.refresh.UserPostRefreshModel

interface UserServiceInterface {

    suspend fun createUser(input: UserCreateInputModel): UserPostTokenOutputModel

    suspend fun getUserById(id: String): GetUserOutputModel

    suspend fun getUserByUsername(name: String): GetUserOutputModel

    suspend fun userLogin(userLogin: UserPostLoginModel): UserPostTokenOutputModel

    suspend fun logout(): String

    suspend fun refresh(userRefresh: UserPostRefreshModel): UserPostTokenOutputModel

    suspend fun getUserInfo(): UserInfoOutputModel

    suspend fun getLeaderboard(skip: Int?, limit: Int?, search: String?): List<GetUserOutputModel>// vai com o siren
}