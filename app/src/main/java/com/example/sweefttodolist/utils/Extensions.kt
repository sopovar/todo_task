package com.example.sweefttodolist.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Sopo Vardidze on 11.09.21
 */

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}