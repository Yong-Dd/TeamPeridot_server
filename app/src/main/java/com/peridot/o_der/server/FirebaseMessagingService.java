package com.peridot.o_der.server;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FMS";

    public FirebaseMessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String token) {     // 등록 ID가 갱신되었을 때 자동으로 호출
        super.onNewToken(token);
        Log.e(TAG, "onNewToken 호출됨: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {   // 상대방으로부터 메시지를 받았을 때 자동으로 호출
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "onMessageReceived() 호출됨.");

        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String contents = data.get("contents");
        Log.d("service", "from : " + from + ", contents : " + contents);

        sendToActivity(from, contents);

    }

    public void sendToActivity(String from, String contents) {
        Intent intent = new Intent(getApplicationContext(), ServerMain.class);
        intent.putExtra("from", from);
        intent.putExtra("contents", contents);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        getApplicationContext().startActivity(intent);
    }


}