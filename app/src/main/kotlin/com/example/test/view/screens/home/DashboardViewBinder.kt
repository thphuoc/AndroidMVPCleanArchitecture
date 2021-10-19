package com.example.test.view.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.test.R
import com.example.test.exts.observe
import com.example.test.view.base.StateFragment
import com.example.test.view.bottomsheets.SchedulerBottomSheet
import com.example.test.view.bottomsheets.VehicleBottomSheet
import com.example.test.view.exts.showIf
import com.example.test.view.exts.showMessageYesNoDialog
import com.example.test.view.screens.locationService.TrackingService
import com.google.android.gms.location.LocationRequest
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.patloew.rxlocation.RxLocation
import com.phuoc.domain.entities.PairEntity
import com.phuoc.domain.usecases.IStartTrackingUseCase
import com.tbruyelle.rxpermissions2.RxPermissions
import org.koin.android.ext.android.inject

@Layout(R.layout.view_dashboard)
class DashboardViewBinder(private val fragment: StateFragment<*>) {

    @View(R.id.tvVehicle)
    lateinit var tvVehicle: TextView

    @View(R.id.tvSchedulers)
    lateinit var tvSchedulers: TextView

    @View(R.id.tvTrackingStatus)
    lateinit var tvTrackingStatus: TextView

    @View(R.id.switcher)
    lateinit var switcher: SwitchCompat

    @View(R.id.tvErrorVehicle)
    lateinit var tvErrorVehicle: TextView

    @View(R.id.tvErrorScheduler)
    lateinit var tvErrorScheduler: TextView

    private var selectedVehicle: PairEntity? = null
    private var selectedScheduler: PairEntity? = null

    @SuppressLint("MissingPermission")
    @Resolve
    fun onResolve() {
        loadCurrentState()

        switcher.setOnClickListener {
            val isChecked = switcher.isChecked
            if (isChecked) {
                var isError = false
                if (selectedVehicle == null) {
                    isError = true
                    tvErrorVehicle.showIf(showIf = true, false)
                }
                if (selectedScheduler == null) {
                    isError = true
                    tvErrorScheduler.showIf(showIf = true, false)
                }
                if (!isError) {
                    tvErrorVehicle.showIf(showIf = false, false)
                    tvErrorScheduler.showIf(showIf = false, false)
                } else {
                    switcher.isChecked = false
                }

                if (isError) {
                    return@setOnClickListener
                }

                fragment.showMessageYesNoDialog(title = "Start sharing",message = "Do you want to start sharing your location?", onYes = {
                    startTracking()
                }, onNo = {
                    switcher.isChecked = false
                })

            } else {
                fragment.showMessageYesNoDialog(title = "Stop sharing",message = "Do you want to stop sharing your location?", onYes = {
                    tvErrorVehicle.showIf(showIf = false, false)
                    tvErrorScheduler.showIf(showIf = false, false)
                    tvTrackingStatus.text = "Stop tracking"

                    TrackingService.stopSelf(fragment.activity!!)
                    saveCurrentState(false)
                    enableDropdown()
                }, onNo = {
                    switcher.isChecked = true
                })
            }
        }
    }

    fun stopTracking() {
        switcher.isChecked = false
        TrackingService.stopSelf(fragment.activity!!)
        saveCurrentState(false)
        enableDropdown()
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {
        RxPermissions(fragment).request(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe({ hasPermission ->
            if (hasPermission) {
                val request = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000)
                request.numUpdates = 1
                request.maxWaitTime = 5000

                disableDropdown()

                RxLocation(fragment.requireContext()).location().updates(
                    request
                ).observe(fragment.viewModel, showLoadingDialog = false, onSuccess = {
                    val startTrackingUseCase: IStartTrackingUseCase by fragment.inject()
                    startTrackingUseCase.execute(
                        schedule = selectedScheduler?.id ?: 0,
                        vehicle = selectedVehicle?.id ?: 0,
                        lat = it.latitude,
                        lng = it.longitude
                    ).observe(
                        fragment.viewModel,
                        showLoadingDialog = false,
                        onSuccess = { tracking ->
                            saveCurrentState(true)
                            val intent =
                                Intent(fragment.requireContext(), TrackingService::class.java)
                            intent.putExtra("trackingId", tracking.id)
                            fragment.activity?.startService(intent)
                        })
                })
            }
        }, {})
    }

    private fun disableDropdown() {
        tvVehicle.isEnabled = false
        tvVehicle.alpha = 0.5f
        tvSchedulers.isEnabled = false
        tvSchedulers.alpha = 0.5f
    }

    private fun enableDropdown() {
        tvVehicle.isEnabled = true
        tvVehicle.alpha = 1f
        tvSchedulers.isEnabled = true
        tvSchedulers.alpha = 1f
    }

    private fun loadCurrentState() {
        val shared =
            fragment.requireContext().getSharedPreferences("dashboard", Context.MODE_PRIVATE)
        val vehicleName = shared.getString("vehicleName", "")
        val vehicleId = shared.getInt("vehicleId", 0)
        val schedulerName = shared.getString("schedulerName", "") ?: ""
        val schedulerId = shared.getInt("schedulerId", 0)
        val tracking = shared.getBoolean("tracking", false)

        if (!vehicleName.isNullOrBlank()) {
            selectedVehicle = PairEntity(id = vehicleId, value = vehicleName)
            selectedScheduler = PairEntity(id = schedulerId, value = schedulerName)
            tvVehicle.text = vehicleName
            tvSchedulers.text = schedulerName
            switcher.isChecked = tracking
        }
    }

    private fun saveCurrentState(startTracking: Boolean) {
        val shared =
            fragment.requireContext().getSharedPreferences("dashboard", Context.MODE_PRIVATE)
        shared.edit().putString("vehicleName", selectedVehicle?.value)
            .putInt("vehicleId", selectedVehicle?.id ?: 0)
            .putString("schedulerName", selectedScheduler?.value)
            .putInt("schedulerId", selectedScheduler?.id ?: 0)
            .putBoolean("tracking", startTracking)
            .apply()
    }

    @Click(R.id.tvVehicle)
    fun onClickVehicle() {
        VehicleBottomSheet(fragment).show {
            tvVehicle.text = it.value
            selectedVehicle = it
        }
    }

    @Click(R.id.tvSchedulers)
    fun onClickSchedulers() {
        SchedulerBottomSheet(fragment).show {
            tvSchedulers.text = it.value
            selectedScheduler = it
        }
    }
}