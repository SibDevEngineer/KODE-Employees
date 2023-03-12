package com.example.kodeemployees.data.api

import com.example.kodeemployees.data.dto.ResponseDto
import retrofit2.http.GET

/** Описание интерфейса реализации запроса к api */
interface KodeApi {

    @GET("users")
    suspend fun getMockUsers(): ResponseDto
}