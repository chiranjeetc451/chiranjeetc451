package com.mindyug.app.domain.repository

import android.net.Uri
import com.mindyug.app.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun setUserData(userData: UserData)
    fun uploadProfilePic(uri: Uri)
//    fun getBookById(bookId: String): Flow<Result<UserData>>
//    fun getUsernameFromUid(uid: String): String?

}