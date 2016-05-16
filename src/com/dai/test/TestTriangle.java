package com.dai.test;

import com.dai.base.CoreEngine;
import com.dai.base.Vector3f;
import com.dai.base.Vertex;
import com.dai.entity.BaseGame;
import com.dai.entity.GameObject;
import com.dai.render.Camera;
import com.dai.render.Material;
import com.dai.render.Mesh;
import com.dai.render.MeshRender;
import com.dai.render.RenderEngine;
import com.dai.render.ShaderManager;


// 简单绘制一个三角形
public class TestTriangle extends BaseGame{

	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestTriangle());
		engine.createWindow(480, 320, "TestTriangle");
		engine.start();
	}

	@Override
	public void init() {
		Mesh mesh = new Mesh();
		Vertex[] data = new Vertex[]{
			new Vertex(new Vector3f(-1, -1, 0)),
			new Vertex(new Vector3f(-1, 1, 0)),
			new Vertex(new Vector3f(1, 1, 0)),
		};
		int[] indices = new int[]{0,1,2};
		mesh.addVertices(data, indices);
		
		// 设置一下 camera的位置
		Camera camera = RenderEngine.getInstance().getMainCamera();
		camera.setCamera(new Vector3f(0, 0, 5), new Vector3f(0, 0, -1), Vector3f.Y_AXIS);
		
		Material material = new Material(1, 1, 0, 1);
		MeshRender render = new MeshRender(mesh, material, ShaderManager.getShader(ShaderManager.TEST_SHADER_POS_UCOLOR));
		GameObject triangle = new GameObject();
		triangle.addComponent(render);
		
		getRootObject().addChild(triangle);
	}

}
