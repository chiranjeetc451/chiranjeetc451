package com.mindyug.app.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mindyug.app.domain.model.UserData
import com.mindyug.app.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataRepositoryImpl
@Inject constructor(
    private val usersList: FirebaseFirestore,
    private val storageReference: StorageReference
) : UserDataRepository {

    override fun setUserData(userData: UserData): Flow<Results<String>> = flow {
        try {
            emit(Results.Loading<String>())

            usersList.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!).set(userData)
            emit(Results.Success<String>(data = "Successful"))

        } catch (e: Exception) {
            emit(Results.Error<String>(message = "Error occurred"))

        }
    }

    override fun setUserDataFromUid(userData: UserData, uid: String): Flow<Results<String>> = flow {
        try {
            emit(Results.Loading<String>())

            usersList.collection("users").document(uid).set(userData)
            emit(Results.Success<String>(data = "Successful"))

        } catch (e: Exception) {
            emit(Results.Error<String>(message = "Error occurred"))

        }
    }

    override fun getUserFromUid(uid: String): Flow<Results<UserData>> = flow {

        try {
            emit(Results.Loading<UserData>())
            val user = usersList
                .collection("users")
                .document(uid)
                .get()
                .await()
                .toObject(UserData::class.java)


            emit(Results.Success<UserData>(data = user))


        } catch (e: Exception) {
            emit(Results.Error<UserData>(message = "Error occurred"))

        }
    }

    override fun updateProfilePictureFromUid(uri: Uri, uid: String) {
        storageReference.child(uid).putFile(uri)
    }

    override fun uploadProfilePic(uri: Uri) {
        storageReference.child(FirebaseAuth.getInstance().currentUser?.uid!!).putFile(uri)
    }

    override fun getUsernameFromUid(): Flow<Results<UserData>> = flow {
        try {
            emit(Results.Loading<UserData>())
            val user = usersList
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                .get()
                .await()
                .toObject(UserData::class.java)


            emit(Results.Success<UserData>(data = user))


        } catch (e: Exception) {
            emit(Results.Error<UserData>(message = "Error occurred"))

        }


    }

    override fun getProfilePictureUri(uid: String): Flow<Results<Uri>> = flow {
        try {
            emit(Results.Loading<Uri>())

            val uri = storageReference
                .child(uid)
                .downloadUrl
                .await()

            emit(Results.Success<Uri>(data = uri))

        } catch (e: Exception) {
            emit(Results.Error<Uri>(message = "Error occurred"))

            Log.d("tag", e.toString())


        }


    }


}

