package com.example.appdev1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdev1.base.UIState
import com.example.appdev1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

            viewModel.fetchData()

            viewModel.data.observe(this, Observer { uiState ->
                Log.e("aboba", "1234", )

                when (uiState) {
                    is UIState.Loading -> {
                        // Handle loading state
                        Log.e("aboba", "load", )

                    }
                    is UIState.Success -> {
                        Log.e("aboba", "suc", )
                        val data = uiState.data
                        data?.let {
                            val intent = Intent(this, WebViewActivity::class.java)
                            intent.putExtra("url", it)  // Pass the data to WebViewActivity if needed
                            startActivity(intent)
                            finish()
                        }
                    }
                    is UIState.Error -> {
                        Log.e("aboba", "chort", )
                    }
                    else -> {
                        Log.e("aboba", "else", )

                        binding.navHost.isVisible = true
                    }
                }
            })
    }
}