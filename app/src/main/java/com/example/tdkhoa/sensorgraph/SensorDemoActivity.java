package com.example.tdkhoa.sensorgraph;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


import java.util.Random;

public class SensorDemoActivity extends Activity implements SensorEventListener {
    /**
     * Hold a reference to our GLSurfaceView
     */
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    private SensorManager sensorManager;
    private long lastUpdate;
    private GraphRenderer graphRenderer;
    private SpeedGraph speedGraph;
    private SpeedGraph speedGraph1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        glSurfaceView = new GLSurfaceView(this);

        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager
                .getDeviceConfigurationInfo();
        final boolean supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")));

        if (supportsEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            graphRenderer = new GraphRenderer(this);
            glSurfaceView.setRenderer(graphRenderer);
            rendererSet = true;
            speedGraph = new SpeedGraph(this, new GraphInfo.Builder().maxData(100)
                    .maxHeight(this.getResources().getDimensionPixelOffset(R.dimen.max_height))
                    .xStartPosition(-0.5f)
                    .xEndPosition(0.5f).yPositionToDraw(-0.5f)
                    .build());
            speedGraph1 = new SpeedGraph(this, new GraphInfo.Builder()
                    .maxData(100)
                    .maxHeight(this.getResources().getDimensionPixelOffset(R.dimen.max_height))
                    .xStartPosition(-0.5f)
                    .xEndPosition(0.5f)
                    .yPositionToDraw(-0.7f)
                    .build());
            graphRenderer.addView(speedGraph);
            graphRenderer.addView(speedGraph1);
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        final float x = values[0];
        float y = values[1];
        float z = values[2];
        float g = (float) Math.sqrt(x * x + y * y + z * z);
        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);


        Random rand = new Random();
        int randomNum = 0 + rand.nextInt((200 - 0) + 1);

        if (speedGraph != null) {
            speedGraph.addSensorData(randomNum);
            randomNum = 0 + rand.nextInt((200 - 0) + 1);
            speedGraph1.addSensorData(randomNum);
        }


        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
        }
    }


    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
