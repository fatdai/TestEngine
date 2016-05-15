package com.dai.entity;

import com.dai.render.Shader;
import com.dai.render.Transform;

public abstract class GameComponent {

	protected GameObject _parent;

	public void setParent(GameObject object) {
		_parent = object;
	}

	public Transform getTransform() {
		return _parent.getTransform();
	}

	public GameObject getParent() {
		return _parent;
	}

	public void update() {
	};

	public void render(Shader shader) {
	};
}
