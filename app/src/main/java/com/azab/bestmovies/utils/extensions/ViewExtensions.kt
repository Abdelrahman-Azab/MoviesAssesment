package com.azab.bestmovies.utils.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.showErrorMsg(errorMsg: String) {
    Toast.makeText(this,errorMsg, Toast.LENGTH_LONG).show()
}


fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}
