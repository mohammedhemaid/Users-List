package com.example.userlists.usersList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlists.R
import com.example.userlists.api.ApiConstants
import com.example.userlists.api.Resource
import com.example.userlists.api.RestRepository
import com.example.userlists.api.errorresponse.ErrorResponse
import com.example.userlists.usersList.model.UserListsResponse
import com.example.userlists.userdetails.model.UserDetails
import com.example.userlists.userdetails.model.UserDetailsResponse
import com.example.userlists.utils.Event
import com.example.userlists.utils.isInternetAvailable
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.net.SocketTimeoutException

class UsersListViewModel(
    private val context: Context,
    private val restRepository: RestRepository
) : ViewModel() {

    private val _userDetailsList = MutableLiveData<List<UserDetails>>()
    val userDetailsList: LiveData<List<UserDetails>> = _userDetailsList

    private val _notInternet = MutableLiveData<Boolean>()
    val notInternet: LiveData<Boolean> = _notInternet

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _timeOutDialog = MutableLiveData<Int>()
    val timeOutDialog: LiveData<Int> = _timeOutDialog

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    private val userLists = mutableListOf<UserDetails>()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        if (isInternetAvailable(context)) {
            getUsers()
            _notInternet.value = false
        } else {
            _notInternet.value = true
            _progress.value = false
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                _progress.value = true
                handleUserList(restRepository.getUsersId())
            } catch (e: SocketTimeoutException) {
                _timeOutDialog.value = R.string.connection_time_out
                _progress.value = false
            }
        }
    }

    private fun handleUserList(users: Resource<UserListsResponse, ErrorResponse>) {
        when (users) {
            is Resource.Success -> users.data?.let {
                userLists.clear()
                viewModelScope.launch {
                    supervisorScope {
                        try {
                            it.data.asFlow()
                                .map { userId -> restRepository.getUserDetails(userId) }
                                .collect { handleUserDetails(it) }
                            _userDetailsList.postValue(userLists)
                            _progress.value = false
                        } catch (e: Exception) {
                            _progress.value = false
                        }
                    }
                }
            }
            is Resource.DataError -> {
                _toastMessage.value =
                    Event(users.errorData?.error?.message ?: ApiConstants.NETWORK_ERROR)
                _progress.value = false
            }
        }
    }

    private fun handleUserDetails(userDetails: Resource<UserDetailsResponse, ErrorResponse>) {
        when (userDetails) {
            is Resource.Success -> userDetails.data?.let {
                userLists.add(it.userDetails)
            }
            is Resource.DataError -> {
                _toastMessage.value =
                    Event(userDetails.errorData?.error?.message ?: ApiConstants.NETWORK_ERROR)
            }
        }
    }
}