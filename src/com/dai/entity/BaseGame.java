package com.dai.entity;

public abstract class BaseGame {

	protected GameObject root;

	public abstract void init();

	public void input() {
		getRootObject().input();
	};

	public void update() {
		getRootObject().update();
	};

	public void render() {
		getRootObject().render();
	};

	public GameObject getRootObject() {
		if (null == root) {
			root = new GameObject();
		}
		return root;
	}

}
