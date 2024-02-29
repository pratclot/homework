package ch.protonmail.common

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <T, R> Iterable<T>.mapAsync(block: suspend (T) -> R): Iterable<R> = coroutineScope {
    map { async { block(it) } }.awaitAll()
}
