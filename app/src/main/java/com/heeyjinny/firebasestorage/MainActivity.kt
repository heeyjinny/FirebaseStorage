package com.heeyjinny.firebasestorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heeyjinny.firebasestorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //뷰바인딩
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}