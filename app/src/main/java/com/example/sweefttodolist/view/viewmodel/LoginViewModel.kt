package com.example.sweefttodolist.view.viewmodel

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.sweefttodolist.utils.PreferencesHelper
import com.example.sweefttodolist.base.BaseViewModel
import com.example.sweefttodolist.databinding.ActivityLoginBinding
import com.example.sweefttodolist.utils.hideKeyboard
import com.example.sweefttodolist.utils.showSnackBar
import com.example.sweefttodolist.view.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by Sopo Vardidze on 03.09.21
 */

class LoginViewModel @Inject constructor(
    context: Context,
    val auth: FirebaseAuth,
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel(context) {

    lateinit var viewBinding: ActivityLoginBinding

    fun init() {
        viewBinding = binding as ActivityLoginBinding
        viewBinding.isLogin = true
        viewBinding.isPasswordValid = false
        viewBinding.loading = false
    }

    fun registerUser(): View.OnClickListener {
        return View.OnClickListener {
            if (viewBinding.tvEmailReg.text.isNullOrEmpty() || viewBinding.tvPasswordReg.text.isNullOrEmpty()) {
                viewBinding.btnLogin.showSnackBar("Please fill all fields")
            } else {
                viewBinding.loading = true
                auth.createUserWithEmailAndPassword(
                    viewBinding.tvEmailReg.text.toString(),
                    viewBinding.tvPasswordReg.text.toString()
                ).addOnSuccessListener {
                    if (auth.currentUser != null) {
                        handleSuccessfulEntry(auth.currentUser!!.uid, viewBinding.tvEmailReg, viewBinding.tvPasswordReg)
                    } else {
                        showError(viewBinding.textView2, null)
                    }
                }
            }
        }
    }

    fun loginUser(): View.OnClickListener {
        return View.OnClickListener {
            if (viewBinding.tvEmail.text.isNullOrEmpty() || viewBinding.tvPassword.text.isNullOrEmpty()) {
                viewBinding.btnLogin.showSnackBar("Please fill all fields")
            } else {
                viewBinding.loading = true
                auth.signInWithEmailAndPassword(
                    viewBinding.tvEmail.text.toString(),
                    viewBinding.tvPassword.text.toString()
                ).addOnSuccessListener {
                    if (auth.currentUser != null) {
                       handleSuccessfulEntry(auth.currentUser!!.uid, viewBinding.tvEmail, viewBinding.tvPassword)
                    } else {
                        showError(viewBinding.textView2, null)
                        viewBinding.loading = false
                    }
                }.addOnFailureListener {
                    showError(viewBinding.textView2, it.message)
                    viewBinding.loading = false
                }
            }
        }
    }

    private fun handleSuccessfulEntry(uid: String, tvEmail: EditText, tvPassword: EditText) {
        preferencesHelper.putString("USER_ID", uid)
        tvEmail.text.clear()
        tvPassword.text.clear()
        viewBinding.loading = false
        baseActivity!!.hideKeyboard()
        baseActivity!!.startActivity(
            Intent(
                baseActivity!!,
                MainActivity::class.java
            )
        )
    }

    fun onTextChange(editable: Editable?) {
        viewBinding.isPasswordValid = editable?.length!! > 5
    }

    fun switchUIs(): View.OnClickListener {
        return View.OnClickListener {
            viewBinding.isLogin = !viewBinding.isLogin!!
        }
    }

    fun checkLoggedInState() {
        if (auth.currentUser != null) {
            baseActivity!!.startActivity(
                Intent(
                    baseActivity!!,
                    MainActivity::class.java
                )
            )
        }
    }
}