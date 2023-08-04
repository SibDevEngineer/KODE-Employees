package com.example.kodeemployees.data

import com.example.kodeemployees.data.api.RemoteApi
import com.example.kodeemployees.data.dto.ResponseDto
import com.squareup.moshi.Moshi

class TestRemoteAPI : RemoteApi {
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(ResponseDto::class.java)

    private val filePath = "assets/mock_users_test.json"
    private val fileUrl = javaClass.classLoader?.getResource(filePath)

    override suspend fun getMockUsers(): ResponseDto {
        val jsonResponse = fileUrl?.readText() ?: error(
            "Could not find file $filePath."
        )

        return jsonAdapter.fromJson(jsonResponse)!!
    }
}