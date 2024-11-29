package com.example.tyme.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    val onErrorResponse = MutableLiveData<String>()
    val onShowLoading = MutableLiveData<Boolean>()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO + exceptionHandler

    //handle exception for coroutine
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Exception handled: ${throwable.localizedMessage}")
    }

    protected val scope = CoroutineScope(coroutineContext)

    protected var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}