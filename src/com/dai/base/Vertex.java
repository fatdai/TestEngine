package com.dai.base;

public class Vertex {

	// 顶点分配 buffer 时需要使用
	public static final int VERTEX_TEXCOORD_NORMAL_SIZE = 8;
	public static final int VERTEX_TEXCOORD_SIZE = 5;
	public static final int VERTEX_NORMAL_SIZE = 6;
	public static final int VERTEX_SIZE = 3;
	
	// 注意:不能更改下面声明的顺序
	private Vector3f pos;
	private Vector2f texCoord;
	private Vector3f normal;

	public Vertex(Vector3f pos) {
		this.pos = pos;
	}

	public Vertex(Vector3f pos, Vector2f texCoord) {
		this.pos = pos;
		this.texCoord = texCoord;
	}

	public Vertex(Vector3f pos,Vector2f texCoord,Vector3f normal){
		this.pos = pos;
		this.texCoord = texCoord;
		this.normal = normal;
	}
	
	public Vertex(Vector3f pos,Vector3f normal){
		this.pos = pos;
		this.normal = normal;
	}
	
	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector2f getTexCoord() {
		return texCoord;
	}

	public void setTexCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}

	public Vector3f getNormal() {
		return normal;
	}

}
