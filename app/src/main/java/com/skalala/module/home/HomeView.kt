package com.skalala.module.home

import android.graphics.*
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.view.ActionMode
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.skalala.adapter.HomeAdapter
import com.skalala.adapter.viewholder.HomeViewHolder
import com.skalala.gmail.R
import com.skalala.model.Email
import kotlinx.android.synthetic.main.home_view.*
import com.skalala.gmail.MainActivity
import android.widget.Toast
import com.skalala.helpers.DividerItemDecoration
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.RecyclerView
import android.view.*
import android.graphics.RectF
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup


/**
 * Created by diegogalindo on 3/9/17.
 * /
 */

class HomeView : Fragment(), IHome.View, HomeViewHolder.MessageAdapterListener {

    lateinit var presenter: HomePresenter
    lateinit var adapter: HomeAdapter
    lateinit var actionModeCallback: ActionModeCallback
    var emails: MutableList<Email> = ArrayList()
    lateinit var mView: View
    var actionMode: ActionMode? = null
    private val p = Paint()

    override fun showError(error: String) {
        Snackbar.make(mView, error, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun presentEmails(emails: MutableList<Email>) {
        this.emails.addAll(emails)
        adapter.addEmails(this.emails)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_view, container, false)
        mView = view
        setHasOptionsMenu(true)
        presenter = HomePresenter(this, context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(toolbar)

        adapter = HomeAdapter(this, context)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(DividerItemDecoration(context))
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)


        actionModeCallback = ActionModeCallback()

        presenter.fetchData()
    }


    override fun onIconClicked(position: Int) {
        enableActionMode(position)
    }

    override fun onIconImportantClicked(position: Int) {
        val message = adapter.getEmailAt(position)
        adapter.getEmailAt(position).isImportant = !message.isImportant
        adapter.notifyDataSetChanged()
    }

    override fun onMessageRowClicked(position: Int) {
        if (adapter.getSelectedItemCount() > 0) {
            enableActionMode(position)
        } else{
            Toast.makeText(context, "Read: " + adapter.getEmailAt(position).message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun enableActionMode(position: Int) {
        if (actionMode == null) {
            actionMode = (activity as MainActivity).startSupportActionMode(actionModeCallback)
        }
        toggleSelection(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_search) {
            Toast.makeText(context, "Search...", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun toggleSelection(position: Int) {
        adapter.toggleSelection(position)
        val count = adapter.getSelectedItemCount()

        if (count == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = count.toString()
            actionMode?.invalidate()
        }
    }


    override fun onRowLongClicked(position: Int) {
        enableActionMode(position)
    }

    /*
        Swipe callback
     */

    val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            if (direction == ItemTouchHelper.LEFT) {
                adapter.removeAt(position)
                adapter.notifyItemRemoved(position)
            } else {

            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

            val icon: Bitmap
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                val itemView = viewHolder.itemView
                val height = itemView.bottom - itemView.top
                val width = height / 3

                if (dX > 0) {
                    p.color = Color.parseColor("#388E3C")
                    val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                    c.drawRect(background, p)
                    icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                    val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                    c.drawBitmap(icon, null, icon_dest, p)

                } else {
                    p.color = Color.parseColor("#D32F2F")
                    val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                    c.drawRect(background, p)
                    icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                    val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                    c.drawBitmap(icon, null, icon_dest, p)
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }


    /*
        Action toolbar

     */

    private fun deleteMessages() {
        adapter.resetAnimationIndex()
        val selectedItemPositions = adapter.getSelectedItems().reversed()
        Log.e("asdas","asdas" + selectedItemPositions)

        for(index in selectedItemPositions) {
            adapter.removeAt(index)
        }

        adapter.notifyDataSetChanged()
    }


    inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_action_mode, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_delete -> {
                    deleteMessages()
                    mode.finish()
                    return true
                }

                else -> return false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            adapter.clearSelections()
//            swipeRefreshLayout.setEnabled(true)
            actionMode = null
            recycler_view.post({
                adapter.resetAnimationIndex()
//                adapter.notifyDataSetChanged()
            })
        }
    }

}