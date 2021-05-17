/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.viewmodel


//region import directives

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nike.snkrs.sunderland.ui.view.callbacks.AdapterDataCallback

//endregion import directives


/**
 * Abstract ViewModel
 * @author Thomas Sunderland. 2021 MAY 13
 */
abstract class ViewModelBase : ViewModel(), AdapterDataCallback {

    //region properties

    /**
     * Stores the current position of the view pager
     */
    val currentPosition = MutableLiveData(0)

    /**
     * Flag indicating whether or not this is the first time this fragment has been displayed
     * Note: Used to auto-scroll the carousel of views to the center-most item
     */
    val firstTimeThrough = MutableLiveData(true)
    //endregion properties
}