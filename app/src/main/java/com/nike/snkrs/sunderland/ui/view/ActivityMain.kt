/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view


//region import directives

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nike.snkrs.sunderland.R
import com.nike.snkrs.sunderland.databinding.ActivityMainBinding
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * Single/Main Activity (Single Activity Architecture)
 * @author Thomas Sunderland. 2021 MAY 09
 */
class ActivityMain : AppCompatActivity() {

    //region data members

    /**
     * Reference to our view binding
     */
    lateinit var viewBinding: ActivityMainBinding

    /**
     * Navigation controller
     */
    private lateinit var navController: NavController
    //endregion data members


    //region lifecycle overrides

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle
     * contains the data it most recently supplied in {@link #onSaveInstanceState}. <b><i>Note: Otherwise it is null.</i></b>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        tryCatchWithLogging({
            // update the app theme to our custom styling
            setTheme(R.style.Theme_SunderlandCodingAssessment)

            // call into base class implementation
            super.onCreate(savedInstanceState)

            // inflate layout, enable view binding
            viewBinding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(viewBinding.root)

            // configure toolbar
            setSupportActionBar(viewBinding.toolbar)
            supportActionBar?.setIcon(R.drawable.toolbar_logo)

            with(supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment) {
                // save off the nav controller reference
                this@ActivityMain.navController = navController

                // notify bottom navigation view to play nicely with navigation controller
                viewBinding.bottomNavigationView.setupWithNavController(navController)

                // notify app bar to play nicely with the bottom navigation view and to show
                // the labels defined in our navigation graph when displaying each destination
                setupActionBarWithNavController(
                    navController, AppBarConfiguration(
                        setOf(
                            R.id.fragmentSneakers,
                            R.id.fragmentAthletes,
                            R.id.fragmentApparel,
                            R.id.fragmentAbout,
                            R.id.fragmentWebView
                        )
                    )
                )
            }

            // note: this ensures that the bottom navigation view highlighting plays nicely with the About screen
            viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.fragmentSneakers, R.id.fragmentAthletes, R.id.fragmentApparel -> {
                        navController.navigate(item.itemId)
                        viewBinding.bottomNavigationView.menu
                            .setGroupCheckable(0, true, true)
                        true
                    }
                    else -> false
                }
            }
        })
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        tryCatch {
            menuInflater.inflate(R.menu.menu_toolbar, menu)

            // note: this is the reason for the RestrictedApi lint suppression
            (menu as MenuBuilder).setOptionalIconsVisible(true)
        }
        return true
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //@formatter:off
        return when (item.itemId) {
            R.id.action_linkedin -> {
                navigateToLinkedInWebPage()
                true
            }

            R.id.action_stack_overflow -> {
                navigateToStackOverflowWebPage()
                true
            }

            R.id.action_medium -> {
                navigateToMediumWebPage()
                true
            }

            R.id.action_about -> {
                viewBinding.bottomNavigationView.menu.setGroupCheckable(0, false, true)
                if (!navController.popBackStack(R.id.fragmentAbout, false)) {
                    navController.navigate(FragmentAboutDirections.actionFragmentAbout())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        //@formatter:on
    }
    //endregion lifecycle overrides


    //region public functions

    /**
     * Shows the WebView fragment and loads our LinkedIn page
     */
    fun navigateToLinkedInWebPage() {
        tryCatchWithLogging({
            viewBinding.bottomNavigationView.menu.setGroupCheckable(0, false, true)
            navController.navigate(
                FragmentWebViewDirections.actionFragmentWebView(
                    targetUrl = getString(
                        R.string.professional_link_linkedin
                    )
                )
            )
            supportActionBar?.title = getString(R.string.menu_item_linkedin)
        })
    }

    /**
     * Shows the WebView fragment and loads our StackOverflow page
     */
    fun navigateToStackOverflowWebPage() {
        tryCatchWithLogging({
            viewBinding.bottomNavigationView.menu.setGroupCheckable(0, false, true)
            navController.navigate(
                FragmentWebViewDirections.actionFragmentWebView(
                    targetUrl = getString(
                        R.string.professional_link_stackoverflow
                    )
                )
            )
            supportActionBar?.title = getString(R.string.menu_item_stack_overflow)
        })
    }

    /**
     * Shows the WebView fragment and loads our Medium page
     */
    fun navigateToMediumWebPage() {
        tryCatchWithLogging({
            viewBinding.bottomNavigationView.menu.setGroupCheckable(0, false, true)
            navController.navigate(
                FragmentWebViewDirections.actionFragmentWebView(
                    targetUrl = getString(
                        R.string.professional_link_medium
                    )
                )
            )
            supportActionBar?.title = getString(R.string.menu_item_medium)
        })
    }
    //endregion public functions
}