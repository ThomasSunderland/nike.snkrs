/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view

/**
 * Encapsulates a piece of remote data used for display by our remote adapter class (AdapterRemoteData)
 * @author Thomas Sunderland. 2021 JUN 02
 */
data class AdapterRemoteDataItem(

    /**
     * Unique identifier
     */
    val id: Int,

    /**
     * Link to a remote resource
     */
    val resource: String,

    /**
     * Source (e.g. site where the resource was acquired or content creator, etc.)
     */
    val source: String
)