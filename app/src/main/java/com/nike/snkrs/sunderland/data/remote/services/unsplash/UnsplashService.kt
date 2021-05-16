/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.data.remote.services.unsplash


//region import directives

import com.nike.snkrs.sunderland.data.remote.services.unsplash.response.SearchResponse
import com.nike.snkrs.sunderland.data.remote.services.unsplash.response.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

//endregion import directives


/**
 * Unsplash Service Interface ("The most powerful photo engine in the world.")
 * Ref: https://unsplash.com/documentation#search-photos
 *
 * @author Thomas Sunderland. 2021 MAY 11
 */
//@formatter:off
interface UnsplashService {

    /**
     * This is our Unsplash service method for searching photos
     */
    @GET("search/photos")
    suspend fun searchPhotos(@Header("Authorization") accessKey: String,
                             @Query("query") query: String,
                             @Query("page") page: Int = 1,
                             @Query("per_page") itemsPerPage: Int = 10,
                             @Query("order_by") orderBy: String = "relevant",
                             @Query("orientation") orientation: String = "portrait"): Response<SearchResponse>
}
//@formatter:on


/**
 * Unsplash service helper
 */
object UnsplashServiceHelper {

    //region data members

    /**
     * Base URL for the Unsplash service
     */
    private const val baseUrl = "https://api.unsplash.com/"

    /**
     * Retrofit instance w/ base url set to Unsplash domain
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(OkHttpClient())
        .build()

    /**
     * Reference to the Unsplash service
     */
    private val unsplashService = retrofit.create(UnsplashService::class.java)
    //endregion data members


    //region functions

    /**
     * Searches Unsplash for photos
     * Ref: https://unsplash.com/documentation#search-photos
     */
    //@formatter:off
    suspend fun searchPhotos(accessKey: String, query: String, page: Int = 1, itemsPerPage: Int = 10, orderBy: String = "relevant"): List<SearchResult> =
        withContext(Dispatchers.IO) {
            val response = unsplashService.searchPhotos("Client-ID ".plus(accessKey), query, page, itemsPerPage, orderBy)
            if (response.isSuccessful) response.body()?.results ?: emptyList() else emptyList()
        }
    //@formatter:on
    //endregion functions
}