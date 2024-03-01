package com.blink.blinkid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blink.blinkid.LoginRequest
import com.blink.blinkid.LoginResponse
import com.blink.blinkid.commons.LocalDataStore
import com.blink.blinkid.commons.NetworkResult
import com.blink.blinkid.model.Constants
import com.blink.blinkid.repo.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val localDataStore: LocalDataStore
) : ViewModel() {


    private val _loginResponse =
        MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Initial)
    val loginResponse: StateFlow<NetworkResult<LoginResponse>> = _loginResponse.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(LoginRequest(email, password)).collect {
                _loginResponse.value = it
                if (it is NetworkResult.Success) {
                    localDataStore.saveObject(Constants.USER, it.body?.user)
                    localDataStore.saveBoolean(Constants.IS_LOGGED_IN, true)
                    localDataStore.saveString(Constants.TOKEN, it.body?.token ?: "")
                }
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return localDataStore.getBoolean(Constants.IS_LOGGED_IN, false)
    }


}