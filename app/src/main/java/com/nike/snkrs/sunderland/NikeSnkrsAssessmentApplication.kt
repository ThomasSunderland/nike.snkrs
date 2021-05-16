@file:Suppress("unused")

/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland


//region import directives

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.nike.snkrs.sunderland.data.local.EntityDatabase
import com.nike.snkrs.sunderland.util.Logger
import com.nike.snkrs.sunderland.util.ioThread
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * Shorter alias that can be used in place of the longer app class name
 * Note: not sure why, but this generates a lint unused warning even though it is indeed used (todo: circle back at some point)
 */
typealias App = NikeSnkrsAssessmentApplication


/**
 * Application class - Main entry point
 * @author Thomas Sunderland, 2021 MAY 09
 */
class NikeSnkrsAssessmentApplication : Application() {

    //region companion object

    companion object {

        /**
         * Reference to the application context
         */
        lateinit var appContext: Context

        /**
         * Reference to our application configuration
         */
        val config by lazy { Firebase.remoteConfig }
    }
    //endregion companion object


    //region Application class overrides

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     */
    override fun onCreate() {
        //@formatter:off
        tryCatchWithLogging({
            // log app version
            Logger.i("Nike SNKRS Coding Assessment application started: ${BuildConfig.VERSION_NAME}")

            // call into base class implementation
            super.onCreate()

            // set our context
            appContext = applicationContext

            // initialize firebase
            // note: this is required prior to using the remote config service
            FirebaseApp.initializeApp(this)

            // set our configuration up
            // note: the minimum fetch threshold could be higher depending on what we make configurable
            with (config) {
                setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 300 })
            }

            // load our local data store with data so that it is available to us when displaying the first screen
            ioThread { EntityDatabase.getInstance(this).runInTransaction {  } }
        })
        //@formatter:on
    }
    //endregion Application class overrides
}