package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_todo.*
import kotlinx.android.synthetic.main.todo_item_cell.*

class CreateTodoActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        add_todo_button_create_todo.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val title = title_textview_create_todo.text.toString()
        val content = content_textview_create_todo.text.toString()


        val todo = hashMapOf(
            "title" to title,
            "content" to content,
        )

        db.collection("todos").add(todo)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "空白の項目があります", Toast.LENGTH_SHORT).show()
            }


    }
}