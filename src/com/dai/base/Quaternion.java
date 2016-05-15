package com.dai.base;

public class Quaternion {

	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion(float x, float y, float z, float w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion(Vector3f axis,float angle) {
		
		double rad = Math.toRadians(angle) / 2;
		float sinHalfAngle = (float)Math.sin(rad);
		float cosHalfAngle = (float)Math.cos(rad);

		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}
	
	public void set(Quaternion other){
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = other.w;
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public Quaternion normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		w /= length;
		return this;
	}

	// 共轭
	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}

	public Quaternion mul(Quaternion r) {
		float w_ = w * r.w - x * r.x - y * r.y - z * r.z;
		float x_ = x * r.w + w * r.x + y * r.z - z * r.y;
		float y_ = y * r.w + w * r.y + z * r.x - x * r.z;
		float z_ = z * r.w + w * r.z + x * r.y - y * r.x;
		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();
		return new Quaternion(x_, y_, z_, w_);
	}
	
	// 和 toMatrix 方法结果一样
	public Matrix4f toRotationMatrix()
	{
		Vector3f forward =  new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
		
		return new Matrix4f().initRotation(forward, up, right);
	}
	
	public Matrix4f toMatrix(){
		float x2 = x * x;
		float y2 = y * y;
		float z2 = z * z;
		float xy = x * y;
		float xz = x * z;
		float yz = y * z;
		float wx = w * x;
		float wy = w * y;
		float wz = w * z;

		// This calculation would be a lot more complicated for non-unit length quaternions
		// Note: The constructor of Matrix4 expects the Matrix in column-major format like expected by
		//   OpenGL
		float[][] m = new float[][]{
				{1.0f - 2.0f * (y2 + z2),   2.0f * (xy - wz),  		2.0f * (xz + wy), 0.0f},
				{2.0f * (xy + wz), 		   1.0f - 2.0f * (x2 + z2), 2.0f * (yz - wx), 0.0f},
				{2.0f * (xz - wy), 		   2.0f * (yz + wx),  		1.0f - 2.0f * (x2 + y2), 0.0f},
				{0.0f, 0.0f, 0.0f, 1.0f}
		};
		Matrix4f result  = new Matrix4f();
		result.setM(m);
		return result;
	}
}
