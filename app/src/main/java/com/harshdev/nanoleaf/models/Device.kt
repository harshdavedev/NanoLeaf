package com.harshdev.nanoleaf.models

/**
 * Created by on Harshdev 14/03/2021.
 */

data class Device (
    var name: String,
    var rectangleColor: Int? = null,
    var id: String? = null,
    var isOn: Boolean = false,
    var brightness: Int? = 0)