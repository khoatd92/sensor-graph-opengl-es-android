package com.example.tdkhoa.sensorgraph;


import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;


public class GraphShaderProgram extends ShaderProgram {

    private static final String TAG = GraphShaderProgram.class.getSimpleName();

    // Uniform locations
    private final int uMatrixLocation;

    // Attribute locations
    private final int vPositionLocation;
    private final int vSensorValue;
    private final int uColorLocation;

    public GraphShaderProgram(Context context) {
        super(context, R.raw.shader_vertex,
                R.raw.shader_fragment);
        // Retrieve uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        // Retrieve attribute locations for the shader program.
        vPositionLocation = GLES20.glGetAttribLocation(program, V_POSITION);
        vSensorValue = GLES20.glGetAttribLocation(program, V_SENSOR_VALUE);
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        Log.d(TAG, "GraphShaderProgram: uMatrixLocation = " + uMatrixLocation
                + "\nvPositionLocation = " + vPositionLocation
                + "\nvSensorValue = " + vSensorValue
                + "\nuColorLocation = " + uColorLocation);
    }

    public void setUniforms(float[] matrix) {
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return vPositionLocation;
    }

    public int getColorAttributeLocation() {
        return uColorLocation;
    }

    public int getMatrixLocation() {
        return uMatrixLocation;
    }

    public int getPositionLocation() {
        return vPositionLocation;
    }

    public int getSensorValue() {
        return vSensorValue;
    }

    public int getColorLocation() {
        return uColorLocation;
    }
}