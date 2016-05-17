package com.dai.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.dai.base.Vector3f;
import com.dai.base.Vertex;
import com.dai.utils.Util;
import com.dai.utils.meshloading.IndexedModel;
import com.dai.utils.meshloading.OBJModel;


// Mesh 包含哪些数据?
// 1.是否包含 纹理坐标?
// 2.是否包含 法线坐标?
public class Mesh {

	private int vbo;
	private int ibo;
	private int size;

	private boolean hasTexCoord;
	private boolean hasNormal;
	
	// 存储数据的 stride
	private int stride;
		
	public Mesh() {
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
	}

	public void addVertices(Vertex[] vertices,int[] indices,boolean hasTexCoord,boolean hasNormal){
		size = indices.length;
		this.hasNormal = hasNormal;
		this.hasTexCoord = hasTexCoord;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		if (hasTexCoord && hasNormal) {
			stride = Vertex.VERTEX_TEXCOORD_NORMAL_SIZE * 4;
			glBufferData(GL_ARRAY_BUFFER, Util.genVertexAndTexCoordAndNormalFlippedBuffer(vertices), GL_STATIC_DRAW);
		}else {
			if (hasTexCoord) {
				stride = Vertex.VERTEX_TEXCOORD_SIZE * 4;
				// 顶点 和 纹理坐标
				glBufferData(GL_ARRAY_BUFFER, Util.genVertexAndTexCoordFlippedBuffer(vertices), GL_STATIC_DRAW);
			}else if (hasNormal) {
				// 顶点 和 法线
				stride = Vertex.VERTEX_NORMAL_SIZE * 4;
				glBufferData(GL_ARRAY_BUFFER, Util.genVertexAndNormalFlippedBuffer(vertices), GL_STATIC_DRAW);
			}else {
				// 只包含顶点
				stride = Vertex.VERTEX_SIZE * 4;
				glBufferData(GL_ARRAY_BUFFER, Util.genVertexFlippedBuffer(vertices), GL_STATIC_DRAW);
			}
		}

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	} 
	
	public void addVertices(Vertex[] vertices,int[] indices) {
		addVertices(vertices, indices, false, false);
	}

	private void enableVertexAttribArray(){
		glEnableVertexAttribArray(Shader.POSITION);
		if (hasTexCoord && hasNormal) {
			glEnableVertexAttribArray(Shader.TEXCOORD);
			glEnableVertexAttribArray(Shader.NORMAL);
		}else {
			if (hasTexCoord) {
				glEnableVertexAttribArray(Shader.TEXCOORD);
			}else if (hasNormal) {
				glEnableVertexAttribArray(Shader.NORMAL);
			}
		}
	}
	
	private void disableVertexAttribArray(){
		glDisableVertexAttribArray(Shader.POSITION);
		if (hasTexCoord && hasNormal) {
			glDisableVertexAttribArray(Shader.TEXCOORD);
			glDisableVertexAttribArray(Shader.NORMAL);
		}else {
			if (hasTexCoord) {
				glDisableVertexAttribArray(Shader.TEXCOORD);
			}else if (hasNormal) {
				glDisableVertexAttribArray(Shader.NORMAL);
			}
		}
	}
	
	private void transferData(){
		glVertexAttribPointer(Shader.POSITION, 3, GL_FLOAT, false,stride, 0);
		if (hasTexCoord && hasNormal) {
			glVertexAttribPointer(Shader.TEXCOORD, 2, GL_FLOAT, false,stride, 3 * 4);
			glVertexAttribPointer(Shader.NORMAL, 2, GL_FLOAT, false,stride, 5 * 4);
		}else {
			if (hasTexCoord) {
				glVertexAttribPointer(Shader.TEXCOORD, 2, GL_FLOAT, false,stride, 3 * 4);
			}else if (hasNormal) {
				glVertexAttribPointer(Shader.NORMAL, 2, GL_FLOAT, false,stride, 3 * 4);
			}
		}
	}
	
	public void draw() {
		// 绑定,启用
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		enableVertexAttribArray();
		
		// 传输数据
		transferData();
		
		// 绘制
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		
		// 解除绑定
		disableVertexAttribArray();
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	//----------------------------------------------------------------------------
	// 提供一些基础的图形，比如cube  球体 等
	public static Mesh createCube() {
//		Mesh mesh = new Mesh();
//		Vertex[] vertexs = new Vertex[]{
//				new Vertex(new Vector3f(-1, -1, 1)),
//				new Vertex(new Vector3f(1, -1, 1)),
//				new Vertex(new Vector3f(1, -1, -1)),
//				new Vertex(new Vector3f(-1, -1, -1)),
//				new Vertex(new Vector3f(-1, 1, 1)),
//				new Vertex(new Vector3f(1, 1, 1)),
//				new Vertex(new Vector3f(1, 1, -1)),
//				new Vertex(new Vector3f(-1,1,-1))
//		};
//		int[] indices = new int[]{
//				0,1,2,0,2,3,  // down
//				4,5,6,4,6,7, // up
//				3,0,4,3,4,7, // left
//				1,2,6,1,6,5, // right
//				0,1,5,0,5,4, // near
//				2,3,7,2,7,6, // far
//		};
//		mesh.addVertices(vertexs, indices);
//		return mesh;
		return Mesh.simpleObjLoader("../res/cube.obj");
	}
	
	public static Mesh createBall(){
		return Mesh.simpleObjLoader("../res/ball.obj");
	}

	private static Mesh simpleObjLoader(String interObjPath){
		Mesh mesh = new Mesh();
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		InputStream is = mesh.getClass().getResourceAsStream(interObjPath);
		if (null == is) {
			new Exception("interObjPath is not exist!").printStackTrace();
			return null;
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float
							.valueOf(tokens[3]))));
				} else if (tokens[0].equals("f")) {
					indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);

					if (tokens.length > 4) {
						indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
					}
				}
			}
			reader.close();

			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			mesh.addVertices(vertexData, Util.toIntArray(indexData));
			return mesh;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
