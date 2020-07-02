//package com.bhaskar.utils.shaketoshare;
//
//import android.hardware.Sensor;
//import android.hardware.SensorManager;
//import android.util.Log;
//
//import com.bhaskar.utils.ShakeDetector;
//
//public final class ShakeToShareProvider {
//
//    private static final String TAG = "ShakeToShareProvider";
//    private Sensor accelerometerSensor;
//    private ShakeDetector shakeDetector;
//    private SensorManager sensorManager;
//    private OnDeviceShackedListener onDeviceShackedListener;
//
//    /**
//     * @param sensorManager
//     */
//    public ShakeToShareProvider(SensorManager sensorManager) {
//        this.sensorManager = sensorManager;
//    }
//
//
//    /***
//     *
//     * @param onDeviceShackedListener
//     */
//    public void initShakeDetector(OnDeviceShackedListener onDeviceShackedListener) {
//        this.onDeviceShackedListener = onDeviceShackedListener;
//        // ShakeDetector initialization
//        if (sensorManager != null) {
//            ////sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
//            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        }
//        shakeDetector = new ShakeDetector();
//        shakeDetector.setOnShakeListener(onShakeListener);
//    }
//
//    private final ShakeDetector.OnShakeListener onShakeListener = new ShakeDetector.OnShakeListener() {
//
//        @Override
//        public void onShake(int count) {
//            Log.d(TAG, "onShake: " + count);
//            unRegisterShake();
//            onDeviceShackedListener.onDeviceShacked();
//        }
//    };
//
//    public void registerShake() {
//        Log.d(TAG, "RegisterShake");
//        //Register the Session Manager Listener onResume
//        if (sensorManager != null) {
//            sensorManager.registerListener(shakeDetector, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
//            shakeDetector.setOnShakeListener(onShakeListener);
//        }
//    }
//
//    public void unRegisterShake() {
//        Log.d(TAG, "UnRegisterShake");
//        //Unregister the Sensor Manager onPause
//        if (sensorManager != null) {
//            shakeDetector.setOnShakeListener(null);
//            sensorManager.unregisterListener(shakeDetector);
//        }
//    }
//
//    public void flush() {
//        if (sensorManager != null) {
//            unRegisterShake();
//            sensorManager.flush(shakeDetector);
//        }
//    }
//}
