package org.emily.auth.domain.token

interface TokenService<T> {
    suspend fun put(value: T)
    suspend fun get(): T?
    suspend fun clear()
}