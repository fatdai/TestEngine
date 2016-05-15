#version 120

attribute vec3 position;
attribute vec2 texCoord;

uniform mat4 transform;

varying vec4 v_color;
varying vec2 v_texCoord;

void main(){
	v_color = vec4(clamp(position,0.0,1.0),1.0 );
	//v_color = vec4(1.0,0.0,0.0,1.0);
    gl_Position = transform * vec4(position,1.0);
    v_texCoord = texCoord;
}



