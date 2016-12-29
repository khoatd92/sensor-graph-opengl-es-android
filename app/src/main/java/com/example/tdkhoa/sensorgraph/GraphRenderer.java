package com.example.tdkhoa.sensorgraph;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GraphRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private GraphShaderProgram graphProgram;
    private List<IView> views;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public GraphRenderer(Context context) {
        this.context = context;
        this.views = new ArrayList<>();
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        this.graphProgram = new GraphShaderProgram(context);
        this.graphProgram.useProgram();
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        for(int i = 0 ; i < views.size(); i++){
            views.get(i).bindData(graphProgram, projectionMatrix, viewMatrix);
            views.get(i).draw(graphProgram);
        }
    }

    public void addView(IView view) {
        views.add(view);
    }
}
