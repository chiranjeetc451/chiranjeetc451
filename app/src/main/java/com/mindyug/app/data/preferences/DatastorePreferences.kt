package com.mindyug.app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

abstract class PrefsDataStore(context: Context, fileName: String) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(fileName)
    val mDataStore: DataStore<Preferences> = context.dataStore
}


class UserLoginStateImpl(context: Context) : PrefsDataStore(context, PREF_USER_LOGIN_STATE),
    UserLoginState {

    override val uid: Flow<String>
        get() = mDataStore.data.map { preferences ->
            val uid = preferences[UID_KEY] ?: ""
            uid
        }

    override val name: Flow<String>
        get() = mDataStore.data.map { preferences ->
            val uid = preferences[NAME_KEY] ?: ""
            uid
        }
    override val isPermissionRequested: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            val isPermissionRequested = preferences[IS_PERMISSION_REQUESTED] ?: false
            isPermissionRequested
        }

    override suspend fun setUid(uid: String) {
        mDataStore.edit { preferences ->
            preferences[UID_KEY] = uid
        }
    }

    override suspend fun editName(name: String) {
        mDataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    override suspend fun clear() {
        mDataStore.edit { preferences ->
            preferences[UID_KEY] = ""
        }
        mDataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
        }
    }

    override suspend fun togglePermissionRequest() {
        mDataStore.edit { preferences ->
            val state = preferences[IS_PERMISSION_REQUESTED] ?: false
            preferences[IS_PERMISSION_REQUESTED] = !state
        }
    }

    companion object {
        private const val PREF_USER_LOGIN_STATE = "user_login_state"

        private val UID_KEY = stringPreferencesKey("uid")
        private val NAME_KEY = stringPreferencesKey("name")
        private val IS_PERMISSION_REQUESTED = booleanPreferencesKey("isPermissionRequested")


    }
}

class PointSysUtilsImpl(context: Context) : PrefsDataStore(context, PREF_POINT_SYSTEM_UTILS),
    PointSysUtils {
    override val collectButtonState: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            val state = preferences[BUTTON_STATE] ?: false
            state
        }

    override val temporaryPoints: Flow<Long>
        get() = mDataStore.data.map { preferences ->
            val tempPoints = preferences[TEMP_POINTS] ?: 0
            tempPoints
        }

    override suspend fun clearPoints() {
        mDataStore.edit { preferences ->
            preferences[TEMP_POINTS] = 0
        }
    }

    override suspend fun addPoints(points: Long) {
        mDataStore.edit { preferences ->
            preferences[TEMP_POINTS] = points
        }
    }

    override suspend fun toggleButtonState() {
        mDataStore.edit { preferences ->
            val state = preferences[BUTTON_STATE] ?: false
            preferences[BUTTON_STATE] = !state
        }
    }


    companion object {
        private const val PREF_POINT_SYSTEM_UTILS = "point_system_utils"

        private val BUTTON_STATE = booleanPreferencesKey("collect_button_state")
        private val TEMP_POINTS = longPreferencesKey("temporary_points")
    }

}


@Singleton
interface UserLoginState {
    val uid: Flow<String>
    val name: Flow<String>
    val isPermissionRequested: Flow<Boolean>

    suspend fun setUid(uid: String)
    suspend fun editName(name: String)
    suspend fun clear()
    suspend fun togglePermissionRequest()
}

@Singleton
interface PointSysUtils {
    val collectButtonState: Flow<Boolean>
    val temporaryPoints: Flow<Long>

    suspend fun toggleButtonState()

    suspend fun clearPoints()
    suspend fun addPoints(points: Long)
}

