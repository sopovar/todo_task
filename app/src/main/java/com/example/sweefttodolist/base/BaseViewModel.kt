package com.example.sweefttodolist.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.example.sweefttodolist.App
import com.example.sweefttodolist.utils.hideKeyboard
import com.example.sweefttodolist.utils.showSnackBar
import java.lang.Exception
import java.util.*

/**
 * Created by Sopo Vardidze on 06.09.21
 */
const val TODO_DB = "todoList"
open class BaseViewModel(val context: Context): Observable(), androidx.databinding.Observable {

    lateinit var binding: ViewDataBinding

    var baseActivity: Activity? = null

    fun getApplication(): App? {
        return if (context is App)
            context
        else null
    }

    override fun addOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
    }

    fun showError(view: View, ex: String?) {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
            view.showSnackBar(ex ?: "Something went wrong")
        }
    }

    fun showSuccess(msg : String, view: View) {
        view.showSnackBar(msg)
    }

}