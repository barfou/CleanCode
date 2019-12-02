package com.example.cleancodingtp.com.example.cleancodingtp.data.networking.api

import com.example.cleancodingtp.com.example.cleancodingtp.data.Model.Character
import com.example.cleancodingtp.com.example.cleancodingtp.data.Model.PaginatedResult
import retrofit2.http.GET
import com.example.cleancodingtp.com.example.cleancodingtp.data.networking.api.CharacterApi.Companion
import retrofit2.Response
import retrofit2.http.Query

interface CharacterApi {

    @GET(Companion.GET_ALL_CHARACTER_PATH)
        suspend fun getAllCharacter(
        @Query("page") page :Int

    ) : Response<PaginatedResult<Character>>

    companion object {
        const val GET_ALL_CHARACTER_PATH = "character/"
    }
}