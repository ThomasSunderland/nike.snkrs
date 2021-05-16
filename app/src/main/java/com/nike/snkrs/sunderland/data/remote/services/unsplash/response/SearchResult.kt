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
 * An Unsplash web service search method response includes 0 or more search results
 *
 * @author Thomas Sunderland. 2021 MAY 15
 */
data class SearchResult(
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String,
    @SerializedName("alt_description") val altDescription: String,
    @SerializedName("urls") val urls: SearchResultUrls,
    @SerializedName("user") val user: SearchResultUser
)
