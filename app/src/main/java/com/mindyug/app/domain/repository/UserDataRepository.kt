package com.mindyug.app.domain.repository

import android.net.Uri
import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun setUserData(userData: UserData): Flow<Results<String>>
    fun uploadProfilePic(uri: Uri)
    fun getUsernameFromUid(): Flow<Results<UserData>>
    fun getProfilePictureUri(uid:String): Flow<Results<Uri>>
}