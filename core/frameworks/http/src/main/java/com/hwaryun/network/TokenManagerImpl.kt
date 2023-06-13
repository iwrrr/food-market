package com.hwaryun.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(dataStore: DataStore<Preferences>) : TokenManager {

    override val userToken: Flow<String> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[USER_TOKEN].orEmpty()
            }

    companion object {
        private const val PREF_USER_TOKEN = "PREF_USER_TOKEN"

        val USER_TOKEN = stringPreferencesKey(PREF_USER_TOKEN)
    }
}