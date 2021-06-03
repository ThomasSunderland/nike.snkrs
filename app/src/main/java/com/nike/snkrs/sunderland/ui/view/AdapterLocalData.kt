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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nike.snkrs.sunderland.databinding.CardLocalDataBinding
import com.nike.snkrs.sunderland.ui.view.callbacks.AdapterDataCallback
import com.nike.snkrs.sunderland.util.GlideApp
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * Data adapter for presenting local data on cards with images (primary and alternates) & text (source)
 * @author Thomas Sunderland. 2021 MAY 12
 */
class AdapterLocalData(val callback: AdapterDataCallback) :
    ListAdapter<AdapterLocalDataItem, AdapterLocalData.ViewHolder>(AdapterDataItemDiffCallBack()) {

    //region inner types

    /**
     * ViewHolder
     */
    //@formatter:off
    inner class ViewHolder(val binding: CardLocalDataBinding,
                           private val callback: AdapterDataCallback) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterLocalDataItem) {
            with(binding) {
                tryCatch {
                    // load primary image
                    item.resource.toIntOrNull()?.let {
                        GlideApp.with(root).load(it).into(featuredImage)
                    }

                    // set image source
                    source.text = item.source

                    // set animation event handle
                    // hide the animation once it's complete running
                    animation.addAnimatorListener( object: Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) { /* _asm nop */ }
                        override fun onAnimationCancel(animation: Animator?) { /* _asm nop */ }
                        override fun onAnimationRepeat(animation: Animator?) { /* _asm nop */ }
                        override fun onAnimationEnd(animation: Animator?) {
                            tryCatchWithLogging({
                                binding.animation.visibility = View.GONE
                            })
                        }
                    })

                    // set click event handler / callback
                    featuredImage.setOnClickListener {
                        // play mock loading animation
                        animation.visibility = View.VISIBLE
                        animation.playAnimation()

                        // trigger callback
                        callback.onClick(item.resource)
                    }
                }
            }
        }
    }
    //@formatter:on

    /**
     * Implementation of DiffUtil.ItemCallback for ensuring that items are only updated as-needed
     */
    //@formatter:off
    private class AdapterDataItemDiffCallBack : DiffUtil.ItemCallback<AdapterLocalDataItem>() {
        /**
         * Called to check whether two objects represent the same item.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return True if the two items represent the same object or false if they are different.
         */
        override fun areItemsTheSame(oldItem: AdapterLocalDataItem, newItem: AdapterLocalDataItem): Boolean =
            oldItem === newItem || oldItem.id == newItem.id

        /**
         * Called to check whether two items have the same data.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return True if the contents of the items are the same or false if they are different.
         */
        override fun areContentsTheSame(oldItem: AdapterLocalDataItem, newItem: AdapterLocalDataItem): Boolean =
            oldItem == newItem
    }
    //@formatter:on
    //endregion inner types


    //region overrides

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterLocalData.ViewHolder {
        val binding =
            CardLocalDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, callback)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(viewHolder: AdapterLocalData.ViewHolder, position: Int) {
        tryCatch { viewHolder.bind(getItem(position % currentList.size)) }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    //@formatter:off
    override fun getItemCount() = if (currentList.size > 0) Int.MAX_VALUE else 0 // note: this allows infinite looping
    //@formatter:on
    //endregion overrides
}