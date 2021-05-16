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
import com.nike.snkrs.sunderland.data.local.entities.Apparel
import com.nike.snkrs.sunderland.ui.model.ModelApparel
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * ViewModel for the Apparel Fragment
 * @author Thomas Sunderland. 2021 MAY 10
 */
class ViewModelApparel : ViewModelBase() {

    //region data members

    /**
     * Reference to our data model which acts as our repository
     */
    private val model by lazy { ModelApparel() }
    //endregion data members


    //region properties

    /**
     * Collection of observable apparel data
     */
    val apparel: LiveData<List<Apparel>> = model.apparelAlt.asLiveData()
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