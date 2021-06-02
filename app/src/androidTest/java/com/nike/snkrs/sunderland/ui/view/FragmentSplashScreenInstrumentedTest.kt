/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view


//region import directives

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.nike.snkrs.sunderland.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

//endregion import directives


/**
 * Provides instrumented test case coverage for the Splash Screen fragment
 * @author Thomas Sunderland. 2021 June 01
 */
@RunWith(AndroidJUnit4::class)
class FragmentSplashScreenInstrumentedTest {

    //region test cases

    /**
     * Tests automated (delayed) navigation from the Splash screen to the Sneakers screen
     */
    @Test
    @ExperimentalTime
    @ExperimentalCoroutinesApi
    fun testNavigationFromSplashScreenToSneakersScreen() = runBlocking {
        // given
        // create a navigation controller for testing screen transitions
        val testNavController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // create a graphical FragmentScenario for the SplashScreen
        val splashScreenScenario = launchFragmentInContainer<FragmentSplashScreen>()
        splashScreenScenario.onFragment { fragment ->
            // set the navigation graph for the TestNavHostController
            testNavController.setGraph(R.navigation.navigation_graph)

            // make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), testNavController)
        }

        // when (wait for the splash screen to auto-transition)
        splashScreenScenario.moveToState(Lifecycle.State.STARTED)
        delay(FragmentSplashScreen.SPLASH_SCREEN_DURATION_SECONDS.plus(Duration.milliseconds(500)))

        // then
        assertThat(testNavController.currentDestination?.id).isEqualTo(R.id.fragmentSneakers)
    }
    //endregion test cases
}