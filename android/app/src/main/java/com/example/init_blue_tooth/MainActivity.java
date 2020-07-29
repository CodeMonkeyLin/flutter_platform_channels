package com.example.init_blue_tooth;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodChannel;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.bluetooth.BluetoothAdapter;

public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "samples.flutter.dev";
//与Flutter Client端写一致的方法
  private static final int REQUEST_ENABLE_BT = 1;

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    GeneratedPluginRegistrant.registerWith(flutterEngine);

    new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
        .setMethodCallHandler(
        (call, result) -> {
        //与Flutter Client invokeMethod调用字符串标识符匹配
          if(call.method.equals("initBlueTooth")){
              boolean isEnabled = initBlueTooth();
              //initBlueTooth为后面安卓端需要调取的方法
              result.success(isEnabled);
          }
          else {
            result.notImplemented();
          }
        }
    );
  }

  private boolean initBlueTooth() {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter == null) {
        return false;
    }

    if (!mBluetoothAdapter.isEnabled()) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, REQUEST_ENABLE_BT);
        return false;
    }
    return true;
  }
}
