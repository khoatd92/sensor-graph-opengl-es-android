package com.example.tdkhoa.sensorgraph.graph;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.example.tdkhoa.sensorgraph.glprogram.GraphShaderProgram;
import com.example.tdkhoa.sensorgraph.glprogram.ShaderProgram;
import com.example.tdkhoa.sensorgraph.model.GraphInfo;

import java.nio.FloatBuffer;

public class SensorGraph extends BaseGraph {

    private static final String TAG = SensorGraph.class.getSimpleName();

    private int SENSOR_HISTORY_LENGTH = 100;
    private float[] sensorData = new float[SENSOR_HISTORY_LENGTH * 2];

    private float[] xPos = new float[SENSOR_HISTORY_LENGTH];

    private int sensorDataIndex;
    private FloatBuffer vertexData;
    private FloatBuffer vertexSensor;

    public SensorGraph(Context context, GraphInfo graphInfo) {
        super(context, graphInfo);
        generateXPos();

        vertexData = allocateBuffer(xPos);
        vertexData.put(xPos);

        vertexSensor = allocateBuffer(sensorData);
        vertexSensor.put(sensorData);
    }

    private void generateXPos() {
        for (int i = 0; i < SENSOR_HISTORY_LENGTH; i++) {
            float t = (float) i / (float) (SENSOR_HISTORY_LENGTH - 1);
            xPos[i] = graphInfo.getxEndPosition() * (1.f - t) + graphInfo.getxStartPosition() * t;
        }
    }

    public void addSensorData(float data) {
        float position;
        if (data > graphInfo.getMaxData()) {
            graphInfo.setMaxData(data);
        }
        position = (data / graphInfo.getMaxData()) * heightRatio;
        Log.d(TAG, "addSensorData: data = " + position);
        sensorData[sensorDataIndex] = position;
        sensorData[SENSOR_HISTORY_LENGTH + sensorDataIndex] = position;
        sensorDataIndex = (sensorDataIndex + 1) % SENSOR_HISTORY_LENGTH;

        vertexSensor = allocateBuffer(sensorData);
        vertexSensor.put(sensorData);
        vertexSensor.position(sensorDataIndex);
        vertexData.position(0);
    }

    @Override public void draw(ShaderProgram shaderProgram) {
        GraphShaderProgram graphShaderProgram = (GraphShaderProgram) shaderProgram;
        GLES20.glLineWidth(5f);
        GLES20.glUniform4f(graphShaderProgram.getColorLocation(), graphInfo.getRed(), graphInfo.getGreen(), graphInfo.getBlue(), 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, SENSOR_HISTORY_LENGTH);
    }

    @Override public void bindData(ShaderProgram shaderProgram, float[] projectionMatrix, float[] modelViewMatrix) {
        GraphShaderProgram graphShaderProgram = (GraphShaderProgram) shaderProgram;
        // Calculate the projection and view transformation
        Matrix.multiplyMM(modelMatrix, 0, projectionMatrix, 0, modelViewMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, graphInfo.getyPositionToDraw(), 0f);

        graphShaderProgram.setUniforms(modelMatrix);

        GLES20.glEnableVertexAttribArray(graphShaderProgram.getPositionAttributeLocation());
        GLES20.glVertexAttribPointer(graphShaderProgram.getPositionAttributeLocation(), 1, GLES20.GL_FLOAT, false, 0, vertexData);

        GLES20.glEnableVertexAttribArray(graphShaderProgram.getSensorValue());
        GLES20.glVertexAttribPointer(graphShaderProgram.getSensorValue(), 1, GLES20.GL_FLOAT, false, 0, vertexSensor);
    }
}
