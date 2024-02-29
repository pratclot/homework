package ch.protonmail.usecase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Named

internal const val DATE_TIME_FORMATTER = "DATE_TIME_FORMATTER"
private const val DATE_TIME_FORMAT = "yyyy-MM-dd • HH:mm"

fun createDateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)

@Module
@InstallIn(SingletonComponent::class)
class DateTimeModule {

    @Named(DATE_TIME_FORMATTER)
    @Provides
    fun provideDayTimeFormatter(): DateTimeFormatter = createDateTimeFormatter()
}
