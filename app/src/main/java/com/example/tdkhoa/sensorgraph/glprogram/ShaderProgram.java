package com.example.tdkhoa.sensorgraph.glprogram;

import android.content.Context;
import android.opengl.GLES20;

import com.example.tdkhoa.sensorgraph.utils.TextResourceReader;
import com.example.tdkhoa.sensorgraph.utils.ShaderUtils;


public abstract class ShaderProgram {
    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";

    // Attribute constants
    protected static final String V_POSITION = "vPosition";
    protected static final String V_SENSOR_VALUE = "vSensorValue";
    protected static final String U_COLOR = "u_Color";

    // Shader program
    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, vertexShaderResourceId);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, fragmentShaderResourceId);
        program = ShaderUtils.buildProgram(vertexShaderSource, fragmentShaderSource);
    }

    public void useProgram() {
        // Set the current OpenGL shader program to this program.
        GLES20.glUseProgram(program);
    }
}
