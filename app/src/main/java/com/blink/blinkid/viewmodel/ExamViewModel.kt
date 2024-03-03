package com.blink.blinkid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blink.blinkid.model.Exam
import com.blink.blinkid.model.User
import com.blink.blinkid.commons.NetworkResult
import com.blink.blinkid.repo.ExamRepository
import com.blink.blinkid.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val examRepository: ExamRepository,
    private val user: User
) : ViewModel() {

    private val _selectedExam = MutableStateFlow<Exam?>(null)
    val selectedExam: StateFlow<Exam?> = _selectedExam.asStateFlow()

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser.asStateFlow()

    private val _exams = MutableStateFlow<NetworkResult<List<Exam>>>(NetworkResult.Initial)
    val exams: StateFlow<NetworkResult<List<Exam>>> = _exams.asStateFlow()

    private val _exam = MutableStateFlow<NetworkResult<Exam>>(NetworkResult.Initial)
    val exam: StateFlow<NetworkResult<Exam>> = _exam.asStateFlow()

    private val _students = MutableStateFlow<NetworkResult<List<User>>>(NetworkResult.Initial)
    val students: StateFlow<NetworkResult<List<User>>> = _students.asStateFlow()


    private val _admins = MutableStateFlow<NetworkResult<List<User>>>(NetworkResult.Initial)
    val admins: StateFlow<NetworkResult<List<User>>> = _admins.asStateFlow()

    init {
        getExams()
    }

    fun getExams() {
        viewModelScope.launch {

            _exams.value = NetworkResult.Loading
            examRepository.getExams().collect {
                _exams.value = it
            }
        }
    }

    fun getStudents() {
        viewModelScope.launch {
            _students.value = NetworkResult.Loading
            userRepository.getStudents().collect {
                _students.value = it
            }
        }
    }

    fun getAdmins() {
        viewModelScope.launch {
            _admins.value = NetworkResult.Loading
            userRepository.getAdmins().collect {
                _admins.value = it
            }
        }
    }

    fun addExam(exam: Exam) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.addExam(exam).collect {
                if (it is NetworkResult.Success) {
                    it.body?.let { exam ->
                        addAdminToExam(exam.id!!, user.id)
                    }
                }
            }
        }
    }

    fun updateExam(examId: Int, exam: Exam) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.updateExam(examId, exam).collect {
                _exam.value = it
            }
        }
    }

    fun addStudentToExam(examId: Int, userId: Int) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.addStudentToExam(examId, userId).collect {
                _exam.value = it
                _selectedExam.value = (it as NetworkResult.Success).body
            }
        }
    }

    private fun addAdminToExam(examId: Int, userId: Int) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.addAdminToExam(examId, userId).collect {
                _exam.value = it
            }
        }
    }

    fun deleteExam(examId: Int) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.deleteExam(examId).collect {
                _exam.value = it
            }
        }
    }

    fun deleteAdminFromExam(examId: Int, userId: Int) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.deleteAdminFromExam(examId, userId).collect {
                _exam.value = it
            }
        }
    }

    fun deleteStudentFromExam(examId: Int, userId: Int) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.deleteStudentFromExam(examId, userId).collect {
                _exam.value = it
                _selectedExam.value = (it as NetworkResult.Success).body
            }
        }
    }


    fun getExam(examId: Int) {
        viewModelScope.launch {
            _exam.value = NetworkResult.Loading
            examRepository.getExam(examId).collect {
                _exam.value = it
            }
        }
    }

    fun setSelectedExam(exam: Exam) {
        _selectedExam.value = exam
    }


}