/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view


//region import directives

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nike.snkrs.sunderland.databinding.FragmentAboutBinding
import com.nike.snkrs.sunderland.ui.viewmodel.ViewModelAbout
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * Used to display an about screen
 * @author Thomas Sunderland. 2021 MAY 13
 */
class FragmentAbout : Fragment() {

    //region data members

    /**
     * Reference to the view model
     */
    private val viewModel by viewModels<ViewModelAbout>()

    /**
     * Reference to the binding for this fragment
     */
    private lateinit var binding: FragmentAboutBinding
    //endregion data members


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
        return FragmentAboutBinding.inflate(inflater, container, false).apply {
            tryCatch {
                this@FragmentAbout.binding = this
                viewModel = this@FragmentAbout.viewModel
                lifecycleOwner = this@FragmentAbout
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
            //@formatter:off

            // setup professional links
            binding.linkedin.setOnClickListener { (requireActivity() as ActivityMain).navigateToLinkedInWebPage() }
            binding.stackoverflow.setOnClickListener { (requireActivity() as ActivityMain).navigateToStackOverflowWebPage() }
            binding.medium.setOnClickListener { (requireActivity() as ActivityMain).navigateToMediumWebPage() }

            // hide the animation once it's complete running
            binding.animation.addAnimatorListener( object: Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) { /* _asm nop */ }
                override fun onAnimationCancel(animation: Animator?) { /* _asm nop */ }
                override fun onAnimationRepeat(animation: Animator?) { /* _asm nop */ }
                override fun onAnimationEnd(animation: Animator?) {
                    tryCatchWithLogging({
                        binding.animation.visibility = View.GONE
                    })
                }
            })
            //@formatter:on
        })
    }
    //endregion lifecycle overrides
}