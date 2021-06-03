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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nike.snkrs.sunderland.R
import com.nike.snkrs.sunderland.databinding.FragmentApparelBinding
import com.nike.snkrs.sunderland.databinding.FragmentAthletesBinding.*
import com.nike.snkrs.sunderland.ui.viewmodel.ViewModelApparel
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//endregion import directives


/**
 * Used to display a carousel of apparel images
 * @author Thomas Sunderland. 2021 MAY 10
 */
class FragmentApparel : Fragment(), CoroutineScope {

    //region data members

    /**
     * Reference to the view model
     */
    private lateinit var viewModel: ViewModelApparel

    /**
     * Reference to the binding for this fragment
     */
    private lateinit var binding: FragmentApparelBinding
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
        return FragmentApparelBinding.inflate(inflater, container, false).apply {
            tryCatch {
                this@FragmentApparel.binding = this
                this@FragmentApparel.viewModel = ViewModelProvider(requireActivity()).get(ViewModelApparel::class.java)

                viewModel = this@FragmentApparel.viewModel
                lifecycleOwner = this@FragmentApparel
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
            // configure our recycler view which presents remote data
            configureRecyclerView()

            // configure our view pager which presents local data
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
     * Configures our recycler view (remote data)
     */
    private fun configureRecyclerView() {
        //@formatter:off
        tryCatchWithLogging({
            with (binding.remoteData) {
                // use a linear layout manager (horizontal orientation)
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL, false)

                // initialize the adapter with an empty data set
                adapter = AdapterRemoteData(viewModel).apply {
                    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            tryCatch {
                                if (currentList.isNotEmpty()) {
                                    if (viewModel.firstTimeThrough.value == true) {
                                        // update current item to be at the middle of the current collection of items
                                        viewModel.firstTimeThrough.value = false
                                        //scrollToPosition(getItemCount() / 2)
                                    } else {
                                        // ensure that we present the last item viewed
                                        //scrollToPosition(viewModel.currentRecyclerViewPosition.value ?: 0)
                                    }
                                }
                            }
                        }
                    })
                }

                // item animator
                itemAnimator = ScaleInRightAnimator().apply {
                    addDuration = 350 // default 120
                    removeDuration = 350 // default 120
                }

                // observe remote data changes
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.remoteApparelData.collectLatest { apparelData ->
                            (adapter as AdapterRemoteData).submitList(apparelData.map { AdapterRemoteDataItem(it.uid, it.resource, it.source) })
                        }
                    }
                }
            }
        })
        //@formatter:on
    }

    /**
     * Configures our view pager (local data)
     */
    private fun configureViewPager() {
        //@formatter:off
        tryCatchWithLogging({
            with (binding.localData) {
                // initialize the adapter with an empty data set
                adapter = AdapterLocalData(viewModel).apply {
                    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            tryCatch {
                                if (currentList.isNotEmpty()) {
                                    if (viewModel.firstTimeThrough.value == true) {
                                        // update current item to be at the middle of the current collection of items
                                        viewModel.firstTimeThrough.value = false
                                        setCurrentItem(getItemCount() / 2, false)
                                    } else {
                                        // ensure that we present the last item viewed
                                        setCurrentItem(viewModel.currentViewPagerPosition.value ?: 0, false)
                                    }
                                }
                            }
                        }
                    })
                }

                // keep 1 page in memory on each side of the current page
                offscreenPageLimit = 1

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
                        viewModel.currentViewPagerPosition.value = position
                    }
                })

                // observe local data changes
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.localApparelData.collectLatest { apparelData ->
                            (adapter as AdapterLocalData).submitList(apparelData.map { AdapterLocalDataItem(it.uid, it.resource, it.source) })
                        }
                    }
                }
            }
        })
        //@formatter:on
    }
    //endregion private methods
}