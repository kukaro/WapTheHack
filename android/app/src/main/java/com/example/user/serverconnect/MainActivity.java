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
import android.telephony.SmsManager;
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

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private TextView textView;
//    public Button btStop;
    int warningNum;
    String testString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//-----------------------------Socket start------------------------------------

        try {
            socket = IO.socket("http://www.theceres.net:8801");
            socket.connect();
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
            }).on("sendMsg", (Object... objects) -> {
                JsonParser jsonParsers = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParsers.parse(objects[0] + "");
                System.out.println(jsonObject.get("msg").toString());
                testString = jsonObject.get("msg").toString();
                warningNum = 1;
                try {
                    textView.setText(testString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
//-----------------------------Socket end------------------------------------

//-----------------------------PERMISSION START-------------------------------

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // CALL_PHONE 이 허용되어있는지 확인해본다.
            int callpermissionResult = checkSelfPermission(CALL_PHONE);
            int smspermissionResult = checkSelfPermission(SEND_SMS);
            // permissionResult 에는 DENIED / GRANTED 중의 값이 저장된다
            if (callpermissionResult == PackageManager.PERMISSION_DENIED) {
                if (shouldShowRequestPermissionRationale(CALL_PHONE)) {
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
                                        requestPermissions(new String[]{CALL_PHONE, SEND_SMS}, 1000);
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
                    requestPermissions(new String[]{CALL_PHONE, SEND_SMS}, 1000);
                }
            } else {
                // 이미 권한이 있을 때
            }

        }
        //-------------------------------PERMISSION END----------------------------

        //-----------------------------NOTIFICATION---------------------------------
//        btStop = (Button) findViewById((R.id.btStop));

        new Thread(()->{
            Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);
        }).start();

//        btStop.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MyService.class);
//                onDestroy();
//            }
//        });
        //-----------------------------NOTIFICATION---------------------------------
    }
}