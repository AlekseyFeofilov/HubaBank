package ru.hits.hubabank.data.database.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.user.model.Profile

@Entity(tableName = "Profile")
internal data class ProfileEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val phone: String,
    val isDarkTheme: Boolean,
)

internal fun Profile.toEntity(): ProfileEntity {
    return ProfileEntity(
        id = userId,
        name = name,
        phone = phone,
        isDarkTheme = isDarkTheme,
    )
}

internal fun ProfileEntity.toDomain(): Profile {
    return Profile(
        userId = id,
        name = name,
        phone = phone,
        isDarkTheme = isDarkTheme,
    )
}
