package com.dai.render;

public class Material {
	
	// 纹理
	private Texture texture;
	
	// 颜色
	private float[] color = new float[4];

	// 光照相关
	private boolean useLight;
	private float intensity;
	
	public Material() {
		color[0] = color[1] = color[2] = color[3] = 1.0f;
		useLight = false;
	}

	public Material(float r, float g, float b, float a) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
		useLight = false;
	}

	public Material(Texture texture) {
		this.texture = texture;
		useLight = false;
	}

	public float[] getColor() {
		return color;
	}

	public void bind() {
		if (null != texture) {
			texture.bind();
		}
	}
	
	public boolean isUseLight(){
		return useLight;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
	
}
