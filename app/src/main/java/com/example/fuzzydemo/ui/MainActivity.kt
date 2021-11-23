package com.example.fuzzydemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fuzzydemo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction().replace(R.id.fragmentFrame, MovieListFragment()).commit()
        }
    }
}