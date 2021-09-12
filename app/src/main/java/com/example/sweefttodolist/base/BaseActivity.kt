package com.example.sweefttodolist.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.sweefttodolist.R
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Sopo Vardidze on 06.09.21
 */
abstract class BaseActivity<V: ViewDataBinding, T : BaseViewModel> : AppCompatActivity() {

    @field:Inject
    lateinit var viewModel: T

    lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(getBindingVariable(), viewModel)
        viewModel.binding = binding
    }

    abstract fun getLayoutId():Int

    abstract fun getBindingVariable():Int
}