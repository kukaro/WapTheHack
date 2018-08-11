package com.example.user.serverconnect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class MyService extends Service {
    private MediaPlayer mp3;
    private boolean isInit = true;

    public MyService() {
    }

    SoundPool soundp;
    int tom;
    public int Standard_1 = 0;
    public int Standard_2 = 1;
    public int Standard_3 = 2;
    public int Standard_4 = 3;
    int warningNum;

    public void setWarningNum(int warningNum) {
        this.warningNum = warningNum;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isInit) {
            isInit = false;
            new Thread(() -> {

                while (true) {
                    Log.e("MyService", "onStartCommand: " + MainActivity.warningNum);
                    if (MainActivity.warningNum == 3) {
                        try {
                            show_3();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (MainActivity.warningNum == 4) {
                        try {
                            show_4();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Log.e("DEBUG", "onStartCommand: " + "쓰레드 실행중");
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return START_REDELIVER_INTENT;
    }

    private void show_1() throws IOException {
        Vibrator mVibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("위험합니다");

        mVibrate.vibrate(2000);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());

        mp3 = MediaPlayer.create(this, R.raw.siren);
        mp3.start();
    }

    private void show_2() throws IOException {//보호자에게 문자 가능 메소드 추가
        Vibrator mVibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        String phoneNo = new String("010-8201-5102");
        String sms = new String("ㅇㅇㅇ 님이 위험합니다 !");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int smspermissionResult = checkSelfPermission(CALL_PHONE);
            if (smspermissionResult == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            }
        }

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("보호자에게 문자가 갔습니다");
        mVibrate.vibrate(2000);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());

        mp3 = MediaPlayer.create(this, R.raw.siren);
        mp3.start();
    }

    private void show_3() throws IOException {//전기 가스 차단하는 메소드
        Vibrator mVibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("전기, 가스가 차단되었습니다");
        mVibrate.vibrate(2000);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());

        mp3 = MediaPlayer.create(this, R.raw.siren);
        mp3.start();
    }

    private void show_4() throws IOException {//자동으로 신고하는 메소드
        Vibrator mVibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("119로 자동 신고되었습니다");
        mVibrate.vibrate(2000);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int callpermissionResult = checkSelfPermission(CALL_PHONE);
            if (callpermissionResult == PackageManager.PERMISSION_GRANTED) {
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-8201-5102"));
                startActivity(call);
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());

        mp3 = MediaPlayer.create(this, R.raw.siren);
        mp3.start();
    }

    public void onDestroy() {
        super.onDestroy();
    }


    public void RemoveNotification(View view) {
        hide();
    }

    private void hide() {
        NotificationManagerCompat.from(this).cancel(1);
    }

}