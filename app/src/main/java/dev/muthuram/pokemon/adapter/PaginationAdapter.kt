package dev.muthuram.pokemon.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.muthuram.pokemon.R
import dev.muthuram.pokemon.helper.inflate

abstract class PaginationAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val items: MutableList<T> = ArrayList()
    protected var isFooterAdded = false
    protected val ITEM = 1
    protected val FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            ITEM -> viewHolder = createItemViewHolder(parent)
            FOOTER -> viewHolder = createFooterViewHolder(parent)
        }

        return if (viewHolder !== null) viewHolder else createItemViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> bindItemViewHolder(viewHolder, position)
            FOOTER -> bindFooterViewHolder(viewHolder)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }



    protected abstract fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    protected fun createFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        FooterViewHolder(parent.inflate(R.layout.model_footer_loading))



    protected abstract fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)

    protected fun bindFooterViewHolder(viewHolder: RecyclerView.ViewHolder) {}

    protected fun displayLoadMoreFooter() {}

    protected fun displayErrorFooter() {}

    fun addFooter() {
        if (items.isNotEmpty()) {
            isFooterAdded = true
            items.add(items[0])
        }
    }

    fun getItem(position: Int): T? {
        return items[position]
    }

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(items: List<T>) {
        for (item in items) {
            add(item)
        }
    }

    fun addAllWithFooter(items: List<T>, addFooter: Boolean) {
        addAll(items)
        if (addFooter) addFooter()
        else removeFooter()
    }

    fun <R : Comparable<R>> sort(selector: (T) -> R?) {
        items.sortBy(selector)
        notifyDataSetChanged()
    }

    private fun remove(item: T?) {
        val position = items.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isFooterAdded = false
        items.clear()
        notifyDataSetChanged()
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    fun isLastPosition(position: Int): Boolean {
        return position == items.size - 1
    }

    fun removeFooter() {
        if (isFooterAdded) {
            isFooterAdded = false

            val position = items.size - 1
            val item = getItem(position)

            if (item != null) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun updateFooter(footerType: FooterType) {
        when (footerType) {
            FooterType.LOAD_MORE -> displayLoadMoreFooter()
            FooterType.ERROR -> displayErrorFooter()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLastPosition(position) && isFooterAdded) FOOTER else ITEM
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    enum class FooterType {
        LOAD_MORE,
        ERROR
    }
}