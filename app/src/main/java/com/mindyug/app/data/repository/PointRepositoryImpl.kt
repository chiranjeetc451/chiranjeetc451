package com.mindyug.app.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class PointRepositoryImpl @Inject constructor(
    private val pointList: FirebaseFirestore,
) : PointRepository {

    override fun getAllPointsFromUid(uid: String): Flow<Results<List<PointItem>>> = flow {
        try {
            emit(Results.Loading<List<PointItem>>())

            val pointList = pointList.collection("userData/points/$uid")
                .get().await().map { document ->
                    document.toObject(PointItem::class.java)
                }

            emit(Results.Success<List<PointItem>>(data = pointList))

        } catch (e: Exception) {
            Log.d("error", e.toString())
            emit(
                Results.Error<List<PointItem>>(
                    message = e.localizedMessage ?: "Error Desconocido"
                )
            )
        }
    }

    override fun savePointItem(pointItem: PointItem, uid: String): Flow<Results<String>> = flow {
        try {
            emit(Results.Loading<String>())

            pointList
                .collection("userData/points/$uid")
                .document(pointItem.fullDate)
                .set(pointItem)

            emit(Results.Success<String>(data = "Successful"))

        } catch (e: Exception) {
            emit(Results.Error<String>(message = "Error occurred"))
            Log.d("error", e.toString())

        }
    }
}