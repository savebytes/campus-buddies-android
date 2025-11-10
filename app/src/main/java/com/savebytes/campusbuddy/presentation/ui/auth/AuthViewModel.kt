package com.savebytes.campusbuddy.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.savebytes.campusbuddy.data.repository.AuthRepositoryImpl
import com.savebytes.campusbuddy.domain.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure
import kotlin.onSuccess

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserData) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
    val authState: LiveData<AuthState> = _authState

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    init {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun emailSignUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.emailSignUp(email, password, name)
            result.isSuccess
            result.onSuccess { response ->
                if (response) {
                    _authState.value = AuthState.Success(response.user!!)
                    _currentUser.value = authRepository.getCurrentUser()
                } else {
                    _authState.value = AuthState.Error(response.message)
                }
            }
            result.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun emailSignIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.emailSignIn(email, password)
            result.onSuccess { response ->
                if (response.success) {
                    _authState.value = AuthState.Success(response.user!!)
                    _currentUser.value = authRepository.getCurrentUser()
                } else {
                    _authState.value = AuthState.Error(response.message)
                }
            }
            result.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun googleSignIn(idToken: String, email: String? = null) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.googleSignIn(idToken, email)
            result.onSuccess { response ->
                if (response.success) {
                    _authState.value = AuthState.Success(response.user!!)
                    _currentUser.value = authRepository.getCurrentUser()
                } else {
                    _authState.value = AuthState.Error(response.message)
                }
            }
            result.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _currentUser.value = null
            _authState.value = AuthState.Idle
        }
    }
}