/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.util


//region import directives

import android.util.Log

import androidx.annotation.CheckResult

//endregion import directives


/**
 * Logging helper class
 */
object Logger {

    //region constants

    /**
     * Used as a fallback during logging
     */
    private val TAG = Logger::class.java.simpleName
    //endregion constants


    //region private properties

    /**
     * Retrieves the caller class name to for automatically setting the Log tag
     * @return Caller class name
     */
    @get:CheckResult
    private val callerClassName: String
        get() {
            // return value
            var callerClassName = Logger::class.java.simpleName

            //@formatter:off
            try {
                // walk the stacktrace to try and determine the caller class
                for (index in 1 until Thread.currentThread().stackTrace.size) {
                    val stackTraceElement = Thread.currentThread().stackTrace[index]
                    if ("Logger" !in stackTraceElement.className && "java.lang.Thread" !in stackTraceElement.className) {
                        callerClassName = stackTraceElement.className.substringAfterLast(".")
                        break
                    }
                }
            } catch (ex: Exception) { Log.w(TAG, ex) }
            //@formatter:on

            // return to caller
            return callerClassName
        }
    //endregion private methods


    //region public methods

    /**
     * Logs a debug message to logcat
     * @param message Message to write to the log
     */
    fun d(message: String) {
        try {
            Log.d(callerClassName, message)
        } catch (ex: Exception) {
            Log.w(TAG, ex)
        }
    }

    /**
     * Logs an informational message to logcat
     * @param message Message to write to the log
     */
    fun i(message: String) {
        try {
            Log.i(callerClassName, message)
        } catch (ex: Exception) {
            Log.w(TAG, ex)
        }
    }

    /**
     * Logs a warning to logcat
     * @param throwable Exception to write to the log
     */
    fun w(throwable: Throwable) {
        try {
            Log.w(callerClassName, throwable)
        } catch (ex: Exception) {
            Log.w(TAG, ex)
        }
    }
    //endregion public methods
}
