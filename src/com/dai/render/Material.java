package com.dai.render;

public class Material {

	private Texture texture;
	private float[] color = new float[4];

	public Material() {
		color[0] = color[1] = color[2] = color[3] = 1.0f;
	}

	public Material(float r, float g, float b, float a) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
	}

	public Material(Texture texture) {
		this.texture = texture;
	}

	public float[] getColor() {
		return color;
	}

	public void bind() {
		if (null != texture) {
			texture.bind();
		}
	}

}
