package com.dai.test;

import com.dai.base.CoreEngine;
import com.dai.base.Time;
import com.dai.entity.BaseGame;
import com.dai.entity.GameObject;
import com.dai.render.Material;
import com.dai.render.Mesh;
import com.dai.render.MeshRender;
import com.dai.render.Shader;
import com.dai.render.ShaderManager;
import com.dai.utils.ResourceLoader;

public class TestSunEarthMoon extends BaseGame {

	GameObject sun;
	GameObject earth;
	GameObject moon;

	float temp = 0.0f;

	float sunAngle = 0;
	float earthAngle = 0;
	float moonAngle = 0;

	public void update() {

		temp += Time.getDelta();

		sunAngle += 1;
		earthAngle += 2;
		moonAngle += 5;

		if (sunAngle > 360) {
			sunAngle -= 360;
		}

		if (earthAngle > 360) {
			earthAngle -= 360;
		}

		if (moonAngle > 360) {
			moonAngle -= 360;
		}

		sun.getTransform().setRotation(0, sunAngle, 0);
		earth.getTransform().setRotation(0, earthAngle, 0);
		earth.getTransform().setRotation(0, moonAngle, 0);
	}

	@Override
	public void init() {
		
		Mesh mesh = ResourceLoader.loadMesh("ball_1.obj");
		Material material = new Material(0,0.5f,0,1);
		Shader shader = ShaderManager.getShader(ShaderManager.TEST_SHADER_POS_UCOLOR);
		
		// sun
		sun = new GameObject();
		sun.addComponent(new MeshRender(mesh, material,shader));
		getRootObject().addChild(sun);

		// earth
		earth = new GameObject();
		earth.addComponent(new MeshRender(mesh, material,shader));
		sun.addChild(earth);

		// moon
		moon = new GameObject();
		moon.addComponent(new MeshRender(mesh, material,shader));
		earth.addChild(moon);

		// 设置初始位置和大小
		sun.getTransform().setPosition(0, 0, -10);

		earth.getTransform().setPosition(-2, 0, -5);
		earth.getTransform().setScale(0.8f, 0.8f, 0.8f);

		moon.getTransform().setPosition(-2, 0, -2);
		moon.getTransform().setScale(0.5f, 0.5f, 0.5f);
	}

	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestSunEarthMoon());
		engine.createWindow(480, 320, "TestSunEarthMoon");
		engine.start();
	}
}
