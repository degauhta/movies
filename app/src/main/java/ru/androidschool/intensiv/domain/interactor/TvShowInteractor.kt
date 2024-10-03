package ru.androidschool.intensiv.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.androidschool.intensiv.domain.repository.TvShowsRepository
import ru.androidschool.intensiv.models.domain.Movie

class TvShowInteractor(
    private val tvShowsRepository: TvShowsRepository
) {

    suspend fun getTvShowFlow(): Flow<List<Movie>> {
        return tvShowsRepository.getTvShowFlow()
    }

    suspend fun updateTvShow() {
        tvShowsRepository.updateTvShow()
    }
}
