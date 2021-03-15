package com.harshdev.nanoleaf.utils

import com.harshdev.nanoleaf.models.Device
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Created by on Harshdev 14/03/2021.
 */

class DevicesHelper {

    companion object {
        fun parseStringSequenceToDeviceList(
                sequence: String,
                homeLights: Array<String>,
                colors: Array<Int>) : ArrayList<Device> {
            val sequenceSplit : List<String> = sequence.split("\\s".toRegex())
            val devicesArrayList = ArrayList<Device>()

            for (i in sequenceSplit.indices step 3) {
                val device = Device(
                    homeLights[Random.nextInt(homeLights.size)],
                    colors[Random.nextInt(colors.size)])

                if (sequenceSplit[i].length == 5) {
                    device.id = sequenceSplit[i]
                }
                if (sequenceSplit[i+1].length == 1) {
                    device.isOn = sequenceSplit[i+1] == "1"
                }
                if (sequenceSplit[i+2].isNotEmpty() && sequenceSplit[i+2].length <= 3){
                    device.brightness = sequenceSplit[i+2].toInt()
                }

                devicesArrayList.add(device)
            }

            return devicesArrayList
        }

        fun orderDeviceListInAscending(devicesList: ArrayList<Device>) : TreeMap<Int, Device> {
            val devicesTreeList = TreeMap<Int, Device>()

            for (device in devicesList) {
                devicesTreeList[device.id!!.toInt()] = device
            }

            return devicesTreeList
        }
    }

}