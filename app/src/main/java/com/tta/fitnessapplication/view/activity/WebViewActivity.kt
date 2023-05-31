package com.tta.fitnessapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.appbar.MaterialToolbar
import com.tta.fitnessapplication.R

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webView)
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        var url = intent.getStringExtra("url")
        if (url!=null){
            webView.loadUrl(url.toString())
        }
        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)

        var btn = findViewById<MaterialToolbar>(R.id.topAppBar)
        btn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }
    }
}