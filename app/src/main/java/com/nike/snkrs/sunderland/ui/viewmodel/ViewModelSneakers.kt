/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.viewmodel


//region import directives

import androidx.lifecycle.viewModelScope
import com.nike.snkrs.sunderland.ui.model.ModelSneakers
import com.nike.snkrs.sunderland.util.tryCatchWithLogging
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

//endregion import directives


/**
 * ViewModel for the Sneakers Fragment
 * @author Thomas Sunderland. 2021 MAY 09
 */
class ViewModelSneakers : ViewModelBase() {

    //region data members

    /**
     * Reference to our data model which acts as our repository
     */
    private val model by lazy { ModelSneakers.instance }
    //endregion data members


    //region properties

    /**
     * Collection of observable sneakers data (local + remote)
     * Note: 5000ms recommendation is from Google to handle screen rotations, etc.
     */
    val sneakers = model.combinedData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    /**
     * Collection of observable sneakers data (local)
     * Note: 5000ms recommendation is from Google to handle screen rotations, etc.
     */
    val localSneakersData = model.localData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    /**
     * Collection of observable sneakers data (remote)
     * Note: 5000ms recommendation is from Google to handle screen rotations, etc.
     */
    val remoteSneakersData = model.remoteData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )
    //endregion properties


    //region adapter callback implementation

    /**
     * Callback for when the user clicks on a featured image
     * Note: no action at this time
     */
    override fun onClick(resource: String) {
        tryCatchWithLogging({
            // _asm nop
        }, listOf(resource))
    }
    //endregion adapter callback implementation
}