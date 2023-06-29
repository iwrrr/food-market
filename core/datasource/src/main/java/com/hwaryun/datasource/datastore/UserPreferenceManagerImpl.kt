package com.hwaryun.datasource.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : UserPreferenceManager {

    override val readOnBoardingState: Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[ON_BOARDING_KEY] ?: false
            }

    override val user: Flow<AuthDto.UserDto?> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                gson.fromJson(
                    it.toPreferences()[USER_OBJECT],
                    AuthDto.UserDto::class.java
                )
            }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit {
            it[ON_BOARDING_KEY] = completed
        }
    }

    override suspend fun saveUserToken(token: String?) {
        dataStore.edit {
            it[USER_TOKEN] = token.orEmpty()
        }
    }

    override suspend fun saveUser(user: AuthDto.UserDto?) {
        dataStore.edit {
            it[USER_OBJECT] = gson.toJson(user)
        }
    }

    override suspend fun clearUser() {
        dataStore.edit {
            it[USER_OBJECT] = gson.toJson(null)
        }
    }

    companion object {
        private const val PREF_USER_TOKEN = "PREF_USER_TOKEN"
        private const val PREF_USER_OBJECT = "PREF_USER_OBJECT"
        private const val PREF_ON_BOARDING_KEY = "PREF_ON_BOARDING_KEY"

        val USER_TOKEN = stringPreferencesKey(PREF_USER_TOKEN)
        val USER_OBJECT = stringPreferencesKey(PREF_USER_OBJECT)
        val ON_BOARDING_KEY = booleanPreferencesKey(PREF_ON_BOARDING_KEY)
    }
}