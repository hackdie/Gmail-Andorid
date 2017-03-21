package com.skalala.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.skalala.gmail.R
import kotlinx.android.synthetic.main.home_row.view.*

/**
 * Created by diegogalindo on 3/10/17.
 * /
 */

class HomeViewHolder(view: View,mListener: HomeViewHolder.MessageAdapterListener) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {

    val txtFrom: TextView = view.from
    val txtSubject: TextView = view.txt_primary
    val txtMessage: TextView = view.txt_secondary
    val txtTime: TextView = view.timestamp
    val iconImp: ImageView = view.icon_star
    val iconContainer: RelativeLayout = view.icon_container
    val messageContainer: LinearLayout = view.message_container
    val icon_profile: ImageView = view.icon_profile
    val icon_text: TextView = view.icon_text
    val icon_front: RelativeLayout = view.icon_front
    val icon_back: RelativeLayout = view.icon_back
    private val listener = mListener


    init {
        view.setOnLongClickListener(this)
        iconImp.setOnClickListener(this)
        iconContainer.setOnClickListener(this)
        messageContainer.setOnClickListener(this)
        messageContainer.setOnLongClickListener(this)
    }

    override fun onLongClick(v: View): Boolean {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        listener.onRowLongClicked(adapterPosition)
        return true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.icon_star -> {
                listener.onIconImportantClicked(adapterPosition)
            }
            R.id.icon_container -> {
                listener.onIconClicked(adapterPosition)
            }
            R.id.message_container -> {
                listener.onMessageRowClicked(adapterPosition)
            }
            else -> {

            }
        }

    }

    interface MessageAdapterListener {
        fun onIconClicked(position: Int)

        fun onIconImportantClicked(position: Int)

        fun onMessageRowClicked(position: Int)

        fun onRowLongClicked(position: Int)
    }
}