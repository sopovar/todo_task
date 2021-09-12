package com.example.sweefttodolist.view.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import com.example.sweefttodolist.base.BaseViewModel
import com.example.sweefttodolist.base.TODO_DB
import com.example.sweefttodolist.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import javax.inject.Inject

import android.content.Intent

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.sweefttodolist.utils.PreferencesHelper
import com.example.sweefttodolist.model.TodoModel
import com.example.sweefttodolist.utils.hideKeyboard
import com.example.sweefttodolist.view.activity.LoginActivity
import com.example.sweefttodolist.view.adapter.TodoAdapter


/**
 * Created by Sopo Vardidze on 07.09.21
 */
class MainViewModel @Inject constructor(
    context: Context,
    val firestore: FirebaseFirestore,
    val auth: FirebaseAuth,
    val preferencesHelper: PreferencesHelper
) : BaseViewModel(context) {

    lateinit var viewBinding: ActivityMainBinding

    private lateinit var todoCollectionRef: CollectionReference

    private lateinit var todoAdapter: TodoAdapter

    lateinit var uid: String

    fun init() {
        viewBinding = binding as ActivityMainBinding
        uid = preferencesHelper.getString("USER_ID").toString()
        todoCollectionRef = firestore.collection(TODO_DB)
        viewBinding.isLoading = true
        getTodoData()

        todoAdapter = TodoAdapter(baseActivity!!, context) { data ->
            update(data)
        }

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                delete(todoAdapter.todoList[position])
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(viewBinding.rvTodos)
    }

    private fun getTodoData() {
        auth.currentUser?.uid.let { uid ->
            if (uid != null) {
                todoCollectionRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
                    var data = mutableListOf<TodoModel>()
                    for (document in it.documents) {
                        val todo = TodoModel(
                            document.get("uid").toString(), document.get("todo").toString(),
                            document.get("done") as Boolean
                        )
                        todo.let { it1 -> data.add(it1) }
                    }
                    todoAdapter.todoList = data
                    todoAdapter.notifyDataSetChanged()
                    viewBinding.isLoading = false
                }.addOnFailureListener {
                    showError(viewBinding.textView3, it.message)
                    viewBinding.isLoading = false
                }
            }
        }
    }

    fun addTodoToDB(): View.OnClickListener {
        return View.OnClickListener {
            if (!viewBinding.inputTodo.text.isNullOrEmpty()) {
                baseActivity!!.hideKeyboard()
                viewBinding.isLoading = true
                todoCollectionRef.add(TodoModel(uid, viewBinding.inputTodo.text.toString(), false))
                    .addOnSuccessListener {
                        viewBinding.inputTodo.text.clear()
                        getTodoData()
                    }
            }
        }
    }

    fun update(todoModel: TodoModel) {
        viewBinding.isLoading = true

        val map = mutableMapOf<String, Any>()
        map["done"] = !todoModel.isDone

        todoCollectionRef
            .whereEqualTo("todo", todoModel.todo)
            .whereEqualTo("done", todoModel.isDone)
            .whereEqualTo("uid", todoModel.uid)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    for (document in it.documents) {
                        todoCollectionRef.document(document.id).set(
                            map,
                            SetOptions.merge()
                        ).addOnSuccessListener {
                            showSuccess("Todo updated", viewBinding.textView3)
                            getTodoData()
                        }
                    }
                }
            }.addOnFailureListener {
                showError(viewBinding.textView3, it.message)
            }
    }

    fun delete(todoModel: TodoModel) {
        viewBinding.isLoading = true

        val map = mutableMapOf<String, Any>()
        map["done"] = !todoModel.isDone

        todoCollectionRef
            .whereEqualTo("todo", todoModel.todo)
            .whereEqualTo("done", todoModel.isDone)
            .whereEqualTo("uid", todoModel.uid)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    for (document in it.documents) {
                        todoCollectionRef.document(document.id).delete().addOnSuccessListener {
                            getTodoData()
                        }
                    }
                }
            }.addOnFailureListener { showError(viewBinding.textView3, it.message) }
    }

    fun logOut(): View.OnClickListener {
        return View.OnClickListener {
            auth.signOut()
            preferencesHelper.clear()
            baseActivity!!.startActivity(Intent(baseActivity!!, LoginActivity::class.java))
        }
    }

    @Bindable
    fun getAdapter(): TodoAdapter {
        return todoAdapter
    }
}