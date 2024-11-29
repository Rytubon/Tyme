package com.example.thong.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.thong.models.Result
import com.example.thong.models.User
import com.example.thong.service.repositories.UserRepository
import com.example.thong.utils.BaseViewModel
import com.example.thong.utils.Constants
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val onUser = MutableLiveData<MutableList<User>>()
    private var users = mutableListOf<User>()
    private var isLoading = false

    init {
        getUsers(true)
    }

    //get users from room if cache is empty, else get from api.With load more,data will be get from api
    fun getUsers(isFirstLoad: Boolean) {
        if (isLoading) return
        job = scope.launch {
            isLoading = true
            onShowLoading.postValue(true)
            when (val response =
                if (isFirstLoad) userRepository.getUsersOrCache(
                    users.size,
                    Constants.PAGE_SIZE
                ) else userRepository.getUsers(users.size, Constants.PAGE_SIZE)
            ) {
                is Result.Failure -> {
                    isLoading = false
                    onErrorResponse.postValue(response.exception.message)
                    onShowLoading.postValue(false)
                }

                is Result.Success -> {
                    isLoading = false
                    response.data?.let {
                        if (isFirstLoad) users.clear()
                        users.addAll(it)
                        onUser.postValue(users.distinct().toMutableList())
                        onShowLoading.postValue(false)
                    }
                }
            }
        }
    }
}