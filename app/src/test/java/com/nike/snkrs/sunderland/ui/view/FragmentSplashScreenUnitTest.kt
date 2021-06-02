/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view


//region import directives

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

//endregion import directives


/**
 * Provides unit test case coverage for the Splash Screen fragment
 * @author Thomas Sunderland. 2021 June 01
 */
class FragmentSplashScreenUnitTest {

    //region data members

    /**
     * Used in place Dispatchers.Main for testing
     */
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    //endregion data members


    //region setUp/tearDown

    /**
     * Executed before each test case is run
     */
    @Before
    @ExperimentalCoroutinesApi
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Executed after each test case is run
     */
    @After
    @ExperimentalCoroutinesApi
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    //endregion setUp/tearDown


    //region test cases

    /**
     * Tests that the navigation/screen transition logic is not executed until after the specified delay
     */
    @Test
    @ExperimentalTime
    fun `screenTransitionWithDelay() honors the delay specified - positive`() = runBlocking {
        // given
        val splashScreenFragment = FragmentSplashScreen()
        val splashScreenDelay = Duration.seconds(5)

        // when
        val executionTime =
            measureTime { splashScreenFragment.screenTransitionWithDelay(splashScreenDelay) }

        // then
        assertThat(executionTime).isAtLeast(splashScreenDelay)
    }
    //endregion test cases
}