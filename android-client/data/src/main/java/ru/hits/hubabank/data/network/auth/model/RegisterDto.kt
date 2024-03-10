package ru.hits.hubabank.data.network.auth.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.domain.auth.model.RegistrationModel

@Serializable
internal data class RegisterDto(
    val fullName: FullNameDto,
    val password: String,
    val phone: String,
)

internal fun RegistrationModel.toDto(): RegisterDto {
    return RegisterDto(
        fullName = FullNameDto(
            firstName = firstName,
            secondName = secondName,
            thirdName = thirdName,
        ),
        password = password,
        phone = phone,
    )
}
