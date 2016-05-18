package gltester;

import com.dai.base.Input;
import com.dai.base.Window;

public abstract class Tester {

	protected boolean isRunning = false;

	protected int width;
	protected int height;
	protected String title;
	protected float ratio;

	public Tester(int w, int h, String title) {
		this.width = w;
		this.height = h;
		this.title = title;
		ratio = (float)width/(float)height;
	}

	public void start() {

		Window.createWindow(width, height, title);
		initGL();
		
		init();
		isRunning = true;
		// 主循环
		while (isRunning) {

			if (Window.isCloseRequested()) {
				isRunning = false;
			}

			input();
			update();
			render();
			swapBuffers();
			Input.update();

			// 直接简单处理
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		cleanUp();

	}

	protected abstract void initGL();

	private void swapBuffers() {
		Window.render();
	}

	protected abstract void init();

	protected void cleanUp() {
		Window.dispose();
	}

	protected abstract void render();

	protected void update() {

	}

	protected void input() {
	};
}
