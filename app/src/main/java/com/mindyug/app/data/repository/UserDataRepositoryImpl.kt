package com.mindyug.app.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import com.mindyug.app.domain.model.UserData
import com.mindyug.app.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserDataRepositoryImpl
    (
    private val usersList: CollectionReference,
    private val storageReference: StorageReference
) : UserDataRepository {

    override fun setUserData(userData: UserData) {
        FirebaseAuth.getInstance().currentUser?.let { usersList.document(it.uid).set(userData) }
    }



    override fun uploadProfilePic(uri: Uri) {
        storageReference.putFile(uri)
    }

//    override  fun getBookById(uid: String): Flow<Result<UserData>> = flow {
//        try {
//            emit(Result.Loading<UserData>())
//
//            val book = usersList
//                .whereGreaterThanOrEqualTo("id", uid)
//                .get()
//                .await()
//                .toObjects(UserData::class.java)
//                .first()
//
//
//            emit(Result.Success<UserData>(data = book))
//
//        } catch (e: Exception) {
//            emit(Result.Error<UserData>(message = e.localizedMessage ?: "Error Detected"))
//        }
//    }


}

