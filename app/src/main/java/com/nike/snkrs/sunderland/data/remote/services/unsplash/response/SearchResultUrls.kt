/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.data.remote.services.unsplash.response


//region import directives

import com.google.gson.annotations.SerializedName

//endregion import directives


/**
 * Each Unsplash web service search method response includes 0 or more search results
 * with each search result consisting of a "urls" object
 *
 * @author Thomas Sunderland. 2021 MAY 15
 */
data class SearchResultUrls(
    @SerializedName("raw") val raw: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String
)
