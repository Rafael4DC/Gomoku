package isel.pdm.gomoku.domain

import isel.pdm.gomoku.domain.user.UserInfo

/**
 * Sum type that represents the state of a I/O operation.
 * @param T the type of the value that results from the I/O operation.
 */
sealed interface IOState<out T> {
    /**
     * The idle state, i.e. the state before the load operation is started.
     */
    data object Idle : IOState<Nothing>

    /**
     * The loading state, i.e. the state while the load operation is in progress.
     */
    data object Loading : IOState<Nothing>

    /**
     * The saving state, i.e. the state while the save operation is in progress.
     */
    data object Saving : IOState<Nothing>

    /**
     * The loaded state, i.e. the state after the load operation has finished.
     * @param value the result of the load operation.
     */
    data class Loaded<T>(val value: Result<T>) : IOState<T>

    /**
     * The saved state, i.e. the state after the save operation has finished.
     * @param value the result of the save operation. If the save operation
     * was successful, the result is the saved value. If the save operation
     * failed, the result is the exception that caused the failure.
     */
    data class Saved<T>(val value: Result<T>) : IOState<T>

}

/**
 * Returns a new [IOState] in the idle state.
 */
fun idle() = IOState.Idle

/**
 * Returns a new [IOState] in the loading state.
 */
fun loading() = IOState.Loading

/**
 * Returns a new [IOState] in the saving state.
 */
fun saving() = IOState.Saving

/**
 * Returns a new [IOState] in the loaded state.
 */
fun <T> loaded(value: Result<T>): IOState<T?> = IOState.Loaded(value)

/**
 * Returns a new [IOState] in the saved state.
 */
fun <T> saved(value: Result<T>) = IOState.Saved(value)

/**
 * Returns a new [IOState] in the loaded state with a successful result.
 */
fun <T> loadSuccess(value: T) = loaded(Result.success(value))

/**
 * Returns a new [IOState] in the loaded state with a failed result.
 */
fun <T> loadFailure(error: Throwable) = loaded<T>(Result.failure(error))

/**
 * Returns a new [IOState] in the saved state with a successful result.
 */
fun <T> saveSuccess(value: T) = saved(Result.success(value))

/**
 * Returns a new [IOState] in the saved state with a failed result.
 */
fun <T> saveFailure(error: Throwable) = saved<T>(Result.failure(error))

/**
 * Returns the result of the I/O operation, if one is available.
 */
fun <T> IOState<T>.getOrNull(): T? = when (this) {
    is IOState.Loaded -> value.getOrNull()
    else -> null
}

/**
 * Returns the result of the I/O operation, if one is available, or throws
 * the exception that caused the operation to fail. If the operation
 * is still in progress, an [IllegalStateException] is thrown.
 */
fun <T> IOState<T>.getOrThrow(): T = when (this) {
    is IOState.Loaded -> value.getOrThrow()
    else -> throw IllegalStateException("No value available")
}

/**
 * Returns the exception that caused the I/O operation to fail, if one is available.
 */
fun <T> IOState<T>.exceptionOrNull(): Throwable? = when (this) {
    is IOState.Loaded -> value.exceptionOrNull()
    else -> null
}
