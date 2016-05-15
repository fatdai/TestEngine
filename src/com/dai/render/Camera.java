package com.dai.render;

import com.dai.base.Input;
import com.dai.base.Matrix4f;
import com.dai.base.Time;
import com.dai.base.Vector3f;

public class Camera {

	public static final Vector3f yAxis = new Vector3f(0, 1, 0);

	// pos 表示相机在 世界坐标系里面的位置
	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;

	private float fov;
	private float ratio;
	private float near;
	private float far;

	public Camera(float fov, float ratio, float near, float far) {
		this.fov = fov;
		this.ratio = ratio;
		this.near = near;
		this.far = far;
		
		pos = new Vector3f(0, 0, 0);
		forward = new Vector3f(0, 0, -1);
		up = new Vector3f(0, 1, 0);
	}
	
	public void setCamera(Vector3f pos, Vector3f forward, Vector3f up){
		this.pos = pos;
		this.forward = forward;
		this.up = up;

		this.forward.normailze();
		this.up.normailze();
	}

	// 
	public Matrix4f getViewMatrix(){
		
		// 计算 zaxis
		Vector3f zaxis = new Vector3f(-forward.getX(), -forward.getY(), -forward.getZ());

		// 计算 xaxis
		Vector3f xaxis = up.cross(zaxis);
		xaxis.normailze();

		// 计算 yaxis
		Vector3f yaxis = zaxis.cross(xaxis);
		zaxis.normailze();

		// M = T * R
		// 我们需要求 M^-1 = (T * R)^-1 = R^-1 * T^-1
		Matrix4f rotation = new Matrix4f();

		// 这里我们直接转置
		float[][] m = new float[][] { { xaxis.getX(), xaxis.getY(), xaxis.getZ(), 0 },
				{ yaxis.getX(), yaxis.getY(), yaxis.getZ(), 0 }, { zaxis.getX(), zaxis.getY(), zaxis.getZ(), 0 }, { 0, 0, 0, 1 }, };
		rotation.setM(m);

		Matrix4f trans = new Matrix4f();
		trans.initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

		// 最终的结果为
		return rotation.mul(trans);
	}
	
	
	private Matrix4f getProjectionMatrix(){
		return new Matrix4f().initPerspective(fov, ratio, near, far);
	}
	
	public Matrix4f getPVMatrix(){
		return getProjectionMatrix().mul(getViewMatrix());
	}
	
//	public Camera() {
//		this(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
//	}
//
//	public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
//		super();
//		this.pos = pos;
//		this.forward = forward;
//		this.up = up;
//
//		this.forward.normailze();
//		this.up.normailze();
//	}

	public void input() {
		float moveAmt = (float) (10 * Time.getDelta());
		float rotAmt = (float) (100 * Time.getDelta());
		if (Input.getKey(Input.KEY_W)) {
			move(getForward(), moveAmt);
		}
		if (Input.getKey(Input.KEY_S)) {
			move(getForward(), -moveAmt);
		}
		if (Input.getKey(Input.KEY_A)) {
			move(getRight(), moveAmt);
		}
		if (Input.getKey(Input.KEY_D)) {
			move(getLeft(), moveAmt);
		}

		// rotate
		if (Input.getKey(Input.KEY_UP)) {
			rotateX(rotAmt);
		}
		if (Input.getKey(Input.KEY_DOWN)) {
			rotateX(-rotAmt);
		}
		if (Input.getKey(Input.KEY_LEFT)) {
			rotateY(-rotAmt);
		}
		if (Input.getKey(Input.KEY_RIGHT)) {
			rotateY(rotAmt);
		}

	}

	public void move(Vector3f dir, float amt) {
		pos = pos.add(dir.mul(amt));
	}

	// --------------------------
	// 相机的 rotateX rotateY 其实都是对 forward 进行旋转.
	// 围绕y轴旋转
	public void rotateY(float angle) {

		// 先计算出相机的x轴,为了后面重新计算 up
		Vector3f Haxis = yAxis.cross(forward);
		Haxis.normailze();

		// 绕y轴旋转angle
		forward.rotate(angle, yAxis);
		forward.normailze();

		// 计算新的up
		up = forward.cross(Haxis);
		up.normailze();
	}

	// 围绕x轴旋转
	public void rotateX(float angle) {
		// 先计算出相机的x轴
		Vector3f Haxis = yAxis.cross(forward);
		Haxis.normailze();

		// 然后计算绕x轴旋转angle角度后的forward
		forward.rotate(angle, Haxis);
		forward.normailze();

		// 计算出相机新的 up
		up = forward.cross(Haxis);
		up.normailze();
	}

	// 从相机观察,如果让相机往-x移动,相当于被观察的对象往x移动

	// 相机的-x轴
	public Vector3f getLeft() {
		Vector3f left = yAxis.cross(forward);
		left.normailze();
		return left;
	}

	// 相机的 x 轴
	public Vector3f getRight() {
		Vector3f right = forward.cross(yAxis);
		right.normailze();
		return right;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getForward() {
		return forward;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}

}
