package com.example.sweefttodolist.view.activity

import android.os.Bundle
import com.example.sweefttodolist.BR
import com.example.sweefttodolist.R
import com.example.sweefttodolist.base.BaseActivity
import com.example.sweefttodolist.databinding.ActivityMainBinding
import com.example.sweefttodolist.view.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.baseActivity = this
        viewModel.init()

    }

    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable() = BR.data
}