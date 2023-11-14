package com.example.appdev1.ui.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appdev1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWebView()

        val url = "https://uzako.site/G4HVkV"
        binding.wv.loadUrl(url)
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
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                if (error != null && (error.errorCode == ERROR_HOST_LOOKUP ||
                            error.errorCode == ERROR_CONNECT ||
                            error.errorCode == ERROR_TIMEOUT ||
                            error.errorCode == ERROR_UNSUPPORTED_SCHEME)
                ) {
                    binding.wv.visibility = View.GONE
                    binding.navHost.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.wv.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.wv.restoreState(savedInstanceState)
    }

    override fun finish() {
        if (binding.wv.canGoBack())
            binding.wv.goBack()
        else
            super.finish()
    }
}