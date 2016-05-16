#version 120

attribute vec3 position;

// mvp
uniform mat4 u_mvpMatrix;

void main(){
    gl_Position = u_mvpMatrix * vec4(position,1.0);
}