package com.blink.blinkid.repo

import android.util.Log
import com.blink.blinkid.Exam
import com.blink.blinkid.LoginRequest
import com.blink.blinkid.LoginResponse
import com.blink.blinkid.User
import com.blink.blinkid.commons.NetworkResult
import com.blink.blinkid.commons.toErrorMessage
import com.blink.blinkid.model.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExamRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getExams(): Flow<NetworkResult<List<Exam>>> = flow {
        try {
            val response = apiService.getExams()
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.e("ExamRepository", "getExams: ${it.joinToString()}")
                    emit(NetworkResult.Success(it))
                }
            } else {
                Log.e("ExamRepository", "getExams: ${response.errorBody()?.toErrorMessage()}")
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }

    suspend fun addExam(exam: Exam): Flow<NetworkResult<Exam>> = flow {
        try {
            val response = apiService.addExam(exam)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }


    suspend fun updateExam(examId: String, exam: Exam): Flow<NetworkResult<Exam>> = flow {
        try {
            val response = apiService.updateExam(examId, exam)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }

    suspend fun deleteExam(examId: String, userId: String): Flow<NetworkResult<Exam>> = flow {
        try {
            val response = apiService.deleteExam(examId, userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }


    suspend fun addStudentToExam(examId: String, userId: String): Flow<NetworkResult<Exam>> = flow {
        try {
            val response = apiService.addExamStudent(examId, userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }

    suspend fun addAdminToExam(examId: String, userId: String): Flow<NetworkResult<Exam>> = flow {
        try {
            val response = apiService.addExamAdmin(examId, userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }


    suspend fun deleteStudentFromExam(examId: String, userId: String): Flow<NetworkResult<Exam>> =
        flow {
            try {
                val response = apiService.deleteExam(examId, userId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(NetworkResult.Success(it))
                    }
                } else {
                    emit(
                        NetworkResult.Error(
                            response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                        )
                    )
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An error occurred"))
            }
        }


    suspend fun deleteAdminFromExam(examId: String, userId: String): Flow<NetworkResult<Exam>> =
        flow {
            try {
                val response = apiService.deleteExamAdmin(examId, userId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(NetworkResult.Success(it))
                    }
                } else {
                    emit(
                        NetworkResult.Error(
                            response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                        )
                    )
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An error occurred"))
            }
        }

    fun getExam(examId: String): Flow<NetworkResult<Exam>> = flow {
        try {
            val response = apiService.getExam(examId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody()?.toErrorMessage()?.message ?: "An error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }

}