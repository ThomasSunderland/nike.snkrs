/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view

/**
 * Encapsulates the data used for display by our AdapterData class
 * @author Thomas Sunderland. 2021 MAY 14
 */
data class AdapterDataItem(
    /**
     * Unique identifier
     */
    val id: Int,

    /**
     * Resource, could be a local drawable resource received from a Room query or it could be
     * a link to a remote resource (client to perform check to determine which it is)
     * Note: could add a type to make this clearer, but time is short
     */
    val resource: String,

    /**
     * Source (e.g. site where the resource was acquired or content creator, etc.)
     */
    val source: String
)