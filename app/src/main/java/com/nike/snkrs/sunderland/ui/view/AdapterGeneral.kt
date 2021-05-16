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
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nike.snkrs.sunderland.databinding.CardGeneralBinding
import com.nike.snkrs.sunderland.ui.view.callbacks.AdapterGeneralCallback
import com.nike.snkrs.sunderland.util.GlideApp
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * General adapter for presenting cards with images & text
 * @author Thomas Sunderland. 2021 MAY 12
 */
class AdapterGeneral(
    var items: MutableList<AdapterGeneralItem>,
    private val callback: AdapterGeneralCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //region inner types

    /**
     * ViewHolder
     */
    //@formatter:off
    inner class ViewHolder(val binding: CardGeneralBinding,
                           private val callback: AdapterGeneralCallback) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterGeneralItem) {
            with(binding) {
                tryCatch {
                    // load image
                    // if the resource is an int then we know it's a local drawable resource id,
                    // otherwise we'll try parsing it as a URI (remote image)
                    item.resource.toIntOrNull()?.let {
                        GlideApp.with(root).load(it).into(featuredImage)
                    } ?: run {
                        GlideApp.with(root).load(Uri.parse(item.resource)).into(featuredImage)
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
     * Defines how to compute if two items are the same or if the contents are the same
     * Note: used when calling addAll(List<AdapterGeneralItem>)
     */
    class ItemComparator(
        private var oldItems: List<AdapterGeneralItem>,
        private var newItems: List<AdapterGeneralItem>
    ) : DiffUtil.Callback() {

        /**
         * Returns the size of the old item collection
         * @return The size of the old item collection
         */
        override fun getOldListSize(): Int = oldItems.size

        /**
         * Returns the size of the new item collection
         * @return The size of the new item collection
         */
        override fun getNewListSize(): Int = newItems.size

        /**
         * Called by the DiffUtil to decide whether two object represent the same Item.
         * For example, if your items have unique ids, this method should check their id equality.
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return True if the two items represent the same object or false if they are different.
         */
        //@formatter:off
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id
        //@formatter:on

        /**
         * Called by the DiffUtil when it wants to check whether two items have the same data.
         * DiffUtil uses this information to detect if the contents of an item has changed.
         * <p>
         * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
         * so that you can change its behavior depending on your UI.
         *
         * For example, if you are using DiffUtil with a RecyclerView.Adapter, you should
         * return whether the items' visual representations are the same.
         * <p>
         * This method is called only if areItemsTheSame(int, int) returns true for these items.
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list which replaces the oldItem
         * @return True if the contents of the items are the same or false if they are different.
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            var contentsAreTheSame = true

            tryCatch {
                contentsAreTheSame = oldItems[oldItemPosition] == newItems[newItemPosition] &&
                        oldItems[oldItemPosition].id == newItems[newItemPosition].id &&
                        oldItems[oldItemPosition].resource == newItems[newItemPosition].resource &&
                        oldItems[oldItemPosition].source == newItems[newItemPosition].source
            }

            // return to caller
            return contentsAreTheSame
        }
    }
    //endregion inner types


    //region overrides

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardGeneralBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, callback)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        tryCatch { (viewHolder as ViewHolder).bind(items[position % items.size]) }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    //@formatter:off
    override fun getItemCount() = if (items.size > 0) Int.MAX_VALUE else 0 // note: this allows infinite looping
    //@formatter:on
    //endregion overrides


    //region public methods

    /**
     * Replaces the existing collection of items with the one passed in
     * @param items New collection of items to present
     */
    fun addAll(items: List<AdapterGeneralItem>) {
        tryCatchWithLogging({
            // calculate differences
            DiffUtil.calculateDiff(ItemComparator(this.items, items)).dispatchUpdatesTo(this)

            // clear and add
            this.items.clear()
            this.items.addAll(items)
        }, listOf("Items: ${items.size}"))
    }
    //endregion public methods
}