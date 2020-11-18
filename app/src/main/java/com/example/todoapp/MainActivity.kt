package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_item_cell.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(TodoItem(Todo("遊ぶ", "家で")))
        adapter.add(TodoItem(Todo("遊ぶ", "友達の家で")))
        adapter.add(TodoItem(Todo("遊ぶ", "カラオケ")))
        recycler_view_main.adapter = adapter
    }
}

class TodoItem(val todo: Todo): Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.todo_item_cell
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.title_textView_todo_item_view.text = todo.title
        viewHolder.itemView.content_textview_todo_item_view.text = todo.content
    }
}

class Todo(val title: String, val content: String)