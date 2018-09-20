package com.example.yanminge.pepper_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.object.conversation.Say;

import okhttp3.OkHttpClient;

public class main_activity extends AppCompatActivity implements RobotLifecycleCallbacks {
    private OkManager manager = OkManager.getInstance();
    private OkHttpClient clients;
    private String response_data;
    private TextView conversationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        QiSDK.register(this, this);
        conversationView = findViewById(R.id.main_layer);
    }

    @Override
    protected void onDestroy() {
        // Unregister the c for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(final QiContext qiContext) {
        // Create a new say action.
        displayHelloHuman();
        String jsonpath = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/8f50bc86-0ba4-4246-b577-400bd262251e?subscription-key=9d60b96ed33a4fbe875f74598b8582cb&staging=true&timezoneOffset=-360&q=%E4%B8%8D%E8%A6%81%E8%BE%A3";

        manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
            @Override
            public void onResponse(String result) {
                response_data = result;
                conversationView.setText(response_data);
                Say say = SayBuilder.with(qiContext) // Create the builder with the context.
                        .withText(response_data) // Set the text to say.
                        .build(); // Build the say action.
                // Execute the action.
                say.async().run();
            }
        });
    }

    @Override
    public void onRobotFocusLost() {
        // Nothing here.
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // Nothing here.
    }

    private void displayHelloHuman() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conversationView.setText("Hello human!");
            }
        });
    }

}
