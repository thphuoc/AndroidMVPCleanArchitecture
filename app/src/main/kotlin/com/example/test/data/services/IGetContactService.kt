package com.example.test.data.services

import com.example.test.data.dao.PersonDAO
import com.example.test.data.dao.base.PagingDataDAO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IGetContactService {
    @GET("api/users")
    fun getContacts(@Query("page") page: Int) : Single<PagingDataDAO<PersonDAO>>
}