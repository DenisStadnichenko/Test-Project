package com.example.testproject.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *
 * collectWhen* block
 *
 * */

inline fun <T> Flow<T>.collectWhenCreated(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launchWhenCreated {
        collectLatest {
            action.invoke(it)
        }
    }
}

inline fun <T> Flow<T>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launchWhenStarted {
        collectLatest {
            action.invoke(it)
        }
    }
}

inline fun <T> Flow<T>.collectWhenResumed(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launchWhenResumed {
        collectLatest {
            action.invoke(it)
        }
    }
}

inline fun <T> Flow<T>.collectOnceWhenResumed(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launchWhenResumed {
        collectLatest {
            action.invoke(it)
            cancel("collectOnceWhenResumed")
        }
    }
}

/**
 *
 * collect with LifecycleOwner block
 *
 * */

inline fun <T> Flow<T>.collect(
    lifecycleOwner: LifecycleOwner,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline action: suspend (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launch(coroutineContext) {
        collectLatest {
            action.invoke(it)
        }
    }
}

inline fun <T> Flow<T>.collectOnce(
    lifecycleOwner: LifecycleOwner,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline action: suspend (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launch(coroutineContext) {
        collectLatest {
            action.invoke(it)
            cancel("collectOnce")
        }
    }
}

/**
 *
 * collect with CoroutineScope block
 *
 * */

inline fun <T> Flow<T>.collect(
    coroutineScope: CoroutineScope,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline action: suspend (T) -> Unit
): Job {
    return coroutineScope.launch(coroutineContext) {
        collectLatest {
            action.invoke(it)
        }
    }
}

inline fun <T> Flow<T>.collectOnce(
    coroutineScope: CoroutineScope,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline action: suspend (T) -> Unit
): Job {
    return coroutineScope.launch(coroutineContext) {
        collectLatest {
            action.invoke(it)
            cancel("collectOnce")
        }
    }
}

fun <T> safeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
