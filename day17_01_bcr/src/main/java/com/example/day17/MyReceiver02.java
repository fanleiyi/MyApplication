package com.example.day17;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**接收系统拨号时发出的广播*/
public class MyReceiver02 extends BroadcastReceiver {
    public MyReceiver02() {
        Log.i("TAG","MyReceiver02");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("TAG","onReceive02");
        // 获得广播接收到的数据（此位置获得的是拨号的号码）
        String data = getResultData();
        // 重新设置拨号的号码
        setResultData("17954"+data);
    }
}
