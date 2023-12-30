import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import isel.pdm.gomoku.domain.IOState
import isel.pdm.gomoku.domain.idle
import isel.pdm.gomoku.domain.loaded
import isel.pdm.gomoku.domain.loading
import isel.pdm.gomoku.domain.user.User
import isel.pdm.gomoku.service.services.user.UserServiceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostTokenOutputModel
import isel.pdm.gomoku.ui.common.PreferencesKeys
import kotlinx.coroutines.flow.firstOrNull
import pt.isel.daw.gomokuroyale.http.controller.user.model.getuser.GetUserOutputModel

class HomeScreenViewModel(
    private val repository: UserServiceInterface,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    companion object {
        fun factory(repository: UserServiceInterface, dataStore: DataStore<Preferences>) = viewModelFactory {
            initializer { HomeScreenViewModel(repository, dataStore) }
        }
    }

    private val _userInfoFlow: MutableStateFlow<IOState<User?>> = MutableStateFlow(idle())
    private val _leaderboardFlow: MutableStateFlow<IOState<List<GetUserOutputModel>?>> = MutableStateFlow(idle())
    private val _userStateFlow: MutableStateFlow<IOState<Boolean?>> = MutableStateFlow(idle())

    val userInfo: Flow<IOState<User?>>
        get() = _userInfoFlow.asStateFlow()

    val leaderboards: Flow<IOState<List<GetUserOutputModel>?>>
        get() = _leaderboardFlow.asStateFlow()

    val userState: Flow<IOState<Boolean?>>
        get() = _userStateFlow.asStateFlow()

    fun getLeaderboards(skip: Int?=0, limit: Int?=10, search: String?="") {
        viewModelScope.launch {
            _leaderboardFlow.value = loading()
            val leaderboardsResult = runCatching { repository.getLeaderboard(skip,limit, search) }
            _leaderboardFlow.value = loaded(leaderboardsResult)
        }
    }

    fun checkUser() {
        viewModelScope.launch {
            val userId = dataStore.data.map { preferences ->
                preferences[PreferencesKeys.USER_ID.toPreferencesKey()]?.toIntOrNull()
            }.firstOrNull()

            val username = dataStore.data.map { preferences ->
                preferences[PreferencesKeys.USER_NAME.toPreferencesKey()]
            }.firstOrNull()

            val accessToken = dataStore.data.map { preferences ->
                preferences[PreferencesKeys.ACCESS_TOKEN.toPreferencesKey()]
            }.firstOrNull()

            val refreshToken = dataStore.data.map { preferences ->
                preferences[PreferencesKeys.REFRESH_TOKEN.toPreferencesKey()]
            }.firstOrNull()

            val user = if (userId != null && username != null && accessToken != null && refreshToken != null) {
                User(
                    id = userId,
                    tokenRefresh = refreshToken,
                    token = accessToken,
                    username = username
                )
            } else {
                null
            }

            _userInfoFlow.value = loaded(Result.success(user))
        }
    }


    fun resetToIdle() {
        _userInfoFlow.value = idle()
        _userStateFlow.value = idle()
        _leaderboardFlow.value = idle()
    }
}
