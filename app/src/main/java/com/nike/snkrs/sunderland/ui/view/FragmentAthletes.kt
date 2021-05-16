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
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.nike.snkrs.sunderland.R
import com.nike.snkrs.sunderland.databinding.FragmentAthletesBinding
import com.nike.snkrs.sunderland.ui.viewmodel.ViewModelAthletes
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//endregion import directives


/**
 * Used to display a carousel of endorsed athlete images
 * @author Thomas Sunderland. 2021 MAY 10
 */
class FragmentAthletes : Fragment(), CoroutineScope {

    //region data members

    /**
     * Reference to the view model
     */
    private lateinit var viewModel: ViewModelAthletes

    /**
     * Reference to the binding for this fragment
     */
    private lateinit var binding: FragmentAthletesBinding
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
        return FragmentAthletesBinding.inflate(inflater, container, false).apply {
            tryCatch {
                this@FragmentAthletes.binding = this
                this@FragmentAthletes.viewModel = ViewModelProvider(requireActivity()).get(ViewModelAthletes::class.java)

                viewModel = this@FragmentAthletes.viewModel
                lifecycleOwner = this@FragmentAthletes
            }
        }.root
    }
    //@formatter:on

    /**
     * Called immediately after [.onCreateView] has returned, but before any saved state has been
     * restored in to the view. This gives subclasses a chance to initialize themselves once they
     * know their view hierarchy has been completely created.
     * @param view The View returned by [.onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tryCatchWithLogging({
            configureViewPager()

            // fade the shimmer effect out
            launch {
                run fadeIn@{
                    repeat(times = 100) {
                        if (binding.background.alpha >= 1.0f) {
                            binding.shimmerViewContainer.visibility = View.GONE
                            return@fadeIn
                        } else {
                            binding.background.alpha += .025f
                        }
                        delay(timeMillis = 100)
                    }
                }
            }
        })
    }
    //endregion lifecycle overrides


    //region private methods

    /**
     * Configures our view pager
     */
    private fun configureViewPager() {
        //@formatter:off
        tryCatchWithLogging({
            with (binding.foregroundViewPager) {
                // initialize the adapter with an empty data set
                adapter = AdapterGeneral(mutableListOf(), viewModel)
                offscreenPageLimit = 1

                // observe athletes data changes
                viewModel.athletes.observe(viewLifecycleOwner) { athletes ->
                    athletes?.let {
                        if (viewModel.firstTimeThrough.value == true) {
                            // transform the received data to a representation understood by our adapter
                            (adapter as AdapterGeneral).addAll(athletes.map { AdapterGeneralItem(it.uid, it.resource, it.source) })

                            // update current item to be at the middle of the current collection of items
                            viewModel.firstTimeThrough.value = false
                            setCurrentItem((adapter as AdapterGeneral).itemCount / 2, false)
                        } else {
                            // transform the received data to a representation understood by our adapter
                            (adapter as AdapterGeneral).addAll(athletes.map { AdapterGeneralItem(it.uid, it.resource, it.source) })

                            // ensure that we present the last item viewed
                            setCurrentItem(viewModel.currentPosition.value ?: 0, false)
                        }
                    }
                }

                // set up carousel effect
                setPageTransformer { page, position ->
                    val pageMargin = resources.getDimensionPixelOffset(R.dimen.general_view_pager_page_margin).toFloat()
                    val pageOffset = resources.getDimensionPixelOffset(R.dimen.general_view_pager_page_offset).toFloat()
                    page.scaleX = .75f
                    page.scaleY = .9f
                    page.translationX = position * -(resources.getDimension(R.dimen.general_view_pager_multiplier) * pageOffset + pageMargin)
                }

                // update view model as page changes so that if we go back to this fragment the last page can be restored
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        viewModel.currentPosition.value = position
                    }
                })
            }
        })
        //@formatter:on
    }
    //endregion private methods
}