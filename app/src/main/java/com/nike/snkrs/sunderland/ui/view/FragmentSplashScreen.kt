/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view


//region import directives

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nike.snkrs.sunderland.R
import com.nike.snkrs.sunderland.util.tryCatchWithLogging
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

//endregion import directives


/**
 * Used to display a "splash" screen prior to showing the main content
 * @author Thomas Sunderland. 2021 MAY 09
 */
class FragmentSplashScreen : Fragment(), CoroutineScope {

    //region companion object

    companion object {

        //region constants

        /**
         * Number of seconds to display the splash screen before proceeding to the next screen
         */
        private const val SPLASH_SCREEN_DURATION_SECONDS = 3L
        //endregion constants
    }
    //endregion companion object


    //region data members

    /**
     * Our coroutine for navigating to the main fragment after a short delay
     * Note: we keep a reference here so that we can cancel it in the event that the activity is destroyed
     */
    private lateinit var navigateAfterDelayRoutine: Job
    //endregion data members


    //region properties

    /**
     * Coroutine context (Main Thread)
     */
    override val coroutineContext get() = Dispatchers.Main
    //endregion properties


    //region lifecycle overrides

    /**
     * Called to have the fragment instantiate its user interface view. This is optional, and non-graphical
     * fragments can return null. This will be called between onCreate(Bundle) and onActivityCreated(Bundle).
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    //@formatter:off
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    //@formatter:on

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has returned,
     * but before any saved state has been restored in to the view. This gives subclasses a chance to
     * initialize themselves once they know their view hierarchy has been completely created. The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tryCatchWithLogging({
            // call into base class implementation
            super.onViewCreated(view, savedInstanceState)

            // wait a bit and then navigate to the sneakers fragment
            navigateAfterDelayRoutine = launch {
                delay(TimeUnit.SECONDS.toMillis(SPLASH_SCREEN_DURATION_SECONDS))
                withContext(Dispatchers.Main) {
                    findNavController().navigate(FragmentSplashScreenDirections.actionFragmentSplashScreenToFragmentSneakers())
                }
            }
        })
    }

    /**
     * Called when the Fragment is visible to the user.
     */
    override fun onStart() {
        tryCatchWithLogging({
            // call into base class implementation
            super.onStart()

            // hide app bar, bottom navigation on start
            with(activity as ActivityMain) {
                supportActionBar?.hide()
                viewBinding.bottomNavigationView.visibility = View.GONE
            }
        })
    }

    /**
     * Called when the Fragment is no longer started.
     */
    override fun onStop() {
        tryCatchWithLogging({
            // call into base class implementation
            super.onStop()

            // show app bar, bottom navigation on stop
            with(activity as ActivityMain) {
                supportActionBar?.show()
                viewBinding.bottomNavigationView.visibility = View.VISIBLE
            }
        })
    }

    /**
     * Called when the fragment is no longer in use. This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    override fun onDestroy() {
        tryCatchWithLogging({
            // call into base class implementation
            super.onDestroy()

            // cancel the navigation routine
            // note: this effectively handles device rotation by preventing navigation from a destroyed context
            if (::navigateAfterDelayRoutine.isInitialized) navigateAfterDelayRoutine.cancel()
        })
    }
    //endregion lifecycle overrides
}