package com.bosha.notespersistencesample.data.utils

import android.util.Log
import kotlin.coroutines.CoroutineContext

fun logError(
    coroutineContext: CoroutineContext? = null,
    throwable: Throwable,
    clazz: Any? = null,
    line: Int = 0,
    ) {
    var fromPrettyPrint = ""
    if (clazz != null) {
        fromPrettyPrint = """
            EXCEPTION
            ----------------------------------------------
            .fromClass(${clazz::class.simpleName}.kt:$line)
            ----------------------------------------------
        """.trimIndent()
    }
    var stackString = """
        |$fromPrettyPrint 
        |cause: ${throwable.localizedMessage}
        |----------------------------------------------
        |context: ${coroutineContext?:""}
        |----------------------------------------------
        |""".trimMargin()

    throwable.stackTrace.forEach { element ->
        stackString += "$element\n"
    }
    Log.e("logStackTrace", stackString)
}