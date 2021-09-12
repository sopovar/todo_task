package com.example.sweefttodolist.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sweefttodolist.R
import com.example.sweefttodolist.databinding.LayoutTodoItemBinding
import com.example.sweefttodolist.model.TodoModel
import com.example.sweefttodolist.view.viewmodel.TodoItemViewModel
import kotlinx.android.synthetic.main.layout_todo_item.view.*

/**
 * Created by Sopo Vardidze on 11.09.21
 */
class TodoAdapter(
    val activity: Activity,
    val context: Context,
    val onItemSelection: (TodoModel) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var todoList = mutableListOf<TodoModel>()

    inner class TodoViewHolder(val binding: LayoutTodoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutTodoItemBinding>(layoutInflater,
            R.layout.layout_todo_item, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = TodoItemViewModel(context, todoList[position], onItemSelection)
        currentItem.baseActivity = activity
        holder.binding.item = currentItem
    }

    override fun getItemCount() = todoList.size
}