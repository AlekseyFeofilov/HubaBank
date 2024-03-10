package ru.hits.hubabank.data.database.auth.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.hits.hubabank.domain.auth.model.Tokens

@Entity(tableName = "Tokens")
internal data class TokensEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val accessToken: String,
    val refreshToken: String,
)

internal fun Tokens.toEntity(): TokensEntity {
    return TokensEntity(
        id = 0,
        accessToken = accessToken.removePrefix("Bearer "),
        refreshToken = refreshToken,
    )
}

internal fun TokensEntity.toDomain(): Tokens {
    return Tokens(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}
