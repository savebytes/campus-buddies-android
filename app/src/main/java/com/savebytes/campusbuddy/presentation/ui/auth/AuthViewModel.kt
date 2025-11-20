package com.savebytes.campusbuddy.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.savebytes.campusbuddy.data.remote.dto.response.HomeResponse
import com.savebytes.campusbuddy.data.repository.AuthRepository
import com.savebytes.campusbuddy.domain.model.UserData
import com.savebytes.campusbuddy.presentation.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserData) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = "AuthViewModel"

    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
    val authState: LiveData<AuthState> = _authState

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _movieResponse = MutableLiveData<HomeResponse>()
    val movieResponse : LiveData<HomeResponse> = _movieResponse

    init {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun emailSignUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = authRepository.emailSignUp(email, password, name)
            when(response.status){
                Status.SUCCESS -> {
                    _authState.value = AuthState.Success(response.data?.user!!)
                    _currentUser.value = authRepository.getCurrentUser()
                }
                Status.ERROR -> {
                    _authState.value = AuthState.Error(response.message ?: "Unknown error")
                }
                Status.LOADING -> {
                    _authState.value = AuthState.Loading
                }
            }
        }
    }

    fun emailSignIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = authRepository.emailSignIn(email, password)
            when(response.status){
                Status.SUCCESS -> {
                    _authState.value = AuthState.Success(response.data?.user!!)
                    _currentUser.value = authRepository.getCurrentUser()
                }
                Status.ERROR -> {
                    _authState.value = AuthState.Error(response.message ?: "Unknown error")
                }
                Status.LOADING -> {
                    _authState.value = AuthState.Loading
                }
            }
        }
    }

    fun googleSignIn(idToken: String, email: String? = null) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = authRepository.googleSignIn(idToken, email)
            when(response.status){
                Status.SUCCESS -> {
                    _authState.value = AuthState.Success(response.data?.user!!)
                    _currentUser.value = authRepository.getCurrentUser()
                }
                Status.ERROR -> {
                    _authState.value = AuthState.Error(response.message ?: "Unknown error")
                }
                Status.LOADING -> {
                    _authState.value = AuthState.Loading
                }
            }
        }
    }

    fun getAllMovies() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = authRepository.getMoviesData()
            when(response.status){
                Status.SUCCESS -> {
                    _movieResponse.value = response.data!!
                }
                Status.ERROR -> {
                    _authState.value = AuthState.Error(response.message ?: "Unknown error")
                }
                Status.LOADING -> {
                    _authState.value = AuthState.Loading
                }
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