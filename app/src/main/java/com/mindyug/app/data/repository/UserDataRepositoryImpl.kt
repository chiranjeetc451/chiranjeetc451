package com.mindyug.app.data.repository

import android.net.Uri
import android.util.Log
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

    override fun setUserData(userData: UserData): Flow<Results<String>> = flow {
        try {
            emit(Results.Loading<String>())

            usersList.document(FirebaseAuth.getInstance().currentUser?.uid!!).set(userData)
            emit(Results.Success<String>(data = "Successful"))

        } catch (e: Exception) {
            emit(Results.Error<String>(message = "Error occurred"))

        }
    }

    override fun uploadProfilePic(uri: Uri) {
        storageReference.putFile(uri)
    }

    override fun getUsernameFromUid(): Flow<Results<UserData>> = flow {
        try {
            emit(Results.Loading<UserData>())
            val user = usersList
                .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                .get()
                .await()
                .toObject(UserData::class.java)


            emit(Results.Success<UserData>(data = user))


        } catch (e: Exception) {
            emit(Results.Error<UserData>(message = "Error occurred"))

        }


    }


}

