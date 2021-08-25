package com.example.blogster

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.blogster.databinding.ActivityMainBinding
import com.example.blogster.ui.auth.AuthActivity
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.ArticleDetailsCallback
import com.example.blogster.ui.feed.MyArticleDetailsCallback
import com.example.blogster.utils.Constants.LOG_OUT
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ArticleDetailsCallback, MyArticleDetailsCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.feedFragment,
            R.id.articlesFragment,
            R.id.profileFragment,
            R.id.createArticleFragment
        ).build()

        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val preferences =
            this.getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
        val token=preferences.getString("TOKEN", null)

        token.let {
            authViewModel.getCurrentUser(it!!)
        }



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                authViewModel.logout()
                val intent=Intent(this,AuthActivity::class.java)
                intent.putExtra(LOG_OUT,true)
                startActivity(intent).also {
                    finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onArticleClicked(articleId: String) {

        Log.d("FeedFragment","callback called")
        navController.navigate(
            R.id.action_global_details_Fragment,
            bundleOf(
                resources.getString(R.string.arg_article_id) to articleId
            )
        )
    }

    override fun onMyArticleClicked(articleId: String) {
        Log.d("FeedFragment","callback called")
        navController.navigate(
            R.id.action_global_myArticlesDetailsFragment,
            bundleOf(
                resources.getString(R.string.arg_article_id) to articleId
            )
        )
    }

}