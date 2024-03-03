package com.blink.blinkid.model.network

import com.blink.blinkid.model.Exam
import com.blink.blinkid.model.LoginRequest
import com.blink.blinkid.model.LoginResponse
import com.blink.blinkid.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users/students")
    suspend fun getStudents(): Response<List<User>>

    @GET("users/students")
    suspend fun getAdmins(): Response<List<User>>

    @POST("exams")
    suspend fun addExam(@Body exam: Exam): Response<Exam>

    @GET("exams")
    suspend fun getExams(): Response<List<Exam>>

    @GET("exams/{examId}")
    suspend fun getExam(@Path("examId") examId: Int): Response<Exam>

    @PUT("exams/{examId}")
    suspend fun updateExam(
        @Path("examId") examId: Int,
        @Body exam: Exam
    ): Response<Exam>

    @POST("exams/{examId}/users/{userId}")
    suspend fun addExamStudent(
        @Path("examId") examId: Int,
        @Path("userId") userId: Int
    ): Response<Exam>

    @POST("exams/{examId}/admins/{userId}")
    suspend fun addExamAdmin(
        @Path("examId") examId: Int,
        @Path("userId") userId: Int
    ): Response<Exam>

    @DELETE("exams/{examId}/users/{userId}")
    suspend fun deleteExamStudent(
        @Path("examId") examId: Int,
        @Path("userId") userId: Int
    ): Response<Exam>

    @DELETE("exams/{examId}/admins/{userId}")
    suspend fun deleteExamAdmin(
        @Path("examId") examId: Int,
        @Path("userId") userId: Int
    ): Response<Exam>

    @DELETE("exams/{examId}")
    suspend fun deleteExam(@Path("examId") examId: Int): Response<Exam>


}