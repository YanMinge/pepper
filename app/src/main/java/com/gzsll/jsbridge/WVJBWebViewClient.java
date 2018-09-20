package com.gzsll.jsbridge;

import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gzsll.Logger;
import com.gzsll.WVJBConstants;

/**
 * Created by sll on 2016/3/1.
 */
public class WVJBWebViewClient extends WebViewClient {

    private WVJBWebView mWVJBWebView;


    public WVJBWebViewClient(WVJBWebView wvjbWebView) {
        mWVJBWebView = wvjbWebView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(WVJBConstants.SCHEME)) {
            if (url.toUpperCase().indexOf(WVJBConstants.BRIDGE_LOADED) > 0) {
                mWVJBWebView.injectJavascriptFile();
            } else if (url.toUpperCase().indexOf(WVJBConstants.MESSAGE) > 0) {
                mWVJBWebView.flushMessageQueue();
            } else {
                Logger.d("UnkownMessage:" + url);
            }
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Log.e("webview", errorCode+"");
        Log.e("webview", description);
        Log.e("webview", failingUrl);
    }
}