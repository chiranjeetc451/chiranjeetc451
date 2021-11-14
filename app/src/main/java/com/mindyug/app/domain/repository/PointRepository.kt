package com.mindyug.app.domain.repository

import com.mindyug.app.data.repository.Results
import com.mindyug.app.domain.model.PointItem
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    fun getAllPointsFromUid(uid: String): Flow<Results<List<PointItem>>>
    fun savePointItem(pointItem: PointItem, uid: String): Flow<Results<String>>
}