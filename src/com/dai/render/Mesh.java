package com.dai.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import com.dai.base.Vertex;
import com.dai.utils.Util;

public class Mesh {

	private int vbo;
	private int ibo;
	private int size;

	public Mesh() {
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
	}

	public void addVertices(Vertex[] vertices,int[] indices) {
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void draw() {
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glEnableVertexAttribArray(Shader.POSITION);
		glEnableVertexAttribArray(Shader.TEXCOORD);
		glVertexAttribPointer(Shader.POSITION, 3, GL_FLOAT, false,Vertex.SIZE * 4, 0);
		glVertexAttribPointer(Shader.TEXCOORD, 2, GL_FLOAT, false,Vertex.SIZE * 4, 3 * 4);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glDisableVertexAttribArray(Shader.POSITION);
		glDisableVertexAttribArray(Shader.TEXCOORD);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

}
