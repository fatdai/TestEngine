#version 120

// 环境光
attribute vec3 position;

// 各个矩阵
uniform mat4 u_pMatrix;
uniform mat4 u_vMatrix;
uniform mat4 u_mMatrix;

// 环境光的颜色和强度
uniform vec3 u_ambient_color;

// 范围应该[0,1]
uniform float u_ambient_strength;

varying vec3 v_ambientColor;

void main(){
    gl_Position = u_pMatrix * u_vMatrix * u_mMatrix * vec4(position,1.0);
    v_ambientColor = u_ambient_color * u_ambient_strength;
}