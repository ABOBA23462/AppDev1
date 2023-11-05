package com.example.appdev1.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.appdev1.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWebView()
        setupDatabase()
    }

    private fun setupWebView() = with(binding) {
        wv.webViewClient = WebViewClient()
        wv.settings.javaScriptEnabled = true
        wv.settings.setSupportZoom(true)
    }

    override fun finish() = with(binding) {
        if (wv.canGoBack()) {
            wv.goBack()
        } else {
            super.finish()
        }
    }

    private fun setupDatabase() {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("webView")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java)
                handleWebViewVisibility(value)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Упс", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun handleWebViewVisibility(value: Boolean?) = with(binding) {
        if (value == true) {
            wv.visibility = View.GONE

        } else {
            navHost.visibility = View.GONE
            wv.visibility = View.VISIBLE
            wv.webViewClient = WebViewClient()
            wv.loadUrl("https://nomadtrust.space/zZYtDN")
        }
    }
}