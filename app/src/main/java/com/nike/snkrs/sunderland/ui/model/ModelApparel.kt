/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.model


//region import directives

import android.content.Context
import androidx.work.*
import com.nike.snkrs.sunderland.App
import com.nike.snkrs.sunderland.data.local.EntityDatabase
import com.nike.snkrs.sunderland.data.local.entities.Apparel
import com.nike.snkrs.sunderland.data.remote.services.unsplash.UnsplashServiceHelper
import com.nike.snkrs.sunderland.data.remote.services.unsplash.accessKey_config
import com.nike.snkrs.sunderland.data.remote.services.unsplash.secretKey_config
import com.nike.snkrs.sunderland.util.Logger
import com.nike.snkrs.sunderland.util.tryCatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

//endregion import directives


/**
 * Model for "apparel" data, note: this is basically our "apparel repository"
 * @author Thomas Sunderland. 2021 MAY 11
 */
class ModelApparel {

    //region data members

    /**
     * Local apparel data from our pre-loaded Room database
     */
    private val localData = EntityDatabase.getInstance(App.appContext).apparelDao().queryAll()

    /**
     * Remote apparel data from our call to the Unsplash service
     * Note: data does not always seem to exactly match our search params
     */
    //@formatter:off
    private val remoteData: Flow<List<Apparel>> = flow {
        // retrieve (and wait for) the unsplash service developer access key from firebase config
        var accessKey = ""
        App.config.fetchAndActivate().addOnCompleteListener {
            accessKey = App.config.getString(accessKey_config)
        }.await()

        // next query the unsplash service
        val searchResults = UnsplashServiceHelper.searchPhotos(accessKey, "nike%20apparel")

        // next transform the response from a collection of search result to a collection of Apparel instances
        val searchResultsAsApparel = mutableListOf<Apparel>()
        searchResults.forEach { searchResult ->
            searchResultsAsApparel.add(Apparel(resource = searchResult.urls.regular, source = searchResult.user.name))
        }

        // last, emit the results
        emit(searchResultsAsApparel)
    }
    //@formatter:on
    //endregion data members


    //region properties

    /**
     * Observable collection of apparel data
     */
    //@formatter:off
    val apparel: Flow<List<Apparel>>
        get() = localData.combine(remoteData) { local, remote -> listOf(local, remote).flatten() }
    //@formatter:on

    /**
     * Alternative observable collection of apparel data where the remote data is pulled from a WorkManager
     * Work request and inserted into the local data store which then flows back through our flow enabled query
     *
     * Note: this has the upside of allowing WorkManager to make an intelligent decision on when to run
     * the remote request based on whether or not the device has connectivity
     */
    //@formatter:off
    val apparelAlt: Flow<List<Apparel>> = localData
    //@formatter:on
    //endregion properties


    //region initialization block

    init {
        // query for remote apparel data and combine it with our local apparel data
        App.config.fetchAndActivate().addOnCompleteListener {
            tryCatch {
                // fetch unsplash keys from firebase remote config
                val accessKey = App.config.getString(accessKey_config)
                val secretKey = App.config.getString(secretKey_config)

                // submit work request for remote apparel data (this will wait for connectivity)
                //@formatter:off
                with(WorkManager.getInstance(App.appContext)) {
                    // set work constraints (we require network connectivity to complete this work)
                    val workConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                    val workInputData = workDataOf(accessKey_config to accessKey, secretKey_config to secretKey)
                    enqueue(OneTimeWorkRequestBuilder<ApparelDataWorker>()
                        .setConstraints(workConstraints)
                        .setInputData(workInputData)
                        .build())
                }
                //@formatter:on
            }
        }
    }
    //endregion initialization block


    //region remote worker used to request apparel data

    /**
     * ApparelData Worker used to request apparel data from a remote source
     */
    //@formatter:off
    class ApparelDataWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

        /**
         * A suspending method to do your work. This function runs on the coroutine context specified by [coroutineContext].
         * <p>
         * A CoroutineWorker is given a maximum of ten minutes to finish its execution and return a
         * [ListenableWorker.Result]. After this time has expired, the worker will be signalled to stop.
         *
         * @return The [ListenableWorker.Result] of the result of the background work; note that
         * dependent work will not execute if you return [ListenableWorker.Result.failure]
         */
        override suspend fun doWork(): Result {
            // return value
            var result = Result.success()

            try {
                // extract out our service keys
                inputData.getString(accessKey_config)?.let {
                    // call the unsplash web service
                    Logger.i("Calling Unsplash web service for additional apparel data...")
                    val searchResults = UnsplashServiceHelper.searchPhotos(it, "nike apparel")

                    // transform the results into a collection of Apparel objects
                    val searchResultsAsApparel = mutableListOf<Apparel>()
                    searchResults.forEach { searchResult ->
                        searchResultsAsApparel.add(Apparel(resource = searchResult.urls.regular, source = searchResult.user.name))
                    }

                    // insert the results into our local database
                    // this will trigger notification to the view model that the data has changed
                    if (searchResultsAsApparel.isNotEmpty()) EntityDatabase.getInstance(applicationContext).apparelDao().insert(searchResultsAsApparel)
                }
            } catch(ex: Exception) {
                Logger.w(ex)
                result = Result.failure()
            }

            // return to caller
            return result
        }
    }
    //@formatter:on
    //region remote worker used to request apparel data
}