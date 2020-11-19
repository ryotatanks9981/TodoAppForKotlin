package com.example.todoapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        login_button_login.setOnClickListener {
            if (!userExist()) {
                createUser()
            } else {
                performLogin()
            }
        }

    }

    private fun createUser() {
        val email = email_textview_login.text.toString()
        val password = password_textview_login.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = hashMapOf(
                    "email" to email,
                    "password" to password,
                )
                db.collection("users").document(email).set(user)
                    .addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
            }
    }

    private fun performLogin() {
        val email = email_textview_login.text.toString()
        val password = password_textview_login.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "メールアドレスまたはパスワードが間違っています", Toast.LENGTH_SHORT).show()
            }
    }

    private fun userExist(): Boolean {
        // email を渡してそのユーザーがいればtrue
        var flag = false
        val email = email_textview_login.text.toString()

        db.collection("users").document(email).get()
            .addOnSuccessListener {
                flag = true
            }
        return flag
    }
}