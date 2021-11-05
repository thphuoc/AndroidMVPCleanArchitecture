package com.kkrtc.driver.view.screens.locationService

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.kkrtc.driver.R
import com.kkrtc.driver.view.screens.home.HomeActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.patloew.rxlocation.RxLocation
import com.phuoc.domain.usecases.IUpdateTrackingUseCase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.inject


class TrackingService : Service(),
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener {

    private lateinit var updateLocationObservable: Observable<Location>
    private val fusedLocation by lazy { RxLocation(this).location() }
    private val updateTrackingUseCase: IUpdateTrackingUseCase by inject()
    private val locationRequest: LocationRequest by lazy {
        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)
        request.maxWaitTime = 5000
        request
    }

    private var disposable: Disposable? = null

    private val locationSubject: PublishSubject<Location> by inject()

    private val CHANNEL_ID = "timer"
    private val NOTIFICATION_ID = 100

    private val googleClient by lazy {
        val client = GoogleApiClient.Builder(this)
        client.addApi(LocationServices.API)
        client.addConnectionCallbacks(this)
        client.addOnConnectionFailedListener(this)
        client.build()
    }

    override fun onBind(intent: Intent): IBinder? = null

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val isStop = intent?.getBooleanExtra("stop", false) ?: false
        if (isStop) {
            googleClient.disconnect()
            disposable?.dispose()
            stopSelf()
            return START_NOT_STICKY
        }

        showNotification("Tracking location")

        updateLocationObservable = fusedLocation.updates(locationRequest)
        updateLocationObservable.observeOn(Schedulers.computation()).subscribe({}, {})
        startUpdateTracking()
        return START_STICKY
    }

    //
    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        showNotification("Connect location service connected")
        LocationServices.FusedLocationApi.requestLocationUpdates(
            googleClient,
            locationRequest,
            this
        )
    }

    override fun onConnectionSuspended(p0: Int) {
        showNotification("Connect location service suspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        showNotification("Connect location service failed")
    }

    override fun onLocationChanged(location: Location) {
        showNotification("Location update: ${location.latitude}, ${location.longitude}")
        val shared = getSharedPreferences("dashboard", Context.MODE_PRIVATE)
        val trackingId = shared.getInt("trackingId", 0)
        locationSubject.onNext(location)
        if (trackingId > 0) {
            updateTrackingUseCase.execute(
                trackingId = trackingId,
                lat = location.latitude,
                lng = location.longitude
            ).subscribeOn(Schedulers.newThread()).subscribe({
                showNotification("Completed update")
            }, {
                it.printStackTrace()
                showNotification("Update error!")
            })
        }
    }


    private fun startUpdateTracking() {
        googleClient.connect()
    }

    private fun showNotification(
        message: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        //Create notification builder
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(false)
            .setContentTitle("Tracking")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)
        val notificationIntent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
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