package com.example.user.sockettest;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class One {
    private Socket mSocket;

    protected void onCreate(Bundle savedInstanceState) {
        // 설명의 편의를 위해 onCreate()메서드에 추가하였으나,
        // 꼭 onCreate() 메서드에 위치할 필요는 없을 것 같습니다.
        try {
            mSocket = IO.socket("localhost:3100");
            mSocket.connect();
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("chat-message", onMessageReceived);
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }
    // Socket서버에 connect 된 후, 서버로부터 전달받은 'Socket.EVENT_CONNECT' Event 처리.
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject receivedData = (JSONObject) args[0];
            if(receivedData != null)
                Log.d("로그 이름1",args.toString());
            else
                Log.d("로그 이름1","실패임");
        }
    };

    // 서버로부터 전달받은 'chat-message' Event 처리.
    private Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.
            JSONObject receivedData = (JSONObject) args[0];
            if(receivedData != null)
                Log.d("로그 이름2",args.toString());
            else
                Log.d("로그 이름2","실패임");
        }
    };
}