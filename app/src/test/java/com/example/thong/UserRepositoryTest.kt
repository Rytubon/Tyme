package com.example.thong

import com.example.thong.models.Result
import com.example.thong.models.User
import com.example.thong.models.UserDetail
import com.example.thong.service.client.ApiService
import com.example.thong.service.client.local.dao.AppDatabase
import com.example.thong.service.client.local.dao.UserDao
import com.example.thong.service.repositories.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class UserRepositoryTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiService = Mockito.mock(ApiService::class.java)
        appDatabase = Mockito.mock(AppDatabase::class.java)
        userDao = Mockito.mock(UserDao::class.java)
    }

    @Test
    fun testGetUser_Fail(): Unit = runBlocking {
        //given
        val apiService = Mockito.mock(ApiService::class.java)
        val appDatabase = Mockito.mock(AppDatabase::class.java)
        //when
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.error(
                405, ResponseBody.create(null, "")
            )
        )
        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsers(1, 1)
        assertEquals(true, result is Result.Failure)
    }

    @Test
    fun testGetUser_EmptyResponse(): Unit = runBlocking {
        //given
        val apiService = Mockito.mock(ApiService::class.java)
        val appDatabase = Mockito.mock(AppDatabase::class.java)
        val userDao = Mockito.mock(UserDao::class.java)
        //when
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.success(mutableListOf())
        )
        Mockito.`when`(appDatabase.userDao()).thenReturn(userDao)
        Mockito.`when`(userDao.getAllUsers())
            .thenReturn(mutableListOf(User("login", 1, "avatar_url", "html_url")))


        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsers(1, 1)/*val acUser: ArgumentCaptor<User> = ArgumentCaptor.forClass(User::class.java)
        verify(userDao).insertUser(acUser.capture())
        Assert.assertEquals(true, acUser.value==null)*/
        assertEquals(true, userDao.getAllUsers().size == 1)
        assertEquals(true, userDao.getAllUsers().first().id == 1)
        assertEquals(true, result is Result.Success)
        assertEquals(true, (result as Result.Success).data?.isEmpty())
    }

    @Test
    fun testGetUserSuccess_SaveData(): Unit = runBlocking {
        //given
        val apiService = Mockito.mock(ApiService::class.java)
        val appDatabase = Mockito.mock(AppDatabase::class.java)
        val userDao = Mockito.mock(UserDao::class.java)
        //when
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.success(mutableListOf(User("login", 2, "avatar_url", "html_url")))
        )
        Mockito.`when`(appDatabase.userDao()).thenReturn(userDao)
        Mockito.`when`(userDao.getAllUsers())
            .thenReturn(mutableListOf(User("login", 1, "avatar_url", "html_url")))


        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsers(1, 1)
        assertEquals(true, userDao.getAllUsers().size == 1)
        assertEquals(true, userDao.getAllUsers().first().id == 1)
        assertEquals(true, result is Result.Success)
        assertEquals(true, (result as Result.Success).data?.size == 1)
        assertEquals(true, result.data!![0].id == 2)
    }

    @Test
    fun testGetUserSuccess_GetAllUserLocalEmpty(): Unit = runBlocking {
        //given
        val apiService = Mockito.mock(ApiService::class.java)
        val appDatabase = Mockito.mock(AppDatabase::class.java)
        val userDao = Mockito.mock(UserDao::class.java)
        //when
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.success(mutableListOf(User("login", 1, "avatar_url", "html_url")))
        )
        Mockito.`when`(appDatabase.userDao()).thenReturn(userDao)
        Mockito.`when`(userDao.getAllUsers())
            .thenReturn(mutableListOf(User("login", 1, "avatar_url", "html_url")))

        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsers(1, 1)
        assertEquals(true, result is Result.Success)
    }

    @Test
    fun testGetUserSuccess_GetAllUserLocal_SaveUser(): Unit = runBlocking {
        //given
        val apiService = Mockito.mock(ApiService::class.java)
        val appDatabase = Mockito.mock(AppDatabase::class.java)
        val userDao = Mockito.mock(UserDao::class.java)
        //when
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.success(mutableListOf(User("login", 1, "avatar_url", "html_url")))
        )

        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(userDao.getAllUsers())
            .thenReturn(mutableListOf(User("login", 1, "avatar_url", "html_url")))

        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsers(1, 1)
        assertEquals(true, result is Result.Success)
    }

    @Test
    fun testGetUserSuccess_GetAllUserLocal_SaveUser1(): Unit = runBlocking {
        //given
        val apiService = Mockito.mock(ApiService::class.java)
        val appDatabase = Mockito.mock(AppDatabase::class.java)
        val userDao = Mockito.mock(UserDao::class.java)
        //when
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.success(mutableListOf(User("login", 1, "avatar_url", "html_url")))
        )

        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(userDao.getAllUsers())
            .thenReturn(mutableListOf(User("login", 1, "avatar_url", "html_url")))

        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsers(1, 1)
        assertEquals(true, result is Result.Success)
    }

    @Test
    fun testGetUsersOrCache_getAllLocalUserSuccess(): Unit = runBlocking {
        //when
        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(appDatabase.userDao().getAllUsers()).thenReturn(
            mutableListOf(User("login", 1, "avatar_url", "html_url"))
        )

        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsersOrCache(1, 1)
        assertEquals(true, result is Result.Success)
        assertEquals(true, (result as Result.Success).data?.size == 1)
        assertEquals(true, (result as Result.Success).data?.first()?.id == 1)
    }

    @Test
    fun testGetUsersOrCache_UsersEmpty_GetUsersFromApiFail(): Unit = runBlocking {
        //when
        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(appDatabase.userDao().getAllUsers()).thenReturn(
            mutableListOf()
        )
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.error(
                405, ResponseBody.create(null, "")
            )
        )

        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsersOrCache(1, 1)
        assertEquals(true, result is Result.Failure)
    }

    @Test
    fun testGetUsersOrCache_UsersEmpty_GetUsersFromApiSuccess(): Unit = runBlocking {
        //when
        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(appDatabase.userDao().getAllUsers()).thenReturn(
            mutableListOf()
        )
        Mockito.`when`(apiService.getUsers(1, 1)).thenReturn(
            Response.success(mutableListOf(User("login", 1, "avatar_url", "html_url")))
        )
        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUsersOrCache(1, 1)
        assertEquals(true, result is Result.Success)
        assertEquals(true, userDao.getAllUsers().isEmpty())
        assertEquals(true, result is Result.Success)
        assertEquals(true, (result as Result.Success).data?.size == 1)
        assertEquals(true, result.data!![0].id == 1)
    }

    @Test
    fun testGetUsersDetailOrCache_getAllLocalUserSuccess(): Unit = runBlocking {
        //when
        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(appDatabase.userDao().getAllUserDetailByLogin("")).thenReturn(
            mutableListOf(UserDetail("login", 1, "avatar_url", "", "", 1, 10, ""))
        )

        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUserDetailOrCache("")
        assertEquals(true, result is Result.Success)
        assertEquals(true, (result as Result.Success).data?.id == 1)
        assertEquals(true, (result as Result.Success).data?.id == 1)
    }

    @Test
    fun testGetUsersDetailOrCache_UsersEmpty_GetUsersFromApiFail(): Unit = runBlocking {
        //when
        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(appDatabase.userDao().getAllUserDetailByLogin("")).thenReturn(
            mutableListOf()
        )
        Mockito.`when`(apiService.getUserDetail("")).thenReturn(
            Response.error(
                405, ResponseBody.create(null, "")
            )
        )

        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUserDetailOrCache("")
        assertEquals(true, result is Result.Failure)
    }

    @Test
    fun testGetUsersDetailOrCache_UsersEmpty_GetUsersFromApiSuccess(): Unit = runBlocking {
        //when
        Mockito.`when`(appDatabase.userDao()).thenReturn(
            userDao
        )
        Mockito.`when`(appDatabase.userDao().getAllUserDetailByLogin("")).thenReturn(
            mutableListOf()
        )
        Mockito.`when`(appDatabase.userDao().getAllUserDetails()).thenReturn(
            mutableListOf()
        )
        var userDetail = UserDetail("login", 1, "avatar_url", "", "", 1, 10, "")
        Mockito.`when`(apiService.getUserDetail("")).thenReturn(
            Response.success(userDetail)
        )
        //expect
        val userRepository = UserRepositoryImpl(apiService, appDatabase)
        val result = userRepository.getUserDetailOrCache("")
        assertEquals(true, result is Result.Success)
        assertEquals(true, userDao.getAllUserDetails().isEmpty())
        assertEquals(true, result is Result.Success)
        assertEquals(true, (result as Result.Success).data?.id == 1)
        assertEquals(true, result.data?.id == 1)
    }
}
