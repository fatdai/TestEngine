package com.dai.test;

import com.dai.base.CoreEngine;
import com.dai.base.Matrix4f;
import com.dai.base.Time;
import com.dai.base.Vector3f;
import com.dai.base.Vertex;
import com.dai.entity.BaseGame;
import com.dai.entity.GameComponent;
import com.dai.entity.GameObject;
import com.dai.render.Camera;
import com.dai.render.Material;
import com.dai.render.Mesh;
import com.dai.render.MeshRender;
import com.dai.render.RenderEngine;
import com.dai.render.Shader;
import com.dai.render.ShaderManager;
import com.dai.utils.ResourceLoader;

// 测试下自定义的shader 能否正常运行
public class TestCustomShader extends BaseGame {

	@Override
	public void init() {

		// 加载我自定义的 shader
		Shader shader = new Shader("my_test_custom_shader");
		shader.addVertexShader(ResourceLoader.loadShader("custome_shader_1.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("custome_shader_1.fs"));
		shader.compileShader();
		shader.bindAttribute("position", Shader.POSITION);
		shader.addUniform("u_pvMatrix");
		shader.addUniform("u_mMatrix");
		shader.addUniform("u_time");
		shader.addUniform("u_color");
		ShaderManager.getInstance().addCustomShader(shader);

		// 设置数据
		Material material = new Material(0.5f, 0.5f, 0, 1);
		Mesh mesh = new Mesh();
		Vertex[] data = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)), new Vertex(new Vector3f(-1, 1, 0)),
				new Vertex(new Vector3f(1, 1, 0)), };
		int[] indices = new int[] { 0, 1, 2 };
		mesh.addVertices(data, indices);

		// 设置一下 camera的位置
		Camera camera = RenderEngine.getInstance().getMainCamera();
		camera.setCamera(new Vector3f(0, 0, 5), new Vector3f(0, 0, -1), Vector3f.Y_AXIS);

		MeshRender render = new MyRender(mesh, material,shader);
		GameObject triangle = new GameObject();
		triangle.addComponent(render);
		
		getRootObject().addChild(triangle);
	}

	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestCustomShader());
		engine.createWindow(480, 320, "TestCustomShader");
		engine.start();
	}

	// 自定义一个 组建
	class MyRender extends MeshRender {

		private float time;

		public MyRender(Mesh mesh, Material material, Shader shader) {
			super(mesh, material, shader);
		}

		@Override
		public void update() {
			super.update();
			time += Time.getDelta();
		}

		@Override
		public void render() {
			shader.bind();

			material.bind();
			Camera camera = RenderEngine.getInstance().getMainCamera();
			Matrix4f pvMatrix = camera.getPVMatrix();
			Matrix4f mMatrix = getTransform().getFinalMatrix();

			shader.setUniform("u_color", material.getColor());
			shader.setUniform("u_pvMatrix", pvMatrix);
			shader.setUniform("u_mMatrix", mMatrix);
			shader.setUniformf("u_time", time * 50);

			mesh.draw();
		}
	}
}
