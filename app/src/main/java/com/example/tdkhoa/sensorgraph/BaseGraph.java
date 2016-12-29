package com.example.tdkhoa.sensorgraph;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class BaseGraph implements IView {

    private static final String TAG = BaseGraph.class.getSimpleName();
    private final static int BYTES_PER_FLOAT = 4;
    protected float[] modelMatrix = new float[16];

    protected Context context;
    protected float heightRatio;
    protected GraphInfo graphInfo;

    public BaseGraph(Context context, GraphInfo graphInfo) {
        this.context = context;
        this.graphInfo = graphInfo;
        this.heightRatio = calculatorHeightRatio();
    }

    private float calculatorHeightRatio() {
        DisplayMetrics lDisplayMetrics = context.getResources().getDisplayMetrics();
        int heightPixels = lDisplayMetrics.heightPixels;
        Log.d(TAG, "SpeedGraph: maxHeight: " + graphInfo.getMaxHeight() + " - default height: " + heightPixels);
        return (float) graphInfo.getMaxHeight() / (float) heightPixels;
    }

    protected FloatBuffer allocateBuffer(float[] data){
        return ByteBuffer
                .allocateDirect(data.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }
}
