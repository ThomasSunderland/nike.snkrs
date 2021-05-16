/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.model


//region import directives

import com.nike.snkrs.sunderland.App
import com.nike.snkrs.sunderland.data.local.EntityDatabase
import com.nike.snkrs.sunderland.data.local.entities.Athletes
import com.nike.snkrs.sunderland.data.remote.services.unsplash.UnsplashServiceHelper
import com.nike.snkrs.sunderland.data.remote.services.unsplash.accessKey_config
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

//endregion import directives


/**
 * Model for "athletes" data, note: this is basically our "athletes repository"
 * @author Thomas Sunderland. 2021 MAY 11
 */
class ModelAthletes {

    //region data members

    /**
     * Local athletes data from our pre-loaded Room database
     */
    private val localData = EntityDatabase.getInstance(App.appContext).athletesDao().queryAll()

    /**
     * Remote athletes data from our call to the Unsplash service
     * Note: data does not always seem to exactly match our search params
     */
    //@formatter:off
    private val remoteData: Flow<List<Athletes>> = flow {
        // retrieve (and wait for) the unsplash service developer access key from firebase config
        var accessKey = ""
        App.config.fetchAndActivate().addOnCompleteListener {
            accessKey = App.config.getString(accessKey_config)
        }.await()

        // next query the unsplash service
        val searchResults = UnsplashServiceHelper.searchPhotos(accessKey, "nike%20athletes")

        // next transform the response from a collection of search result to a collection of Athletes instances
        val searchResultsAsAthletes = mutableListOf<Athletes>()
        searchResults.forEach { searchResult ->
            searchResultsAsAthletes.add(Athletes(resource = searchResult.urls.regular, source = searchResult.user.name))
        }

        // last, emit the results
        emit(searchResultsAsAthletes)
    }
    //@formatter:on
    //endregion data members


    //region properties

    /**
     * Observable collection of athletes data
     */
    //@formatter:off
    val athletes: Flow<List<Athletes>>
        get() = localData.combine(remoteData) { local, remote -> listOf(local, remote).flatten() }
    //@formatter:on
    //endregion properties
}