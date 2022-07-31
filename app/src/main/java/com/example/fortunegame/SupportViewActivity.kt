package com.example.fortunegame


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fortunegame.HelpTools.Companion.AEKGTISU
import com.example.fortunegame.HelpTools.Companion.IKYBKKUP

class SupportViewActivity : AppCompatActivity() {
    var extraHeaders: MutableMap<String, String> = HashMap()
    private lateinit var uriValueCallback: ValueCallback<Array<Uri?>>
    private lateinit var webView: WebView
    private lateinit var container: FrameLayout
    private lateinit var progress: ProgressBar
    private var secondaryWebView: WebView? = null

    private val pfiltaw: String? by lazy { intent?.getStringExtra(WZCCA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        container = FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        progress = ProgressBar(this).apply {
            val size = 48.px
            layoutParams = FrameLayout.LayoutParams(size, size).apply {
                gravity = Gravity.CENTER
            }
        }

        webView = WebView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            visibility = View.GONE
        }

        listOf(webView, progress).forEach { v -> container.addView(v) }

        initWebView()

        setContentView(container)

        acceptCookies()

        extraHeaders["X-Requested-With"] = "app-view"

        var url = "https://$IKYBKKUP/$AEKGTISU"
        if (pfiltaw != null) {
            url += "?uid=$pfiltaw"
        }

        webView.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.apply {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            this.webViewClient = object : WebViewClient() {

                override fun onPageFinished(layout: WebView?, webURL: String?) {
                    super.onPageFinished(layout, webURL)

                    visibility = View.VISIBLE
                    this@SupportViewActivity.progress.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    when {
                        url.startsWith("mailto:") -> {
                            startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse(url)))
                        }

                        url.startsWith("tel:") -> {
                            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                        }

                        else -> view?.loadUrl(url)
                    }

                    return true
                }
            }

            this.webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    layout: WebView?, fpcBack: ValueCallback<Array<Uri?>>,
                    fileC: FileChooserParams?
                ): Boolean {
                    uriValueCallback = fpcBack

                    startFileChooser()

                    return true
                }

                override fun onCloseWindow(window: WebView?) {
                    super.onCloseWindow(window)

                    secondaryWebView?.destroy()

                    container.removeView(secondaryWebView)

                    visibility = View.VISIBLE
                }

                override fun onCreateWindow(
                    view: WebView?, isMessage: Boolean,
                    isPlayerGest: Boolean, message: Message?
                ): Boolean {
                    val go = message?.obj as WebView.WebViewTransport?

                    secondaryWebView = WebView(context).apply {
                        setLayerType(View.LAYER_TYPE_HARDWARE, null)

                        this.webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String
                            ): Boolean {
                                view?.loadUrl(url)

                                return true
                            }
                        }

                        setJSSettings()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                        }
                    }

                    container.addView(secondaryWebView)
                    visibility = View.GONE
                    go?.webView = secondaryWebView
                    message?.sendToTarget()

                    return true
                }
            }

            setJSSettings()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun WebView.setJSSettings() {
        settings.apply {
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        requestFocus(View.FOCUS_DOWN)
    }

    @Suppress("DEPRECATION")
    private fun startFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)

        intent.type = "image/*"

        startActivityForResult(
            Intent.createChooser(intent, "Image Chooser"),
            XNERUKDX_IBGZT
        )
    }

    private fun acceptCookies() {
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
    }

    override fun onResume() {
        super.onResume()
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
    }

    override fun onBackPressed() {
        if (secondaryWebView?.visibility == View.VISIBLE) {
            if (secondaryWebView?.canGoBack() == true) {
                secondaryWebView?.goBack()
            } else {
                secondaryWebView?.destroy()
                container.removeView(secondaryWebView)
                webView.visibility = View.VISIBLE
            }
        } else if (webView.canGoBack()) {
            val list = webView.copyBackForwardList();

            if (list.currentIndex == 1) {
                super.onBackPressed()
            } else {
                webView.goBack()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(
        codeAccept: Int,
        codeFinal: Int,
        base: Intent?
    ) {
        super.onActivityResult(codeAccept, codeFinal, base)

        if (codeAccept == XNERUKDX_IBGZT) {
            val final =
                if (base == null || codeFinal != Activity.RESULT_OK) null else base.data

            if (final != null) {
                uriValueCallback.onReceiveValue(arrayOf(final))
            }
        }
    }

    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    companion object {
        const val WZCCA = "utfihhea"
        private const val XNERUKDX_IBGZT = 456
    }
}