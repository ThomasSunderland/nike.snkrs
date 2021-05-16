/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.util


//region import directives

import androidx.annotation.CheckResult

//endregion import directives


//region helper types

/**
 * Used to obtain caller class and method info
 * @property className Class of the calling code
 * @property methodName Class::Method of the calling code
 */
data class CallerInfo(val className: String = "Unknown", val methodName: String = "Unknown")
//endregion helper types


//region helper functions

/**
 * Retrieves the caller class and method name to help with logging
 * @return Caller class and method name (or unknown if unable to obtain)
 */
@get:CheckResult
val callerInfo: CallerInfo
    get() {
        // return value
        var callerInfo = CallerInfo()

        //@formatter:off
        try {
            // walk the stacktrace to try and determine the caller info
            for (index in 1 until Thread.currentThread().stackTrace.size) {
                val stackTraceElement = Thread.currentThread().stackTrace[index]
                if ("ExceptionHandlingKt" !in stackTraceElement.className && "java.lang.Thread" !in stackTraceElement.className) {
                    callerInfo = CallerInfo(stackTraceElement.className.substringAfterLast("."), stackTraceElement.methodName)
                    break
                }
            }
        } catch (ex: Exception) { /* swallow exception */ }
        //@formatter:on

        // return to caller
        return callerInfo
    }
//endregion helper functions


//region exception handling helper functions

/**
 * Common try/catch boilerplate code for the purpose of reducing typed code
 */
inline fun tryCatch(body: () -> Unit) {
    try {
        body()
    } catch (ex: Exception) {
        Logger.w(ex)
    }
}

/**
 * try/catch with method entry/exit logging
 */
//@formatter:off
inline fun tryCatchWithLogging(body: () -> Unit, methodParams: List<Any> = listOf()) {
    // get caller info
    val (_, callerMethodName) = callerInfo

    // log entry
    Logger.i("Entered $callerMethodName(${methodParams.joinToString()})")

    try {
        body()
    } catch (ex: Exception) {
        Logger.w(ex)
    } finally {
        // log exit
        Logger.i("Exiting $callerMethodName(${if (methodParams.isNotEmpty()) "..." else ""})")
    }
}
//@formatter:on
//endregion exception handling helper functions