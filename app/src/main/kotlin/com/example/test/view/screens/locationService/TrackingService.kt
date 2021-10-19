package com.example.test.view.screens.locationService

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.test.R
import com.example.test.view.screens.home.HomeActivity
import com.google.android.gms.location.*
import com.patloew.rxlocation.RxLocation
import com.phuoc.domain.usecases.IUpdateTrackingUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class TrackingService : Service() {

    private lateinit var updateLocationObservable: Observable<Location>
    private val fusedLocation by lazy { RxLocation(this).location() }
    private val updateTrackingUseCase: IUpdateTrackingUseCase by inject()
    private val locationRequest: LocationRequest by lazy {
        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)
        request.numUpdates = 1
        request.maxWaitTime = 10000
        request
    }

    private lateinit var fusedLoc : FusedLocationProviderClient

    private var disposable: Disposable? = null

    private val CHANNEL_ID = "timer"
    private val NOTIFICATION_ID = 100

    override fun onBind(intent: Intent): IBinder? = null

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification("Tracking location")
        fusedLoc = LocationServices.getFusedLocationProviderClient(this)

        val isStop = intent?.getBooleanExtra("stop", false) ?: false
        if (isStop) {
            disposable?.dispose()
            stopSelf()
            return START_NOT_STICKY
        }

        updateLocationObservable = fusedLocation.updates(locationRequest)
        updateLocationObservable.observeOn(Schedulers.computation()).subscribe({}, {})
        val trackingId = intent?.getIntExtra("trackingId", 0) ?: 0
        if (trackingId > 0) {
            startUpdateTracking(trackingId)
        }
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    fun startUpdateTracking(trackingId: Int) {
        disposable = Observable
            .interval(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Tracking", "Phuoc: updated0 $it")
//                fusedLoc.requestLocationUpdates(locationRequest, object: LocationCallback() {
//                    override fun onLocationResult(p0: LocationResult) {
//                        Log.d("Tracking", "Phuoc: updated1 $it")
//                    }
//                }, Looper.getMainLooper())
                RxLocation(this).location().updates(locationRequest)
                    .observeOn(Schedulers.newThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe({ loc ->

                        updateTrackingUseCase.execute(
                            trackingId = trackingId,
                            lat = loc.latitude,
                            lng = loc.longitude
                        ).subscribeOn(Schedulers.newThread()).subscribe({

                        }, {
                            it.printStackTrace()
                        })


                    }, {})
            }, {})
    }

    private fun showNotification(
        message: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        //Create notification builder
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(true)
            .setContentTitle("Tracking")
            .setContentText(message)
            .setNotificationSilent()
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)
        val notificationIntent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        builder.setContentIntent(pendingIntent)
        builder.setOngoing(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(NOTIFICATION_ID, builder.build())
        } else {
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        fun stopSelf(activity: Activity) {
            val intent = Intent(activity, TrackingService::class.java)
            intent.putExtra("stop", true)
            activity.startService(intent)
            val notificationManager =
                activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }
    }
}