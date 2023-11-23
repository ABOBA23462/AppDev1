package com.example.appdev1.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.appdev1.R
import com.example.appdev1.databinding.ActivityWebViewBinding
import im.delight.android.webview.AdvancedWebView

class WebViewActivity : AppCompatActivity(R.layout.activity_web_view), AdvancedWebView.Listener {

    private val binding by viewBinding(ActivityWebViewBinding::bind)
    private lateinit var mWebView: AdvancedWebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val webSettings: WebSettings = binding.wv.settings

        webSettings.javaScriptEnabled = true

        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.wv, true)

        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        mWebView = findViewById(R.id.wv)
        mWebView.setListener(this, this)
        mWebView.setMixedContentAllowed(false)
        mWebView.setCookiesEnabled(true)
        mWebView.setMixedContentAllowed(true)
        mWebView.setThirdPartyCookiesEnabled(true)
        mWebView.settings.javaScriptEnabled = true
        mWebView.loadUrl("https://uzako.site/G4HVkV")

        mWebView.webChromeClient = object : WebChromeClient() {
            private var mCustomView: View? = null
            private var mOriginalSystemUiVisibility = 0
            private var mOriginalOrientation = 0
            private var mCustomViewCallback: CustomViewCallback? = null

            override fun onPermissionRequest(request: PermissionRequest?) {
                if (!isCameraPermissionGranted()) {
                    getCameraAndStoragePermissions()
                    mWebView.pauseTimers()
                    request?.grant(request.resources)
                } else {
                    request?.grant(request.resources)
                }
            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                if (mCustomView != null) {
                    onHideCustomView()
                    return
                }

                mCustomView = view
                mOriginalSystemUiVisibility = window.decorView.systemUiVisibility
                mOriginalOrientation = requestedOrientation

                mCustomViewCallback = callback

                val decor = window.decorView as FrameLayout
                decor.addView(
                    mCustomView, FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )

                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE
            }

            override fun onHideCustomView() {
                val decor = window.decorView as FrameLayout
                decor.removeView(mCustomView)
                mCustomView = null

                window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
                requestedOrientation = mOriginalOrientation

                mCustomViewCallback?.onCustomViewHidden()
                mCustomViewCallback = null
            }
        }
        mWebView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                return if (url.startsWith("http://") || url.startsWith("https://")) {
                    false
                } else {
                    try {
                        if (url.startsWith("intent:")) {
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            view.context.startActivity(intent)
                        } else {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            view.context.startActivity(intent)
                        }
                        true
                    } catch (e: Exception) {
                        true
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mWebView.resumeTimers()
    }

    fun isCameraPermissionGranted(): Boolean {
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCameraAndStoragePermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 1
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!mWebView.onBackPressed()) return

        super.onBackPressed()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        mWebView.onActivityResult(requestCode, resultCode, intent)
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
    }

    override fun onExternalPageRequest(url: String?) {
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {

    }

    override fun onPageFinished(url: String?) {

    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}