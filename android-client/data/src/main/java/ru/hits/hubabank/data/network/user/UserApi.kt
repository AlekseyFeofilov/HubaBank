package ru.hits.hubabank.data.network.user

import retrofit2.http.GET
import ru.hits.hubabank.data.network.user.model.ProfileDto

internal interface UserApi {

    @GET("users/my")
    suspend fun getProfile(): ProfileDto
}
