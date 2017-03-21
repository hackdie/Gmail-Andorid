package com.skalala.gmail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.skalala.module.home.HomeView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.mainFrame, HomeView()).commit()
    }
}
