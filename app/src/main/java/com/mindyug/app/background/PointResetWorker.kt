package com.mindyug.app.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mindyug.app.data.preferences.PointSysUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect

@HiltWorker
class PointResetWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val pointSysUtils: PointSysUtils
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {

        pointSysUtils.collectButtonState.collect {
            if (it) {
                pointSysUtils.toggleButtonState()
                pointSysUtils.clearPoints()
            }
        }

        return Result.success()
    }
}