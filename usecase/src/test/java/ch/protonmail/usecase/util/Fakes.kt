package ch.protonmail.usecase.util

import ch.protonmail.cryptowrapper.CryptoWrapper

object CryptoWrapperFake : CryptoWrapper {
    override suspend fun decrypt(msg: String): Result<String> {
        return Result.success(msg)
    }

    override suspend fun encrypt(msg: String): Result<String> {
        return decrypt(msg)
    }
}

object DummyToMakeKtlintCalmDown
