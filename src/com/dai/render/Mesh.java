package com.dai.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import com.dai.base.Vertex;
import com.dai.utils.Util;


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

}
