package com.bosha.notespersistencesample.presentation.utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchInWhenStarted(lifecycleCoroutineScope: LifecycleCoroutineScope): Job =
    lifecycleCoroutineScope.launchWhenStarted {
    collect() // tail-call
}