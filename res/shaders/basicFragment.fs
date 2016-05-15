#version 120

varying vec4 v_color;
varying vec2 v_texCoord;

uniform sampler2D sampler;

void main(){
    gl_FragColor =  texture2D(sampler,v_texCoord);
}

