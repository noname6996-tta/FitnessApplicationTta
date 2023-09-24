package com.tta.fitnessapplication.view.activity

import android.webkit.WebViewClient
import com.tta.fitnessapplication.databinding.ActivityWebViewBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    override fun getDataBinding(): ActivityWebViewBinding {
        return ActivityWebViewBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        with(binding){
            // WebViewClient allows you to handle
            // onPageFinished and override Url loading.
            webView.webViewClient = WebViewClient()

            // this will load the url of the website
            val url = intent.getStringExtra("url")
            if (url != null) {
                webView.loadUrl(url.toString())
            }
            // this will enable the javascript settings, it can also allow xss vulnerabilities
            webView.settings.javaScriptEnabled = true

            // if you want to enable zoom feature
            webView.settings.setSupportZoom(true)

            topAppBar.setOnClickListener {
                onBackPressed()
            }
        }

    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }
    }
}