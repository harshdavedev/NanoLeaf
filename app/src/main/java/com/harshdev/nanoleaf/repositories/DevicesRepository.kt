package com.harshdev.nanoleaf.repositories

import android.content.Context
import android.util.Log
import com.harshdev.nanoleaf.constants.DeviceEnumMeta
import com.harshdev.nanoleaf.data.MockData
import com.harshdev.nanoleaf.models.Device
import com.harshdev.nanoleaf.utils.DevicesHelper
import java.util.*

/**
 * Created by on Harshdev 14/03/2021.
 */

class DevicesRepository {

    private val TAG = "CommandCenterRepository"
    private val TURNED_ON = "Turned On"
    private val TURNED_OFF = "Turned Off"

    fun retrieveAscendingParsedData(context: Context) : TreeMap<Int, Device> {
        val devicesList = DevicesHelper.parseStringSequenceToDeviceList(
            MockData(context).devicesSequences,
            MockData(context).houseLights,
            MockData(context).roomcolors,
        )

        return DevicesHelper.orderDeviceListInAscending(devicesList)
    }

    fun processDeviceRequests(device: Device, deviceEnumMeta: DeviceEnumMeta) {
        if (deviceEnumMeta == DeviceEnumMeta.POWER) {
            val isOnStr = if (device.isOn) {
                TURNED_ON
            } else {
                TURNED_OFF
            }

            Log.i(TAG, "Device ${device.id}- $isOnStr")
        } else if (deviceEnumMeta == DeviceEnumMeta.BRIGHTNESS) {
            Log.i(TAG, "Device ${device.id}- Brightness is ${device.brightness}")
        }
    }
}