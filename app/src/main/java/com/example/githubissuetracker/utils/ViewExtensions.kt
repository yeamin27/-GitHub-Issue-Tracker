package com.example.githubissuetracker.utils

import android.view.View

fun View.animateShowWithFade() {
    visibility = View.VISIBLE
    alpha = 0f

    animate()
        .alpha(1f)
        .setDuration(300)
        .setListener(null)
}

fun View.animateHideWithFade() {
    animate()
        .alpha(0f)
        .setDuration(300)
        .withEndAction {
            visibility = View.INVISIBLE
        }
}