package com.example.user.serverconnect;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class MyService extends Service {
    public MyService() {
    }
    boolean run = true;
    public int Standard_1 = 0;
    public int Standard_2 = 1;
    public int Standard_3 = 2;
    public int Standard_4 = 3;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            int Risk = 1;
            while (run) {
                if (Risk >= Standard_1 && Risk < Standard_2 && Risk < Standard_3 && Risk < Standard_4) {
                    show_1();
                } else if (Risk >= Standard_1 && Risk >= Standard_2 && Risk < Standard_3 && Risk < Standard_4) {
                    show_2();
                } else if (Risk >= Standard_1 && Risk >= Standard_2 && Risk >= Standard_3 && Risk < Standard_4) {
                    show_3();
                } else if (Risk >= Standard_1 && Risk >= Standard_2 && Risk >= Standard_3 && Risk >= Standard_4) {
                    show_4();
                }
                try {
                    Log.e("DEBUG", "onStartCommand: " + "쓰레드 실행중");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return START_REDELIVER_INTENT;
    }

    private void show_1() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("위험합니다");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());

    }

    private void show_2() {//보호자에게 문자 가능 메소드 추가
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        String phoneNo = new String("010-8201-5102");
        String sms = new String("ㅇㅇㅇ 님이 위험합니다 !");
        run = false;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int smspermissionResult = checkSelfPermission(SEND_SMS);
            if (smspermissionResult == PackageManager.PERMISSION_GRANTED){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            }
}

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("보호자에게 문자가 갔습니다");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());

    }

    private void show_3() {//전기 가스 차단하는 메소드
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("전기, 가스가 차단되었습니다");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());

    }

    private void show_4() {//자동으로 신고하는 메소드
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        builder.setContentTitle("경고 알림");
        builder.setContentText("119로 자동 신고되었습니다");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int callpermissionResult = checkSelfPermission(CALL_PHONE);
            if (callpermissionResult == PackageManager.PERMISSION_GRANTED){
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-8201-5102"));
                startActivity(call);
                }
        }
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Bitmap largeIcon= BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notifications_black_24dp);
        builder.setLargeIcon(largeIcon);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        long[] vibrate={0,100,200,300};
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true);

        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1,builder.build());

    }

    public void onDestroy()
    {
        super.onDestroy();

    }


    public void RemoveNotification(View view)
    {
        hide();
    }

    private void hide() {
        NotificationManagerCompat.from(this).cancel(1);
    }

}