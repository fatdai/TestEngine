package com.dai.base;

public class Vector3f {
	
	// 三个轴
	public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
	public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);
	
	private float x;
	private float y;
	private float z;

	public Vector3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3f other){
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float dot(Vector3f r) {
		return x * r.x + y * r.y + z * r.z;
	}

	public Vector3f cross(Vector3f r) {
		float x_ = y * r.z - z * r.y;
		float y_ = z * r.x - x * r.z;
		float z_ = x * r.y - y * r.x;
		return new Vector3f(x_, y_, z_);
	}

	public Vector3f normailze() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}

	// 主要利用四元数来计算
	// q' = pqp^-1
	public Vector3f rotate(float angle,Vector3f axis) {
		
		float sinHalfAngle = (float) Math.sin(Math.toRadians(angle/2));
		float cosHalfAngle = (float) Math.cos(Math.toRadians(angle/2));
		
		// p = [cos(a/2),sin(a/2)];
		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion w = rotation.mul(this).mul(conjugate);
		x = w.getX();
		y = w.getY();
		z = w.getZ();
	
		return this;
	}

	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.x, y + r.y, z + r.z);
	}

	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}

	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.x, y - r.y, z - r.z);
	}

	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.x, y * r.y, z * r.z);
	}

	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.x, y / r.y, z / r.z);
	}

	@Override
	public String toString() {
		return "X:" + x + ", Y:" + y + ",Z:" + z;
	}

}
