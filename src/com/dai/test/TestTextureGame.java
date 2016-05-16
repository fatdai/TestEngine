package com.dai.test;

import com.dai.base.CoreEngine;
import com.dai.base.Vector2f;
import com.dai.base.Vector3f;
import com.dai.base.Vertex;
import com.dai.entity.BaseGame;
import com.dai.entity.GameObject;
import com.dai.render.Material;
import com.dai.render.Mesh;
import com.dai.render.MeshRender;
import com.dai.render.Shader;
import com.dai.render.ShaderManager;
import com.dai.render.Texture;
import com.dai.utils.ResourceLoader;

// 测试下纹理映射
public class TestTextureGame extends BaseGame {
	
	@Override
	public void init() {

		Texture texture = ResourceLoader.loadTexture("test.png");
		
		Mesh mesh = new Mesh();
		Vertex[] data = new Vertex[] { 
				new Vertex(new Vector3f(-1, -1 , 0),new Vector2f(0, 1)),
				new Vertex(new Vector3f(-1, 1, 0),new Vector2f(0, 0)), 
				new Vertex(new Vector3f(1, 1, 0),new Vector2f(1, 0)),
				new Vertex(new Vector3f(1, -1,0), new Vector2f(1, 1)), };
		int[] indices = new int[] { 0, 2, 1, 0, 3, 2 };
		mesh.addVertices(data, indices,true,false);
		
		
		Shader shader = ShaderManager.getShader(ShaderManager.TEST_SHADER_POS_TEXTURE);
		GameObject object = new GameObject();
		Material material = new Material(texture);
		object.addComponent(new MeshRender(mesh, material,shader));
		getRootObject().addChild(object);
		
		object.getTransform().setPosition(0, 0, -5);
	}

	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestTextureGame());
		engine.createWindow(480, 320, "TestTextureGame");
		engine.start();
	}
}
