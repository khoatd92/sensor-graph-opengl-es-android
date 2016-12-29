package com.example.tdkhoa.sensorgraph.graph;

import com.example.tdkhoa.sensorgraph.glprogram.ShaderProgram;

public interface IViewRenderer {
    void draw(ShaderProgram shaderProgram);
    void bindData(ShaderProgram shaderProgram, float[] projectionMatrix, float[] modelViewMatrix);
}
