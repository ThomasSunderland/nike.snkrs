/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view.callbacks

/**
 * Used to display an Apparel item
 * @author Thomas Sunderland. 2021 MAY 12
 */
interface AdapterDataCallback {

    /**
     * Click event callback for the featured image
     */
    fun onClick(resource: String)
}