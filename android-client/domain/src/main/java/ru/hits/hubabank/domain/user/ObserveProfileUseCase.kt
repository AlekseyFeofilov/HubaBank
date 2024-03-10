package ru.hits.hubabank.domain.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hits.hubabank.domain.core.ObservingUseCase
import ru.hits.hubabank.domain.user.model.Profile
import javax.inject.Inject

class ObserveProfileUseCase @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
) : ObservingUseCase<Unit, Profile?> {

    override fun execute(param: Unit): Flow<Result<Profile?>> {
        return userLocalDataSource.observeProfile().map { Result.success(it) }
    }
}
