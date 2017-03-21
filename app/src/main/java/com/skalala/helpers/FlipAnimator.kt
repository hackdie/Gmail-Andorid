package com.skalala.helpers

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import com.skalala.gmail.R

/**
 * Created by diegogalindo on 3/18/17.
 *
 */
class FlipAnimator {
    private val TAG = "FlipAnimator"

    /**
     * Performs flip animation on two views
     */

    companion object {
        private lateinit var leftIn: AnimatorSet
        private lateinit var rightOut: AnimatorSet
        private lateinit var leftOut: AnimatorSet
        private lateinit var rightIn: AnimatorSet

        fun flipView(context: Context, back: View, front: View, showFront: Boolean) {


            leftIn = AnimatorInflater.loadAnimator(context, R.anim.card_flip_left_in) as AnimatorSet
            rightOut = AnimatorInflater.loadAnimator(context, R.anim.card_flip_right_out) as AnimatorSet
            leftOut = AnimatorInflater.loadAnimator(context, R.anim.card_flip_left_out) as AnimatorSet
            rightIn = AnimatorInflater.loadAnimator(context, R.anim.card_flip_right_in) as AnimatorSet


            val showFrontAnim = AnimatorSet()
            val showBackAnim = AnimatorSet()

            leftIn.setTarget(back)
            rightOut.setTarget(front)
            showFrontAnim.playTogether(leftIn, rightOut)

            leftOut.setTarget(back)
            rightIn.setTarget(front)
            showBackAnim.playTogether(rightIn, leftOut)

            if (showFront) {
                showFrontAnim.start()
            } else {
                showBackAnim.start()
            }


        }
    }
}
