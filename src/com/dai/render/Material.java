package com.dai.render;

public class Material {

	private Texture texture;

	public Material() {

	}

	public Material(Texture texture) {
		this.texture = texture;
	}

	public void bind() {
		if (null != texture) {
			texture.bind();
		}
	}

}
