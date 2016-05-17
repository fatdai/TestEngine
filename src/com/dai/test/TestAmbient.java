package com.dai.test;

import com.dai.base.CoreEngine;
import com.dai.base.Input;
import com.dai.base.Vector3f;
import com.dai.entity.AmbientLight;
import com.dai.entity.BaseGame;
import com.dai.entity.GameObject;
import com.dai.render.Material;
import com.dai.render.Mesh;
import com.dai.render.MeshRender;
import com.dai.render.Shader;
import com.dai.render.ShaderManager;
import com.dai.utils.ResourceLoader;

// 测试下 环境光
public class TestAmbient extends BaseGame {

	Material material;

	@Override
	public void init() {

		// 设置一个环境光
		AmbientLight light = new AmbientLight(new Vector3f(1, 0, 0), 0.8f);

		// 使用环境光的光照
		Shader shader = new Shader("light");
		shader.addVertexShader(ResourceLoader.loadShader("test_ambient.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("test_ambient.fs"));
		shader.compileShader();
		shader.bindAttribute("position", Shader.POSITION);
		shader.addUniform("u_pMatrix");
		shader.addUniform("u_vMatrix");
		shader.addUniform("u_mMatrix");
		shader.addUniform("u_ambient_color");
		shader.addUniform("u_ambient_strength");
		shader.addUniform("u_color");
		ShaderManager.getInstance().addCustomShader(shader);

		// 设置一个球
		Mesh mesh = ResourceLoader.loadObjOnlyVertex("ball_1.obj");
		material = new Material(1,1,0,1);
		material.setIntensity(0.5f);
		MeshRender render = new MeshRender(mesh, material, shader);
		GameObject ball = new GameObject();
		ball.addComponent(render);
		ball.getTransform().setPosition(0, 0, -5);
		getRootObject().addChild(ball);

		// 将灯光也加入到场景中
		GameObject lamp = new GameObject();
		lamp.addComponent(light);
		getRootObject().addChild(lamp);
		
		Mesh testMesh = ResourceLoader.loadObjWithNormalOrTexCoord("monkey3.obj");
		GameObject testBall = new GameObject();
		testBall.addComponent(new MeshRender(testMesh, material, shader));
		testBall.getTransform().setPosition(-2, 0, -5);
		getRootObject().addChild(testBall);
	}

	@Override
	public void input() {
		super.input();
		if (Input.getKeyDown(Input.KEY_P)) {
			float intensity = material.getIntensity();
			float newIntensity = intensity + 0.1f;
			if (newIntensity > 1.0f) {
				newIntensity = 1.0f;
			}
			material.setIntensity(newIntensity);
		}

		if (Input.getKeyDown(Input.KEY_M)) {
			float intensity = material.getIntensity();
			float newIntensity = intensity - 0.1f;
			if (newIntensity < 0) {
				newIntensity = 0.0f;
			}
			material.setIntensity(newIntensity);
		}
	}

	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestAmbient());
		engine.createWindow(800, 600, "TestAmbient");
		engine.start();
	}

}
