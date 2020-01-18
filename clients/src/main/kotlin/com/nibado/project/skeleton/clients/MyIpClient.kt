package com.nibado.project.skeleton.clients

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.RuntimeException

class MyIpClient {
    private val client = OkHttpClient()
    private val mapper = ObjectMapper().registerKotlinModule()

    fun ip() : String {
        val request = Request.Builder()
            .url("https://api.ipify.org?format=json")
            .build()

        val response = client.newCall(request).execute()

        return response.body
            ?.let { mapper.readValue<Response>(it.byteStream()).ip }
            ?: throw RuntimeException("oops")
    }

    private data class Response(val ip: String)
}