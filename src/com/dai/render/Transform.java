package com.dai.render;

import com.dai.base.Matrix4f;
import com.dai.base.Vector3f;

// 每一个 gameobject 应该都一个 transform

public class Transform {

	// private static float zNear;
	// private static float zFar;
	// private static float width;
	// private static float height;
	// private static float fov;

	// 表示 位置旋转缩放
	private Vector3f _pos;
	private Vector3f _rot;
	private Vector3f _scale;

	// 存储父变换
	private Transform _parentTransform;

	// 存储自己的 模型矩阵
	private Matrix4f _selfModelMatrix;
	private Matrix4f _parentMatrix;
	
	// 为了减少计算,存储最终的矩阵
	private Matrix4f _finalMatrix;
	
	private boolean _changed;

	public Transform() {
		_pos = new Vector3f(0, 0, 0);
		_rot = new Vector3f(0, 0, 0);
		_scale = new Vector3f(1, 1, 1);

		_selfModelMatrix = new Matrix4f().initIdentity();
		_parentMatrix = new Matrix4f().initIdentity();
		_finalMatrix =  new Matrix4f().initIdentity();
		_changed = false;
	}

	public void setParentTransform(Transform parentTransform) {
		this._parentTransform = parentTransform;
	}

	// 计算当前的模型矩阵
	public Matrix4f updateSelfModelView() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(_pos.getX(), _pos.getY(), _pos.getZ());
		Matrix4f rotationMatrix = new Matrix4f().initRotation(_rot.getX(), _rot.getY(), _rot.getZ());
		Matrix4f scaleMatrix = new Matrix4f().initScale(_scale.getX(), _scale.getY(), _scale.getZ());
		_selfModelMatrix = translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
		return _selfModelMatrix;
	}

	// 获取最终的模型矩阵
	public Matrix4f getFinalMatrix() {
		return _finalMatrix;
	}

	public boolean isChanged() {
		return _changed;
	}

	// 主要就是重新计算矩阵
	// 一定要在render之前调用
	public void visit() {
		
		// 自己是否改变了?
		if (_changed) {
			updateSelfModelView();
			_changed = false;
		}
		
		// 计算
		if (_parentTransform != null) {
			_parentMatrix = _parentTransform.getFinalMatrix();
		}
		
		// 更新最终的矩阵
		_finalMatrix = _parentMatrix.mul(_selfModelMatrix);
	}

	public void setPosition(float x, float y, float z) {
		_pos.setX(x);
		_pos.setY(y);
		_pos.setZ(z);
		_changed = true;
	}

	public void setRotation(float x, float y, float z) {
		_rot.setX(x);
		_rot.setY(y);
		_rot.setZ(z);
		_changed = true;
	}

	public void setScale(float x, float y, float z) {
		_scale.setX(x);
		_scale.setY(y);
		_scale.setZ(z);
		_changed = true;
	}

//	public Matrix4f getTransformation() {
//		Matrix4f translationMatrix = new Matrix4f().initTranslation(_pos.getX(), _pos.getY(), _pos.getZ());
//		Matrix4f rotationMatrix = new Matrix4f().initRotation(_rot.getX(), _rot.getY(), _rot.getZ());
//		Matrix4f scaleMatrix = new Matrix4f().initScale(_scale.getX(), _scale.getY(), _scale.getZ());
//		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
//	}

	// public static void setProjection(float fov, float width, float height,
	// float near, float far) {
	// Transform.fov = fov;
	// Transform.width = width;
	// Transform.height = height;
	// Transform.zNear = near;
	// Transform.zFar = far;
	// }

	// public Matrix4f getTransformation() {
	// Matrix4f translationMatrix = new
	// Matrix4f().initTranslation(translation.getX(), translation.getY(),
	// translation.getZ());
	// Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(),
	// rotation.getY(), rotation.getZ());
	// Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(),
	// scale.getY(), scale.getZ());
	//
	// // 下面注释的写法也可以.
	// return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	// // return (translationMatrix.mul(rotationMatrix)).mul(scaleMatrix);
	// }

	// public Matrix4f getProjectedTransformation() {
	// Matrix4f transformationMatrix = getTransformation();
	// Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width,
	// height, zNear, zFar);
	//
	// Matrix4f cameraRotation = new Matrix4f().initCamera(camera.getForward(),
	// camera.getUp());
	// Matrix4f cameraTranslation = new
	// Matrix4f().initTranslation(-camera.getPos().getX(),
	// -camera.getPos().getY(), -camera
	// .getPos().getZ());
	//
	// Matrix4f viewMatrix = cameraRotation.mul(cameraTranslation);
	// return projectionMatrix.mul(viewMatrix).mul(transformationMatrix);
	//
	// //return
	// projectionMatrix.mul(cameraRotation.mul(cameraTranslation.mul(transformationMatrix)));
	// }

	// public Vector3f getTranslation() {
	// return translation;
	// }
	//
	// public void setTranslation(Vector3f translation) {
	// this.translation = translation;
	// }
	//
	// public void setTranslation(float x, float y, float z) {
	// this.translation = new Vector3f(x, y, z);
	// }
	//
	// // rotation
	// public Vector3f getRotation() {
	// return rotation;
	// }
	//
	// public void setRotation(Vector3f rotation) {
	// this.rotation = rotation;
	// }
	//
	// public void setRotation(float x, float y, float z) {
	// this.rotation = new Vector3f(x, y, z);
	// }

	// scale
	// public Vector3f getScale() {
	// return scale;
	// }
	//
	// public void setScale(Vector3f scale) {
	// this.scale = scale;
	// }
	//
	// public void setScale(float x, float y, float z) {
	// this.scale = new Vector3f(x, y, z);
	// }
	//
	// public static Camera getCamera() {
	// return camera;
	// }
	//
	// public static void setCamera(Camera camera) {
	// Transform.camera = camera;
	// }

}
