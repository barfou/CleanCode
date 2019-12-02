package com.example.cleancodingtp.com.example.cleancodingtp.data.networking.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.cleancodingtp.com.example.cleancodingtp.data.networking.api.CharacterApi
import com.example.cleancodingtp.com.example.cleancodingtp.data.Model.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDataSource private constructor(
    private val scope: CoroutineScope,
    private  val api: CharacterApi)
    :PageKeyedDataSource<Int, Character>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {

        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllCharacter(page = FIRST_KEY).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                if (params.placeholdersEnabled) callback.onResult(
                    response.results,
                    0,
                    response.information.count,
                    null,
                    if (response.information.next.isNotEmpty()) FIRST_KEY + 1 else null
                ) else callback.onResult(
                    response.results,
                    null,
                    if (response.information.next.isNotEmpty()) FIRST_KEY + 1 else null
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) = Unit

    class Factory(
        private val api: CharacterApi,
        private val scope: CoroutineScope

    ) : DataSource.Factory<Int, Character>() {
        override fun create(): DataSource<Int, Character> =
            CharacterDataSource(
                scope, api
            )

    }

    companion object {
        private const val FIRST_KEY = 1
    }
}