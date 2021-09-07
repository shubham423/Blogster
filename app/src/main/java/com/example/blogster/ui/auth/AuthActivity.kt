package com.example.blogster.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blogster.MainActivity
import com.example.blogster.databinding.ActivityAuthBinding
import com.example.blogster.utils.Constants.LOG_OUT
import com.example.blogster.utils.Constants.TOKEN
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PrefsHelper.init(applicationContext)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logout = intent.getBooleanExtra(LOG_OUT, false)
        if (logout){
            PrefsHelper.remove(TOKEN)
        }
        val token=PrefsHelper.read(TOKEN,null)

        if (token!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}