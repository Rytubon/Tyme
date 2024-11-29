package com.example.tyme.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.tyme.models.Result
import com.example.tyme.models.UserDetail
import com.example.tyme.service.repositories.UserRepository
import com.example.tyme.utils.BaseViewModel
import kotlinx.coroutines.launch

class UserDetailViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val onUserDetail = MutableLiveData<UserDetail>()

    fun getUserDetail(userName: String) {
        job = scope.launch {
            onShowLoading.postValue(true)
            when (val response =
                userRepository.getUserDetailOrCache(userName)
            ) {
                is Result.Failure -> {
                    onShowLoading.postValue(false)
                    onErrorResponse.postValue(response.exception.message)
                }

                is Result.Success -> {
                    response.data?.let {
                        onShowLoading.postValue(false)
                        onUserDetail.postValue(it)
                    }
                }
            }
        }
    }
}