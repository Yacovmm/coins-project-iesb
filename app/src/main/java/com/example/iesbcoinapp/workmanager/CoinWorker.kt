package com.example.iesbcoinapp.workmanager

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.iesbcoinapp.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random
import com.example.iesbcoinapp.core.utils.ResponseWrapper
import com.example.iesbcoinapp.data.CoinRepository

@HiltWorker
class CoinWorker @AssistedInject constructor(
    @Assisted val ctx: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var repository: CoinRepository

    // https://stackoverflow.com/questions/53297982/how-to-run-workmanager-immediately
    override suspend fun doWork(): Result {
        try {
            setForeground(createForegroundInfo())

            val response = repository.getLatestCoins()
            println("Passei aqui")

            when (response) {
                is ResponseWrapper.Error -> {
                    Log.d(TAG, "ERRO INESPERADO")
                    return Result.retry()
                }
                is ResponseWrapper.Success -> {
                    Log.d(TAG, "")
                }
            }

            scheduleWorker()

            return Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            return Result.failure()
        }
    }

    private fun scheduleWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request =
            OneTimeWorkRequestBuilder<CoinWorker>()
                .setConstraints(constraints)
                .setInitialDelay(15, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(ctx).enqueue(request)
    }

    private fun createForegroundInfo(): ForegroundInfo {
        // generate a random id for each notification
        val notificationId = Random.nextInt()

        val notification = createNotification()
        return ForegroundInfo(notificationId, notification)
    }

    private fun createNotification(): Notification {
        val intent = WorkManager.getInstance(ctx).createCancelPendingIntent(id)

        val builder = NotificationCompat.Builder(ctx, "channelId")
            .setContentTitle("Coin Worker")
            .setTicker("Coin Worker")
            .setContentText("Coin Worker")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true) // Nao pode ser cancelada pelo user
            .addAction(android.R.drawable.ic_delete, "Cancel", intent)
            .setContentIntent(intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(createNotificationChannel().id)
        }
        return builder.build()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            "channelId", "ChannelName", NotificationManager.IMPORTANCE_LOW
        ).also { channel ->
            val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
