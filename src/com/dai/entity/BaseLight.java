package com.dai.entity;


// 灯
public class BaseLight extends GameComponent{
	
	// 灯的类型
	public static final int AMBIENT_LIGHT = 1;
	public static final int DIRECTION_LIGHT = 2;
	public static final int POINT_LIGHT = 3;
	public static final int SPOT_LIGHT = 4;
	
	protected int lightType;
	protected boolean enable;
	
	// 光强
	protected float intensity;
	
	// light flag,只有和物体的lightMask进行与操作不为0时,灯光才对物体有效
	protected int lightFlag;
	
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
