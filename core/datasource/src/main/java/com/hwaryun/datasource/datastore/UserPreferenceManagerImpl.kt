package com.hwaryun.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.hwaryun.network.model.response.AuthDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : UserPreferenceManager {

    override val userToken: Flow<String> =
        context.dataStore.data
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

    override val user: Flow<AuthDto.UserDto> =
        context.dataStore.data
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

    override suspend fun saveUserToken(token: String?) {
        context.dataStore.edit {
            it[USER_TOKEN] = token.orEmpty()
        }
    }

    override suspend fun saveUser(user: AuthDto.UserDto) {
        context.dataStore.edit {
            it[USER_TOKEN] = gson.toJson(user)
        }
    }

    companion object {
        private const val PREF_USER_TOKEN = "PREF_USER_TOKEN"
        private const val PREF_USER_OBJECT = "PREF_USER_OBJECT"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("food_market_pref")
        val USER_TOKEN = stringPreferencesKey(PREF_USER_TOKEN)
        val USER_OBJECT = stringPreferencesKey(PREF_USER_OBJECT)
    }
}