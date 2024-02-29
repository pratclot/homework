package ch.protonmail.repository.di

import ch.protonmail.repository.TasksRepository
import ch.protonmail.repository.TasksRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun provideRepo(tasksRepositoryImpl: TasksRepositoryImpl): TasksRepository = tasksRepositoryImpl
}
