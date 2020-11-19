package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_item_cell.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getTodo()
    }

    private fun getTodo() {
        val adapter = GroupAdapter<ViewHolder>()
        val email = auth.currentUser?.email
        db.collection("todos").get()
            .addOnCompleteListener {
                val documents = it.result

                if (documents != null) {
                    for (document in documents) {
                        val owner = document.data.get("owner").toString()
                        if (owner == email) {
                            val title = document.data.get("title").toString()
                            val content = document.data.get("content").toString()
                            val todo = Todo(title, content)
                            adapter.add(TodoItem(todo))
                        }
                    }
                }
            }
        recycler_view_main.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator: MenuInflater = menuInflater
        inflator.inflate(R.menu.nav_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.create_todo -> {
                val intent = Intent(this, CreateTodoActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
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