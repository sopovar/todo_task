package com.example.sweefttodolist.view.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import com.example.sweefttodolist.base.BaseViewModel
import com.example.sweefttodolist.model.TodoModel
import javax.inject.Inject

/**
 * Created by Sopo Vardidze on 11.09.21
 */
class TodoItemViewModel @Inject constructor(
    context: Context, val model: TodoModel, val onItemSelection: (TodoModel) -> Unit
) : BaseViewModel(context) {

    @Bindable
    fun getTodoName(): String {
        return model.todo
    }

    @Bindable
    fun getIsTodoChecked(): Boolean {
        return model.isDone
    }

    @Bindable
    fun getOnItemCLicked(): View.OnClickListener {
        return View.OnClickListener {
            onItemSelection.invoke(model)
        }
    }
}