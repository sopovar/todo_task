package com.example.sweefttodolist.view.activity

import android.os.Bundle
import com.example.sweefttodolist.BR
import com.example.sweefttodolist.R
import com.example.sweefttodolist.base.BaseActivity
import com.example.sweefttodolist.databinding.ActivityLoginBinding
import com.example.sweefttodolist.view.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.baseActivity = this
        viewModel.init()
        viewModel.checkLoggedInState()
    }

    override fun getLayoutId() = R.layout.activity_login

    override fun getBindingVariable() = BR.data
}