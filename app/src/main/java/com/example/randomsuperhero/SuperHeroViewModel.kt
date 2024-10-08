package com.example.randomsuperhero

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.random.Random

interface SuperHeroApi {
    @GET("{id}")
    suspend fun getSuperHero(
        @Path("id") id: Int
    ): SuperHero

    companion object {
        private const val BASE_URL = "https://superheroapi.com/api/"

        fun create(apiKey: String): SuperHeroApi {
            return Retrofit.Builder()
                .baseUrl("$BASE_URL$apiKey/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SuperHeroApi::class.java)
        }
    }
}

class SuperHeroViewModel: ViewModel() {
    var loading by mutableStateOf(false)
    var name by mutableStateOf("")
    var firstAppearance by mutableStateOf("")
    var publisher by mutableStateOf("")
    var alignment by mutableStateOf("")
    var imgUrl by mutableStateOf("")
    var error by mutableStateOf("")
    private val API_KEY: String = ""
    private val superHeroApi = SuperHeroApi.create(API_KEY)

    //Handle button press
    fun handleButtonPress() {
        if (API_KEY===""){
            error="No API KEY FOUND!!!"
        }
        else{
            loading=true
            randomize()}
    }

    // Randomize SuperHero ID between 1 and 731
    private fun randomize() {
        val number: Int = Random.nextInt(1, 732)
        fetchData(number)
    }

    // Fetch data using Retrofit and Coroutines
    private fun fetchData(number: Int) {
        viewModelScope.launch {
            try {
                val superHero = withContext(Dispatchers.IO) {
                    superHeroApi.getSuperHero(number)
                }
                Log.d("SuperHero Data", superHero.toString())

                name = superHero.name
                firstAppearance = superHero.biography.firstAppearance ?: "N/A"
                publisher = superHero.biography.publisher
                alignment = superHero.biography.alignment
                imgUrl = superHero.image.url
            } catch (e: retrofit2.HttpException) {
                error = "HTTP Error: ${e.code()} - ${e.message()}"
                Log.d("error1", error)
                loading = false
            } catch (e: Exception) {
                error = "Can't get SuperHero data! :( ${e.message}"
                Log.d("error2", error)
                loading = false
            }
            loading = false
        }
    }
}