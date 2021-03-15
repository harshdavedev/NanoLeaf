package com.harshdev.nanoleaf.data

import android.content.Context
import com.harshdev.nanoleaf.R

/**
 * Created by on Harshdev 14/03/2021.
 */

class MockData(context: Context) {

        val houseLights = arrayOf(
            "Bedroom Light",
            "Kitchen Light",
            "Bathroom Light",
            "Living Light",
            "Pool Light",
            "Basement Light",
            "Garden Light",
            "Pantry Light",
            "Storeroom Light",
            "Balcony Light"
        )

        val devicesSequences =
            "10001 1 34 " +
                    "10010 1 88 " +
                    "10004 1 20 " +
                    "10003 0 100 " +
                    "10007 0 5 " +
                    "10002 0 0 " +
                    "10008 0 41 " +
                    "10005 1 76 " +
                    "10006 0 52 " +
                    "10009 1 10"

        val roomcolors = arrayOf(
            context.getColor(R.color.sky_blue),
            context.getColor(R.color.orange),
            context.getColor(R.color.violet),
            context.getColor(R.color.green),
            context.getColor(R.color.cyan),
            context.getColor(R.color.magenta),
            context.getColor(R.color.black),
            context.getColor(R.color.lightgray),
            context.getColor(R.color.darkgray),
            context.getColor(R.color.sky_blue)
        )
 }