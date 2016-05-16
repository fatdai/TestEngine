package com.dai.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;

import com.dai.base.Window;
import com.dai.entity.GameObject;

public class RenderEngine {

	private Camera mainCamera;

	private static RenderEngine _renderEngine;

	public RenderEngine() {

		if (_renderEngine != null) {
			System.out.println("_renderEngine Already created!");
			System.exit(1);
		}
		mainCamera = new Camera(70.0f, (float) Window.getWidth() / (float) Window.getHeight(), 0.1f, 1000.0f);
		System.out.println(getOpenGLVersion());
		initGraphics();
		_renderEngine = this;
	}

	public void input() {
		mainCamera.input();
	}

	public static RenderEngine getInstance() {
		return _renderEngine;
	}

	public void render(GameObject object) {
		clearScreen();
		object.visit();
		object.render();
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	private void initGraphics() {

		//
		glEnable(GL_DEPTH_TEST);

		//TODO  cull face 好像有问题
//		glFrontFace(GL_CW);
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_BACK);

		// TODO Depth clamp for later

		// Why???
		glEnable(GL_FRAMEBUFFER_SRGB);
		glEnable(GL_TEXTURE_2D);
	}

	private String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}

	public void clearScreen() {
		// TODO : Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
