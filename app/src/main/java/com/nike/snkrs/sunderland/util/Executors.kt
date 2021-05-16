/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nike.snkrs.sunderland.util


//region import directives

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

//endregion import directives


//region variables

/**
 * Used for io/database work
 */
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Used for executing work over a network
 */
private val NETWORK_EXECUTOR = Executors.newSingleThreadExecutor()
//endregion variables


//region functions

/**
 * Utility method to run blocks on the main thread, used for UI work
 */
fun mainThread(f: () -> Unit) {
    Handler(Looper.getMainLooper()).post(f)
}

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

/**
 * Utility method to run blocks on a dedicated background thread, used for network work.
 */
fun networkThread(f: () -> Unit) {
    NETWORK_EXECUTOR.execute(f)
}
//endregion functions