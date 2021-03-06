package com.dai.entity;

import java.util.ArrayList;

import com.dai.render.Shader;
import com.dai.render.Transform;

public class GameObject {

	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private GameObject parent;

	private Transform transform;

	// 可能会受到光照的影响
	// 默认受所有的光照影响
	private int lightMask = 0xFFFFFFFF;
	
	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}

	public void addChild(GameObject child) {

		if (child.parent != null) {
			System.err.println("child has added to parent!");
			new Exception("child has added to parent!").printStackTrace();
			System.exit(1);
		}

		children.add(child);
		child.setParent(this);		
	}

	public void setParent(GameObject object) {
		parent = object;
		transform.setParentTransform(object.getTransform());
	}

	public void addComponent(GameComponent component) {
		
		if (component.getParent() != null) {
			System.err.println("component has added to parent!");
			new Exception("component has added to parent!").printStackTrace();
			System.exit(1);
		}
		
		components.add(component);
		component.setParent(this);
	}

	public void input() {

		for (GameObject child : children) {
			child.input();
		}
	}

	public void update() {
		for (GameComponent component : components) {
			component.update();
		}

		for (GameObject child : children) {
			child.update();
		}
	}

	public void render() {

		// 先渲染自己,再递归渲染子节点
		for (GameComponent component : components) {
			component.render();
		}

		for (GameObject child : children) {
			child.render();
		}
	}

	public Transform getTransform() {
		return transform;
	}

	public void visit() {

		// 先 visit 自己
		transform.visit();

		// 递归更新 transform
		for (GameObject child : children) {
			child.visit();
		}
	}

	public int getLightMask() {
		return lightMask;
	}
	public void setLightMask(int lightMask) {
		this.lightMask = lightMask;
	}
	
	
}
