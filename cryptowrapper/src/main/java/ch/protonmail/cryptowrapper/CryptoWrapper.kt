package ch.protonmail.cryptowrapper

private const val E_DECRYPTION_FAILED = "E_DECRYPTION_FAILED"

interface CryptoWrapper {
    suspend fun decrypt(msg: String): Result<String>
    suspend fun encrypt(msg: String): Result<String>
    suspend fun String.decrypt(): String = decrypt(this).getOrNull() ?: E_DECRYPTION_FAILED
}
