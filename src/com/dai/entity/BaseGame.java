package com.dai.entity;

import com.dai.render.Shader;

public abstract class BaseGame {

	protected GameObject root;

	public abstract void init();

	public void input() {
		getRootObject().input();
	};

	public void update() {
		getRootObject().update();
	};

	public void render(Shader shader) {
		getRootObject().render(shader);
	};

	public GameObject getRootObject() {
		if (null == root) {
			root = new GameObject();
		}
		return root;
	}

}
