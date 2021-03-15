package com.harshdev.nanoleaf.mydevices.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshdev.nanoleaf.R
import com.harshdev.nanoleaf.mydevices.adapters.MyDevicesAdapter
import com.harshdev.nanoleaf.constants.DeviceEnumMeta
import com.harshdev.nanoleaf.mydevices.viewmodels.DevicesViewModel

/**
 * Created by on Harshdev 14/03/2021.
 */
class MyDevicesActivity : AppCompatActivity(), MyDevicesAdapter.DeviceControlListener {

    private lateinit var myDevicesAdapter: MyDevicesAdapter
    private lateinit var devicesViewModel: DevicesViewModel

    // Define views
    private lateinit var globalDevicesSwitch : SwitchCompat
    private lateinit var globalBrightnessBar : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instantiate global switch and seek bar views
        globalDevicesSwitch = findViewById(R.id.switchdevices)
        globalBrightnessBar = findViewById(R.id.seekbar_brightness)

        // Instantiate recyclerview and adapter
        setRecyclerViewAndAdapter()

        // Instantiate view model
        devicesViewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)

        // Retrieve data from view model
        devicesViewModel.retrieveData(this)

        // Observe view model
        devicesViewModel.devicesInfoLiveData.observe(this, {
            orderedDevices ->
            myDevicesAdapter.setDevicesList(orderedDevices)
            globalDevicesSwitch.isChecked = myDevicesAdapter.getGlobalPowerSwitchState()
            globalBrightnessBar.progress = myDevicesAdapter.getAverageBrightness()
        })

        setGlobalPowerAndBrightnessListeners();
    }

    private fun setGlobalPowerAndBrightnessListeners() {
        globalDevicesSwitch.setOnCheckedChangeListener {
                _: CompoundButton, isOn: Boolean ->
            myDevicesAdapter.toggleAllDevices(isOn)
        }

        globalBrightnessBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(p0: SeekBar?) {
                myDevicesAdapter.setBrightnessForAllDevicesAsGlobalBrightness(p0!!.progress)
            }

            override fun onProgressChanged(p0: SeekBar?, brightness: Int, p2: Boolean) {}
            override fun onStartTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun setRecyclerViewAndAdapter() {
        myDevicesAdapter = MyDevicesAdapter(this)
        val myDevicesRecyclerView : RecyclerView = findViewById(R.id.rv_devices)
        myDevicesRecyclerView.adapter = myDevicesAdapter
        myDevicesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onSwitchClickListener(position: Int, isOn: Boolean) {
        myDevicesAdapter.getDeviceAtPosition(position).isOn = isOn
        globalDevicesSwitch.isChecked = myDevicesAdapter.getGlobalPowerSwitchState()
        devicesViewModel.deviceChange(
            myDevicesAdapter.getDeviceAtPosition(position),
            DeviceEnumMeta.POWER)
    }

    override fun onBrightnessControlListener(position: Int, brightness: Int) {
        myDevicesAdapter.getDeviceAtPosition(position).brightness = brightness
        globalBrightnessBar.progress = myDevicesAdapter.getAverageBrightness()
        devicesViewModel.deviceChange(
            myDevicesAdapter.getDeviceAtPosition(position),
            DeviceEnumMeta.BRIGHTNESS)
    }
}