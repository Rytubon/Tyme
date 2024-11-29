package com.example.tyme

import com.example.tyme.models.Result
import com.example.tyme.service.client.ApiService
import com.example.tyme.service.client.local.dao.AppDatabase
import com.example.tyme.service.repositories.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class UserRepositoryTest {

    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetUser_EmptyList(): Unit = runBlocking {
        Mockito.`when`(apiService.getUsers(1, 10)).thenReturn(
            Response.success(mutableListOf())
        )
        val sut = UserRepositoryImpl(apiService, appDatabase)
        val result = sut.getUsers(1, 10)
        Assert.assertEquals(true, result is Result.Success)
        Assert.assertEquals(0, (result as Result.Success).data?.size)
    }
}