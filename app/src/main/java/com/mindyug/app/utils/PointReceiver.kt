package com.mindyug.app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mindyug.app.background.PointCollectWorker

class PointsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val workManager = WorkManager.getInstance(context)

        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<PointCollectWorker>()
                .addTag("PointCollectorWorker")
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()

        workManager.enqueue(uploadWorkRequest)
    }
}