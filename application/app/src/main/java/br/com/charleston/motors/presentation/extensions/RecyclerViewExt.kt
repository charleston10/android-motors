package br.com.charleston.motors.presentation.extensions

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.motors.R

fun RecyclerView.animateFallDown() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}