package com.todoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.todoapp.R
import com.todoapp.fragment.currentToDosBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}