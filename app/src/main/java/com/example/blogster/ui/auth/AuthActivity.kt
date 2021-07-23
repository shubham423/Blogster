package com.example.blogster.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blogster.MainActivity
import com.example.blogster.R
import com.example.blogster.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences =
            this.getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
        val token=preferences.getString("TOKEN", null)

        if (token!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}