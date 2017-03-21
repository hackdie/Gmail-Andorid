package com.skalala.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.skalala.adapter.viewholder.HomeViewHolder
import com.skalala.gmail.R
import com.skalala.model.Email
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import com.skalala.gmail.GmailApplication
import com.skalala.helpers.CircleTransform
import com.squareup.picasso.Picasso
import javax.inject.Inject
import com.skalala.helpers.FlipAnimator


/**
 * Created by diegogalindo on 3/10/17.
 * /
 */

class HomeAdapter(mListener: HomeViewHolder.MessageAdapterListener, context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

    @Inject
    lateinit var picasso: Picasso
    private var currentSelectedIndex = -1

    init {
        GmailApplication.picassoComponent.inject(this)
    }

    private val items: ArrayList<Email> = ArrayList()
    private val listener = mListener
    private val mContext = context

    private var selectedItems = SparseBooleanArray()
    private var animationItemsIndex = SparseBooleanArray()
    private var reverseAllAnimations = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_row, parent, false)
        return HomeViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val email = items[position]

        holder.txtFrom.text = items[position].from
        holder.txtMessage.text = items[position].message
        holder.txtSubject.text = items[position].subject
        holder.txtTime.text = items[position].timestamp

        holder.itemView.isActivated = selectedItems.get(position, false)


        if (!items[position].picture.isNullOrEmpty()) {
            picasso.load(items[position].picture).transform(CircleTransform()).into(holder.icon_profile)
            holder.icon_profile.colorFilter = null
            holder.icon_text.visibility = View.GONE
        } else {
            holder.icon_text.text = items[position].from.substring(0, 1)
            holder.icon_profile.setImageResource(R.drawable.bg_circle)
            holder.icon_profile.setColorFilter(items[position].color)
            holder.icon_text.visibility = View.VISIBLE
        }

        applyImportant(holder, email)


        applyIconAnimation(holder, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addEmails(emails: List<Email>) {
        items.addAll(emails)
    }

    private fun applyImportant(holder: HomeViewHolder, message: Email) {
        if (message.isImportant) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_black_24dp))
            holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_selected))
        } else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_black_24dp))
            holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal))
        }
    }


    private fun applyIconAnimation(holder: HomeViewHolder, position: Int) {
        if (selectedItems.get(position, false)) {
            holder.icon_front.visibility = View.GONE
            resetIconYAxis(holder.icon_back)
            holder.icon_back.visibility = View.VISIBLE
            holder.icon_back.alpha = 1.0F
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.icon_back, holder.icon_front, true)
                resetCurrentIndex()
            }
        } else {
            holder.icon_back.visibility = View.GONE
            resetIconYAxis(holder.icon_front)
            holder.icon_front.visibility = View.VISIBLE
            holder.icon_front.alpha = 1.0f
            if (reverseAllAnimations && animationItemsIndex.get(position, false) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.icon_back, holder.icon_front, false)
                resetCurrentIndex()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    fun toggleSelection(pos: Int) {
        currentSelectedIndex = pos
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos)
            animationItemsIndex.delete(pos)
        } else {
            selectedItems.put(pos, true)
            animationItemsIndex.put(pos, true)
        }
        notifyItemChanged(pos)
    }

    fun clearSelections() {
        reverseAllAnimations = true
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun resetAnimationIndex() {
        reverseAllAnimations = false
        animationItemsIndex.clear()
    }

    private fun resetCurrentIndex() {
        currentSelectedIndex = -1
    }

    private fun resetIconYAxis(view: View) {
        if (view.rotationY != 0f) {
            view.rotationY = 0f
        }
    }

    fun getSelectedItemCount(): Int {
        return selectedItems.size()
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        resetCurrentIndex()
    }

    fun getSelectedItems(): List<Int> {
        val items = ArrayList<Int>(selectedItems.size())

        for (i in 0..selectedItems.size() - 1) {
            items.add(selectedItems.keyAt(i))
        }

        return items
    }

    fun getEmailAt(position: Int): Email {
        return items[position]
    }

}