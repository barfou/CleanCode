package com.example.cleancodingtp.com.example.cleancodingtp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.cleancodingtp.com.example.cleancodingtp.data.networking.HttpClientManager
import com.example.cleancodingtp.com.example.cleancodingtp.data.networking.api.CharacterApi
import com.example.cleancodingtp.com.example.cleancodingtp.data.networking.datasource.CharacterDataSource
import kotlinx.coroutines.CoroutineScope
import com.example.cleancodingtp.com.example.cleancodingtp.data.Model.Character
import com.example.cleancodingtp.com.example.cleancodingtp.data.networking.createApi

private class CharacterRepositoryImpl(
    private val api: CharacterApi
) : CharacterRepository {
    private val paginationConfig = PagedList.Config
        .Builder()
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()
    override fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Character>> {
        return LivePagedListBuilder(
            CharacterDataSource.Factory(api, scope),
            paginationConfig
        ).build()
    }
}
interface CharacterRepository {

    fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Character>>
    
    companion object {
        val instance: CharacterRepository by lazy {
            CharacterRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }
}