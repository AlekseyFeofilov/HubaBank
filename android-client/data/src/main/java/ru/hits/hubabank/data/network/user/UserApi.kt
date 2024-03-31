package ru.hits.hubabank.data.network.user

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.hits.hubabank.data.network.user.model.ProfileDto

internal interface UserApi {

    @GET("users/my")
    suspend fun getProfile(): ProfileDto

    @POST("users/theme")
    suspend fun saveTheme(@Body isDarkTheme: Boolean)
}
