package ch.protonmail.android.protonmailtest.crypto

import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.common.IO_DISPATCHER
import ch.protonmail.cryptowrapper.CryptoWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class CryptoWrapperImpl @Inject constructor(
    private val cryptoLib: CryptoLib,
    @Named(IO_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) : CryptoWrapper {

    override suspend fun decrypt(msg: String): Result<String> = withContext(dispatcher) {
        cryptoLib.decrypt(msg)
    }

    override suspend fun encrypt(msg: String): Result<String> = withContext(dispatcher) {
        cryptoLib.encrypt(msg)
    }
}
