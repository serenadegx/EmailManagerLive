package com.example.emailmanagerlive.emaildetail;

import android.webkit.WebView;

import androidx.databinding.BindingAdapter;

public class WebViewBindings {

    @BindingAdapter("android:html")
    public static void setHtml(WebView webView, String html) {
//        XRWebView.with(webView).simple().build().loadHtml(html, "text/html", "utf-8");
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
}
