package com.dai.base;

import com.dai.entity.BaseGame;
import com.dai.render.RenderEngine;

public class CoreEngine {

	public static final double FRAME_CAP = 60.0;

	private boolean isRunning;

	private BaseGame game;
	
	private RenderEngine _renderEngine;
	
	public CoreEngine(BaseGame game) {
		
		isRunning = false;
		this.game = game;
	}

	public void start() {
		if (isRunning) {
			return;
		}
		run();
	}

	public void stop() {
		if (!isRunning) {
			return;
		}
		isRunning = false;
	}

	private void run() {

		isRunning = true;

		int frames = 0;
		long frameCounter = 0;

		final double frameTime = 1.0 / FRAME_CAP;
		
		game.init();
		
		long lastTime = Time.getTime();
		double unprocessedTime = 0;

		while (isRunning) {

			boolean render = false;

			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;

			while (unprocessedTime > frameTime) {

				if (Window.isCloseRequested()) {
					System.out.println("It will close!");
					stop();
				}

				render = true;
				unprocessedTime -= frameTime;

				Time.setDelta(frameTime);
				Input.update();

				game.input();
				game.update();
				_renderEngine.input();

				if (frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}

			if (render) {
				render();
				++frames;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		cleanUp();
	}

	private void render() {
		_renderEngine.render(game.getRootObject());
		Window.render();
	}

	private void cleanUp() {
		Window.dispose();
	}

	public void createWindow(int width, int height, String title) {
		Window.createWindow(width, height,title);
		_renderEngine = new RenderEngine();
	}

}
