import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import isel.pdm.gomoku.domain.IOState
import isel.pdm.gomoku.domain.idle
import isel.pdm.gomoku.domain.loadFailure
import isel.pdm.gomoku.domain.loadSuccess
import isel.pdm.gomoku.domain.loading
import isel.pdm.gomoku.service.services.user.UserServiceInterface
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostLoginModel
import isel.pdm.gomoku.ui.common.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.daw.gomokuroyale.http.controller.user.model.create.UserCreateInputModel
import isel.pdm.gomoku.service.services.user.model.gettoken.UserPostTokenOutputModel

class LoginViewModel(
    private val repository: UserServiceInterface,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    companion object {
        fun factory(repository: UserServiceInterface, dataStore: DataStore<Preferences>) = viewModelFactory {
            initializer { LoginViewModel(repository, dataStore) }
        }
    }

    private val _loginState: MutableStateFlow<IOState<UserPostTokenOutputModel?>> = MutableStateFlow(idle())

    val loginState: Flow<IOState<UserPostTokenOutputModel?>>
        get() = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = loading()
            val result = runCatching { repository.userLogin(UserPostLoginModel(username, password)) }
            _loginState.value = result.fold(
                onSuccess = { user ->
                    saveLoginData(user, username)
                    loadSuccess(user)
                },
                onFailure = {
                    loadFailure(it)
                }
            )
        }
    }


    fun createUser(username: String, password: String, email: String) {
        viewModelScope.launch {
            _loginState.value = loading()
            val result = runCatching { repository.createUser(UserCreateInputModel(username, password, email)) }
            _loginState.value = result.fold(
                onSuccess = { user ->
                    saveLoginData(user, username)
                    loadSuccess(user)
                },
                onFailure = {
                    loadFailure(it)
                }
            )
        }
    }

    private suspend fun saveLoginData(user: UserPostTokenOutputModel, username: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME.toPreferencesKey()] = username
            preferences[PreferencesKeys.ACCESS_TOKEN.toPreferencesKey()] = user.token
            preferences[PreferencesKeys.REFRESH_TOKEN.toPreferencesKey()] = user.tokenRefresh
            preferences[PreferencesKeys.USER_ID.toPreferencesKey()] = user.userId.toString()
        }
    }


}
