package com.mindyug.app.data.repository

import com.google.firebase.firestore.CollectionReference
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val pointList: CollectionReference,
) : PointRepository {

    override fun getAllPointsFromUid(uid: String): Flow<Results<List<PointItem>>> {
        TODO("Not yet implemented")
    }

    override fun savePointItem(pointItem: PointItem): Flow<Results<String>> {
        TODO("Not yet implemented")
    }
}