package isel.pdm.gomoku.ui.common

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

enum class PreferencesKeys {
    USER_ID,
    USER_NAME,
    ACCESS_TOKEN,
    REFRESH_TOKEN;

    fun toPreferencesKey(): Preferences.Key<String> {
        return when (this) {
            USER_ID -> stringPreferencesKey(this.name)
            USER_NAME -> stringPreferencesKey(this.name)
            ACCESS_TOKEN -> stringPreferencesKey(this.name)
            REFRESH_TOKEN -> stringPreferencesKey(this.name)
        }
    }
}

