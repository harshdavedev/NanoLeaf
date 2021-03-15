package com.harshdev.nanoleaf.mydevices.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harshdev.nanoleaf.constants.DeviceEnumMeta
import com.harshdev.nanoleaf.models.Device
import com.harshdev.nanoleaf.repositories.DevicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by on Harshdev 14/03/2021.
 */

class DevicesViewModel(application: Application) : AndroidViewModel(application) {

    val devicesInfoLiveData = MutableLiveData<TreeMap<Int, Device>>()
    private val commandCenterRepository = DevicesRepository()

    fun retrieveData(context: Context) {
        devicesInfoLiveData.value = commandCenterRepository.retrieveAscendingParsedData(context)
    }

    fun deviceChange(device: Device, deviceEnumMeta: DeviceEnumMeta) {
        viewModelScope.launch(Dispatchers.IO) {
            commandCenterRepository.processDeviceRequests(device, deviceEnumMeta)
        }
    }
}