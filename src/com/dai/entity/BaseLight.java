package com.dai.entity;


// 灯
public class BaseLight extends GameComponent{
	
	// 灯的类型
	public static final int AMBIENT_LIGHT = 1;
	public static final int DIRECTION_LIGHT = 2;
	public static final int POINT_LIGHT = 3;
	public static final int SPOT_LIGHT = 4;
	
	// 光的类型
	protected int lightType;
	protected boolean enable;
	
	// 光强
	protected float intensity;

	public BaseLight(float intensity) {
		this.intensity = intensity;
	}

	public int getLightType() {
		return lightType;
	}

	public void setLightType(int lightType) {
		this.lightType = lightType;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	} 
	
	
	
}
