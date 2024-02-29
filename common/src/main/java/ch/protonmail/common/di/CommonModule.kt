package ch.protonmail.common.di

import ch.protonmail.common.IO_DISPATCHER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Named(IO_DISPATCHER)
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}
