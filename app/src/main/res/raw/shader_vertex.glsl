attribute float vPosition;
attribute float vSensorValue;
uniform mat4 u_Matrix;

void main() {
    gl_Position = u_Matrix * vec4(vPosition, vSensorValue, 0, 1);
}
