/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.viewmodel


//region import directives

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nike.snkrs.sunderland.data.local.entities.Athletes
import com.nike.snkrs.sunderland.ui.model.ModelAthletes
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * ViewModel for the Athletes Fragment
 * @author Thomas Sunderland. 2021 MAY 10
 */
class ViewModelAthletes : ViewModelBase() {

    //region data members

    /**
     * Reference to our data model which acts as our repository
     */
    private val model by lazy { ModelAthletes() }
    //endregion data members


    //region properties

    /**
     * Collection of observable athletes data
     */
    val athletes: LiveData<List<Athletes>> = model.athletes.asLiveData()
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