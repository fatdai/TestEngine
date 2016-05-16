#version 120

uniform vec4 u_color;
varying vec3 v_ambientColor;
void main(){
    gl_FragColor = u_color * vec4(v_ambientColor,1.0);
}

