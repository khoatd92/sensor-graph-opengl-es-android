package com.example.tdkhoa.sensorgraph;

public interface IView {
    void draw(ShaderProgram shaderProgram);
    void bindData(ShaderProgram shaderProgram, float[] projectionMatrix, float[] modelViewMatrix);
}
