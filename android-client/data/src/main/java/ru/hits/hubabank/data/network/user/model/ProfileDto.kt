package ru.hits.hubabank.data.network.user.model

import kotlinx.serialization.Serializable
import ru.hits.hubabank.data.network.auth.model.FullNameDto
import ru.hits.hubabank.domain.user.model.Profile

@Serializable
internal data class ProfileDto(
    val id: String,
    val fullNameDto: FullNameDto,
    val phone: String, // В Gateway не забыть убрать остальные поля!!!
)

internal fun ProfileDto.toDomain(): Profile {
    return Profile(
        userId = id,
        name = "${fullNameDto.firstName} ${fullNameDto.secondName}" +
                if (fullNameDto.thirdName == null) "" else ' ' + fullNameDto.thirdName,
        phone = phone,
    )
}
