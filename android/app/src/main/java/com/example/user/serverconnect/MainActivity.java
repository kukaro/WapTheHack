package com.example.user.serverconnect;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {
    private TextView tvMain;
    private EditText etMsg;
    private Button btnSubmit;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMain = findViewById(R.id.tvMain);
        etMsg = findViewById(R.id.etMsg);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener((view)->{
            JsonObject preJsonObject = new JsonObject();
            preJsonObject.addProperty("comment", etMsg.getText()+"");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(preJsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("reqMsg", jsonObject);
            etMsg.setText("");
        });

        try {
            socket = IO.socket("http://192.168.43.98:3100");
            socket.on(Socket.EVENT_CONNECT, (Object... objects) -> {
                JsonObject preJsonObject = new JsonObject();
                preJsonObject.addProperty("roomName", "myroom");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(preJsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("joinRoom",jsonObject);
            }).on("recMsg", (Object... objects) -> {
                JsonParser jsonParsers = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParsers.parse(objects[0] + "");
                runOnUiThread(()->{
                    tvMain.setText(tvMain.getText().toString()+jsonObject.get("comment").getAsString());
                });
            });
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}