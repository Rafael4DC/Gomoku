package isel.pdm.gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import isel.pdm.gomoku.domain.game.play.Match
import isel.pdm.gomoku.infrastructure.MatchFirebase
import isel.pdm.gomoku.service.services.game.GameService
import isel.pdm.gomoku.service.services.game.GameServiceInterface
import isel.pdm.gomoku.service.services.user.UserService
import isel.pdm.gomoku.service.services.user.UserServiceInterface
import okhttp3.OkHttpClient

const val TAG = "Gomoku9"

/**
 * The contract for the object that holds all the globally relevant dependencies.
 */
interface DependenciesContainer {
    val userService: UserServiceInterface
    val game: GameServiceInterface
    val matchFactory: () -> Match
    val dataStore: DataStore<Preferences>
}


/**
 * The application class to be used as a Service Locator.
 */
class Gomoku9Application : Application(), DependenciesContainer {

    override val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

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

    override val userService: UserService
        get() = UserService(OkHttpClient(), Gson()) //UserInfoDataStore(dataStore)

    override val game: GameService
        get() = GameService(OkHttpClient(), Gson()) //LobbyFirebase(emulatedFirestoreDb

    override val matchFactory: () -> Match
        get() = { MatchFirebase(Any()) }
}
