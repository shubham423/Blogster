package com.example.blogster.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blogster.MainActivity
import com.example.blogster.databinding.ActivityAuthBinding
import com.example.blogster.utils.Constants.LOG_OUT
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val logout = intent.getBooleanExtra(LOG_OUT, false)
        if (logout){
            val pref: SharedPreferences =
                this.getSharedPreferences("BLOGSTER", MODE_PRIVATE)
            pref.edit().remove("TOKEN").apply()
        }
        val preferences =
            this.getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
        val token=preferences.getString("TOKEN", null)

        if (token!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}