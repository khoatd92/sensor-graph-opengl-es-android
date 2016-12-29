package com.example.tdkhoa.sensorgraph;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tdkhoa.sensorgraph.graph.GraphRenderer;
import com.example.tdkhoa.sensorgraph.graph.SensorGraph;
import com.example.tdkhoa.sensorgraph.model.GraphInfo;

import java.util.Random;

public class SensorDemoActivity extends Activity implements SensorEventListener {
    /**
     * Hold a reference to our GLSurfaceView
     */
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    private SensorManager sensorManager;
    private GraphRenderer graphRenderer;
    private SensorGraph xSensorGraph;
    private SensorGraph ySensorGraph;
    private SensorGraph zSensorGraph;
    private SensorGraph heartBeatGraph;

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
            initSensorGraph();
            glSurfaceView.setEGLContextClientVersion(2);
            graphRenderer = new GraphRenderer(this);
            glSurfaceView.setRenderer(graphRenderer);
            rendererSet = true;
            graphRenderer.addView(heartBeatGraph);
            graphRenderer.addView(xSensorGraph);
            graphRenderer.addView(ySensorGraph);
            graphRenderer.addView(zSensorGraph);
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(glSurfaceView);
    }

    private void initSensorGraph(){
        heartBeatGraph = new SensorGraph(this, new GraphInfo.Builder()
                .maxHeight(this.getResources().getDimensionPixelOffset(R.dimen.max_height))
                .xStartPosition(-1)
                .xEndPosition(1)
                .yPositionToDraw(0.5f)
                .color(Color.WHITE)
                .build());
        xSensorGraph = new SensorGraph(this, new GraphInfo.Builder().maxData(10)
                .maxHeight(this.getResources().getDimensionPixelOffset(R.dimen.max_height))
                .xStartPosition(-1)
                .xEndPosition(1)
                .yPositionToDraw(0)
                .color(Color.WHITE)
                .build());
        ySensorGraph = new SensorGraph(this, new GraphInfo.Builder().maxData(10)
                .maxHeight(this.getResources().getDimensionPixelOffset(R.dimen.max_height))
                .xStartPosition(-1)
                .xEndPosition(1).yPositionToDraw(-0.4f)
                .build());
        zSensorGraph = new SensorGraph(this, new GraphInfo.Builder()
                .maxData(10)
                .maxHeight(this.getResources().getDimensionPixelOffset(R.dimen.max_height))
                .xStartPosition(-1)
                .xEndPosition(1)
                .yPositionToDraw(-0.7f)
                .color(Color.BLUE)
                .build());
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
        xSensorGraph.addSensorData(x);
        ySensorGraph.addSensorData(y);
        zSensorGraph.addSensorData(z);

        Random rand = new Random();
        int randomNumber = rand.nextInt(50);
        heartBeatGraph.drawHeartBeat(randomNumber < 2);

    }


    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
