package com.example.appdev1.ui.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appdev1.databinding.ActivityMainBinding
import im.delight.android.webview.AdvancedWebView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: AdvancedWebView
    private var initialUrl: String = "https://uzako.site/G4HVkV"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        webView = binding.wv

        setupWebView()

        // Восстановление состояния
        if (savedInstanceState != null) {
            initialUrl = savedInstanceState.getString("initialUrl", "https://github.com")
        }
        if (savedInstanceState == null) {
            webView.loadUrl(initialUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = binding.wv.settings

        webSettings.javaScriptEnabled = true

        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.wv, true)

        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false

        binding.wv.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                return true
            }
        }

        binding.wv.webViewClient = object : WebViewClient() {
            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)

                val statusCode = errorResponse?.statusCode ?: 0
                if (statusCode == 404) {
                    handleError()
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                handleError()
            }
        }
    }

    private fun handleError() {
        binding.wv.visibility = View.GONE
        binding.navHost.visibility = View.VISIBLE
    }

    override fun finish() {
        if (binding.wv.canGoBack()) {
            binding.wv.goBack()
            Log.d("WebViewActivity", "goBack")
        } else {
            super.finish()
            Log.d("WebViewActivity", "super.finish()")
        }
    }
}


//Заменить устаревший код

