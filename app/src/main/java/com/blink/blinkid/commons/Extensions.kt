package com.blink.blinkid.commons

import com.blink.blinkid.model.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.io.File


fun ResponseBody.toErrorMessage(): ErrorResponse {
    val gson = Gson()
    return gson.fromJson(this.charStream(), ErrorResponse::class.java)
}

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
