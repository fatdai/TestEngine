#version 120

attribute vec3 position;

uniform mat4 u_pvMatrix;
uniform mat4 u_mMatrix;

uniform float u_time;

void main(){
    // 让顶点做 sin 动画
	 float tempY = sin(radians(u_time)) * 2.0 + position.y;
     vec4 newpos = vec4(position.x, tempY,position.z,1.0);
     gl_Position = u_pvMatrix * u_mMatrix * newpos;
}