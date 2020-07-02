//package com.bhaskar.utils.demos;
//
//import android.app.Activity;
//import android.content.Context;
//import android.hardware.SensorManager;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import android.util.Log;
//
//import com.bhaskar.utils.shaketoshare.OnDeviceShackedListener;
//import com.bhaskar.utils.shaketoshare.ShakeToShareProvider;
//
//public class ShakeDetectionDemo extends Activity implements OnDeviceShackedListener {
//    private ShakeToShareProvider shakeToShareProvider;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        shakeToShareProvider = new ShakeToShareProvider(sensorManager);
//        shakeToShareProvider.initShakeDetector(this);
//    }
//
//    @Override
//    public void onDeviceShacked() {
//        Log.d("onDeviceShacked", "Do something when Shacked happen here");
//    }
//
//    @Override
//    public void onPause() {
//        if (null != shakeToShareProvider) {
//            shakeToShareProvider.unRegisterShake();
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (null != shakeToShareProvider) {
//            shakeToShareProvider.registerShake();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (null != shakeToShareProvider) {
//            shakeToShareProvider.flush();
//        }
//        super.onDestroy();
//    }
//}
