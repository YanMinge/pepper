package com.example.yanminge.pepper_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.gzsll.jsbridge.WVJBWebView;

import org.json.JSONException;
import org.json.JSONObject;

public class main_activity extends AppCompatActivity implements RobotLifecycleCallbacks {

    private WVJBWebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        QiSDK.register(this, this);

        loadWebView();
        registerNativeMethods();
    }

    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    private void loadWebView() {
        webview = findViewById(R.id.webview);
        String file = "file:///android_asset/mblockly-zero/views/makeblockhd/index.html";
        webview.loadUrl(file);
    }

    private void registerNativeMethods() {
        webview.registerHandler("blocklyIsReady", new WVJBWebView.WVJBHandler() {
            @Override
            public void request(Object data, WVJBWebView.WVJBResponseCallback callback) {
                String json = data.toString();
            }
        });


        webview.registerHandler("sendViaBluetoothUnreliably", new WVJBWebView.WVJBHandler() {
            @Override
            public void request(Object data, WVJBWebView.WVJBResponseCallback callback) {
                String json = data.toString();
                try {
                    JSONObject jsonData = new JSONObject(json);
                    String cmd = (String) jsonData.get("cmdStr");

                    Log.d("sendViaBluetoothUnreliably", cmd);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // Create a new say action.
        Say say = SayBuilder.with(qiContext) // Create the builder with the context.
                .withText("Hello hujinghong!") // Set the text to say.
                .build(); // Build the say action.

        // Execute the action.
        say.run();
    }

    @Override
    public void onRobotFocusLost() {
        // Nothing here.
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // Nothing here.
    }
}