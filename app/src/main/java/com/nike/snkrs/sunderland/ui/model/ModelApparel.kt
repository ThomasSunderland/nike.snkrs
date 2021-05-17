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
import com.nike.snkrs.sunderland.data.local.entities.Apparel
import com.nike.snkrs.sunderland.data.remote.services.unsplash.UnsplashServiceHelper
import com.nike.snkrs.sunderland.data.remote.services.unsplash.accessKey_config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

//endregion import directives


/**
 * Model for "apparel" data, note: this is basically our "apparel repository"
 * @author Thomas Sunderland. 2021 MAY 11
 */
class ModelApparel private constructor() {

    //region companion object

    companion object {

        /**
         * Single instance of this class
         */
        val instance by lazy { ModelApparel() }
    }
    //endregion companion object


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
        emit(UnsplashServiceHelper.searchPhotos(accessKey, "nike%20apparel"))
    }.map { results -> results.map { result -> Apparel(resource = result.urls.regular, source = result.user.name) }
    }.flowOn(Dispatchers.IO)
    //@formatter:on
    //endregion data members


    //region properties

    /**
     * Observable collection of apparel data
     */
    //@formatter:off
    val apparel: Flow<List<Apparel>>
        get() = localData.combine(remoteData) { local, remote -> local + remote }
    //@formatter:on
    //endregion properties
}