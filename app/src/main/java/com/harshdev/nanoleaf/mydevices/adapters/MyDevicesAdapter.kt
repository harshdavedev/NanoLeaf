package com.harshdev.nanoleaf.mydevices.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.harshdev.nanoleaf.R
import com.harshdev.nanoleaf.models.Device
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by on Harshdev 14/03/2021.
 */

class MyDevicesAdapter(val deviceControlListener: DeviceControlListener) : RecyclerView.Adapter<MyDevicesAdapter.MyViewHolder>() {

    var devicesList = ArrayList<Device>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardView: MaterialCardView = itemView.findViewById(R.id.materialcardview)
        var deviceRectangleColor: View = itemView.findViewById(R.id.view_devicerectanglecolor)
        var deviceName: TextView = itemView.findViewById(R.id.tv_devicename)
        var deviceId: TextView = itemView.findViewById(R.id.tv_deviceid)
        var deviceOnOff: SwitchCompat = itemView.findViewById(R.id.switch_deviceonoff)
        var brightnessSeekBar: SeekBar = itemView.findViewById(R.id.sb_brightness)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.device_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val deviceItem = devicesList[holder.adapterPosition]
        val color = deviceItem.rectangleColor!!

        holder.cardView.setCardBackgroundColor(color)
        holder.deviceRectangleColor.setBackgroundColor(color)
        holder.deviceName.text = deviceItem.name
        holder.deviceId.text = deviceItem.id
        holder.deviceOnOff.isChecked = deviceItem.isOn
        holder.brightnessSeekBar.progress = deviceItem.brightness!!

        setSwitchesSeekBarColorsAttributes(holder)

        holder.deviceOnOff.setOnCheckedChangeListener { _: CompoundButton, isOn: Boolean ->
            deviceControlListener.onSwitchClickListener(holder.adapterPosition, isOn)
        }

        holder.brightnessSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, brightness: Int, p2: Boolean) {
                deviceControlListener.onBrightnessControlListener(holder.adapterPosition, brightness)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun setSwitchesSeekBarColorsAttributes(holder: MyViewHolder) {
        val deviceItem = devicesList[holder.adapterPosition]
        val color = deviceItem.rectangleColor!!

        DrawableCompat.setTintList(
            DrawableCompat.wrap(holder.deviceOnOff.thumbDrawable),
            ColorStateList(arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)),
                intArrayOf(Color.WHITE, color))
        )
        DrawableCompat.setTintList(
            DrawableCompat.wrap(holder.deviceOnOff.thumbDrawable),
            ColorStateList(arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)),
                intArrayOf(Color.WHITE, color)))

        holder.brightnessSeekBar.progressDrawable.setColorFilter(
            deviceItem.rectangleColor!!,
            PorterDuff.Mode.MULTIPLY
        )
        holder.brightnessSeekBar.thumb.setColorFilter(
            deviceItem.rectangleColor!!,
            PorterDuff.Mode.SRC_ATOP
        )
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    fun getDeviceAtPosition(position: Int) : Device{
        return devicesList[position]
    }

    fun setDevicesList(devices: TreeMap<Int, Device>) {
        for (device in devices) {
            devicesList.add(device.value)
        }
        notifyDataSetChanged()
    }

    fun getAverageBrightness() : Int {
        var averageBrightness = 0
        for (device in devicesList) {
            averageBrightness += device.brightness!!
        }
        return (averageBrightness / devicesList.size)
    }

    fun getGlobalPowerSwitchState() : Boolean {
        for (device in devicesList) {
            if (device.isOn) return true
        }
        return false
    }

    fun toggleAllDevices(isOn: Boolean) {
        for (device in devicesList) {
            device.isOn = isOn
        }
        notifyDataSetChanged()
    }

    fun setBrightnessForAllDevicesAsGlobalBrightness(brightness: Int) {
        for (device in devicesList) {
            device.brightness = brightness
        }
        notifyDataSetChanged()
    }

    interface DeviceControlListener {
        fun onSwitchClickListener(position: Int, isOn: Boolean)
        fun onBrightnessControlListener(position: Int, brightness: Int)
    }
}