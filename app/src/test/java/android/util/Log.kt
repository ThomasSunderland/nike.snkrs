/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
@file:JvmName("Log")

package android.util

/**
 * Dummy Log.i method for logging messages at INFO level
 */
fun i(tag: String, msg: String): Int {
    println("INFO: $tag: $msg")
    return 0
}

/**
 * Dummy Log.w method for logging exception at WARN level
 */
fun w(tag: String, t: Throwable): Int {
    println("WARN: $tag: ${t.message}")
    return 0
}