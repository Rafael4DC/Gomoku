package isel.pdm.gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import isel.pdm.gomoku.domain.game.lobby.Lobby
import isel.pdm.gomoku.domain.game.play.Match
import isel.pdm.gomoku.domain.user.UserInfoRepository
import isel.pdm.gomoku.infrastructure.LobbyFirebase
import isel.pdm.gomoku.infrastructure.MatchFirebase
import isel.pdm.gomoku.infrastructure.UserInfoDataStore

const val TAG = "Gomoku9"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    val userInfoRepository: UserInfoRepository
    val lobby: Lobby
    val matchFactory: () -> Match
}

/**
 * The application class to be used as a Service Locator.
 */
class Gomoku9Application : Application(), DependenciesContainer {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")

    //private val emulatedFirestoreDb: Any = TODO()
            /*FirebaseFirestore by lazy {
        Firebase.firestore.also {
            it.useEmulator("10.0.2.2", 8080)
            it.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                .build()
        }
    }*/

    //private val realFirestoreDb: Any = TODO()//FirebaseFirestore by lazy { Firebase.firestore}

    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

    override val lobby: Lobby
        get() = LobbyFirebase(Any())

    override val matchFactory: () -> Match
        get() = { MatchFirebase(Any()) }
}
