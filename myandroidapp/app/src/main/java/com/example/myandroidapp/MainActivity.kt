package com.example.myandroidapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init_action()
    }

    fun init_action() {
        val check_button = findViewById<Button>(R.id.check)
        val next_button = findViewById<Button>(R.id.next)

        check_button.setOnClickListener {
            palindrome()
        }

        //next is to go to fragment_second_screen
        next_button.setOnClickListener {
            next()
        }
    }

    private fun palindrome() {
        val inputPalindrome = findViewById<EditText>(R.id.palindrome)
        val inputText = inputPalindrome.text.toString()

        val cleanedText = inputText.replace(Regex("[^A-Za-z0-9]"), "").toLowerCase()

        val isPalindrome = cleanedText == cleanedText.reversed()

        if (isPalindrome) {
            Toast.makeText(this, "The input is a palindrome", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "The input is not a palindrome", Toast.LENGTH_SHORT).show()
        }
    }

    fun next(){
        val nameEditText = findViewById<EditText>(R.id.name)
        val name = nameEditText.text.toString()

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(nameEditText.windowToken, 0)

        findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main_content).visibility = View.GONE

        val fragment = second_screen()

        val bundle = Bundle()
        bundle.putString("name", name)
        fragment.arguments = bundle

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}