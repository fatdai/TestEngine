package gltester;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.dai.base.Matrix4f;
import com.dai.base.Vector3f;
import com.dai.render.Camera;
import com.dai.render.Shader;
import com.dai.utils.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

// 进行 纯opengl的功能小demo
public class TestLight1 extends Tester {

	public TestLight1(int w, int h, String title) {
		super(w, h, title);
	}

	Shader shader;
	FloatBuffer vertexBuffer;
	Camera camera;
	Matrix4f pvMatrix;
	Matrix4f modelMatrix;
	
	public static void main(String[] args) {
		TestLight1 test = new TestLight1(480,320,"Test");
		test.start();
	}
	
	@Override
	protected void initGL() {
		glClearColor(0, 0, 0, 1);
		glEnable(GL_DEPTH_TEST);
		glViewport(0, 0, width, height);
	}

	@Override
	protected void init() {
		camera = new Camera(70, ratio, 0.1f, 1000.0f);
		
		shader = new Shader("test");
		shader.addVertexShader(ResourceLoader.loadShader("test_triangle.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("test_triangle.fs"));
		shader.compileShader();
		shader.bindAttribute("position", Shader.POSITION);
		shader.addUniform("u_mvpMatrix");
		shader.addUniform("u_color");

		Vector3f[] v = new Vector3f[]{
				new Vector3f(-1, -1, 0),
				new Vector3f(-1, 1, 0),
				new Vector3f(1, 1, 0)
		};

		vertexBuffer = BufferUtils.createFloatBuffer(v.length * 3);
		for (int i = 0; i < v.length; i++) {
			vertexBuffer.put(v[i].getX());
			vertexBuffer.put(v[i].getY());
			vertexBuffer.put(v[i].getZ());
		}
		vertexBuffer.flip();
	
		pvMatrix = camera.getPVMatrix();
		modelMatrix = new Matrix4f().initTranslation(0, 0, -3); 
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		shader.bind();
			
		float[] color = new float[]{1,0,0,1};
		glEnableVertexAttribArray(Shader.POSITION);
		glVertexAttribPointer(Shader.POSITION, 3, false, 0, vertexBuffer);
		shader.setUniform("u_mvpMatrix", pvMatrix.mul(modelMatrix));
		shader.setUniform("u_color", color);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glDisableVertexAttribArray(Shader.POSITION);
	}
}
