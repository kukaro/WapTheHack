package com.example.user.serverconnect;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private EditText etMsg;
    private Button btnSubmit;
    private Button button;
    private TextView textView;
    int warningNum;
    String testString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMsg = findViewById(R.id.etMsg);
        btnSubmit = findViewById(R.id.btnSubmit);
        textView = findViewById(R.id.textView);

//-----------------------------Socket start------------------------------------

        try {
            socket = IO.socket("http://192.168.43.36:8801");
            socket.on(Socket.EVENT_CONNECT, (Object... objects) -> {
                JsonObject preJsonObject = new JsonObject();
                preJsonObject.addProperty("roomID", "1");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(preJsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("joinRoom", jsonObject);

                socket.on("sendMsg", (Object... msgObjects) -> {
                    JsonParser jsonParsers = new JsonParser();
                    JsonObject msgJson = (JsonObject) jsonParsers.parse(msgObjects[0] + "");
                    System.out.println(msgJson.get("msg").toString());
                    testString = msgJson.get("msg").toString();
                    warningNum = 1;

                    try {
                        System.out.println("try catch");
                        textView.setText(testString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
//-----------------------------Socket end------------------------------------

//-----------------------------???------------------------------------
        btnSubmit.setOnClickListener((view) -> {
            JsonObject preJsonObject = new JsonObject();
            preJsonObject.addProperty("comment", etMsg.getText() + "");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(preJsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("reqMsg", jsonObject);
            etMsg.setText("");
        });
//-----------------------------???------------------------------------

// ----------------------------Call Phone Start----------------------------------------

        button = (Button) findViewById(R.id.permission);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // CALL_PHONE 이 허용되어있는지 확인해본다.
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    // permissionResult 에는 DENIED / GRANTED 중의 값이 저장된다
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            // shouldShowRequestPermissionRationale 메소드는
                            // 요청이 거부된 적 있다 = True
                            // 요청이 거부된 적 없다 = False
                            // 를 리턴한다.
                            // 이 아래의 내용은 True , 즉 요청이 거부된 적 있을 때에 실행되는 코드이다.
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("<--권한 요청-->")
                                    .setMessage("이 어플은 << 전화 걸기 >> 권한을 필요로 합니다. 동의하고 계속하시겠습니까 ?")
                                    .setPositiveButton("YES !", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 새로운 인스턴스(onClickListener)를 생성했기때문에 버전 체크를 한번 더 해준다 -> 왜지 ?
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                                // CALL_PHONE 권한을 android os 에 요청한다.
                                            }
                                        }
                                    })
                                    .setNegativeButton("NO ~", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity.this, "권한 요청 거부", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            // 최초로 권한을 요청할 떄
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    } else {
                        // 이미 권한이 있을 때
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2974-1693"));
                        startActivity(intent);

                    }
                }
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            // 사용자가 "허용" 했을 때
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2974-1693"));

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(MainActivity.this, "권한 요청 거부", Toast.LENGTH_SHORT).show();
            }
        }
    }

// ----------------------------Call Phone End----------------------------------------

}