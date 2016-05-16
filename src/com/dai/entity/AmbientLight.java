package com.dai.entity;

import com.dai.base.Vector3f;

// 环境光
public class AmbientLight extends BaseLight {

	// 光的颜色
	private Vector3f lightColor;

	public AmbientLight(Vector3f lightColor, float intensity) {
		super(intensity);
		this.lightColor = lightColor;
		setLightType(AMBIENT_LIGHT);
		setEnable(true);
	}

	public Vector3f getLightColor() {
		return lightColor;
	}

	public void setLightColor(Vector3f lightColor) {
		this.lightColor = lightColor;
	}

	
}
