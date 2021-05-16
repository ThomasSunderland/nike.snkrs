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
 * Root of the Unsplash web service search method response
 *
 * @author Thomas Sunderland. 2021 MAY 15
 */
data class SearchResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<SearchResult>
)
