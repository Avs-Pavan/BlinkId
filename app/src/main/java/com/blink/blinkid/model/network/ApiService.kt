package com.blink.blinkid.model.network

import com.blink.blinkid.LoginRequest
import com.blink.blinkid.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>


}