package com.dai.base;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {
	public static void createWindow(int width, int height, String title) {
		
		try {
			
//			ContextAttribs contextAttribs = new ContextAttribs(2, 1);
//			contextAttribs.withForwardCompatible(true);
//			contextAttribs.withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void render() {
		Display.update();
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public static int getWidth() {
		return Display.getDisplayMode().getWidth();
	}

	public static int getHeight() {
		return Display.getDisplayMode().getHeight();
	}

	public static void dispose() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
}
