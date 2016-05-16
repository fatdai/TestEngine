package com.dai.render;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.HashMap;

import com.dai.base.Matrix4f;
import com.dai.base.Vector3f;
import com.dai.utils.Util;

public class Shader {

	protected int program;

	protected HashMap<String, Integer> uniforms;

	public static final int POSITION = 0;
	public static final int TEXCOORD = 1;
	public static final int NORMAL = 2;
	
	// shader name
	private String shaderName;
	
	public Shader(String shaderName) {

		this.shaderName = shaderName;
		uniforms = new HashMap<String, Integer>();

		program = glCreateProgram();
		if (program == 0) {
			System.err.println("glCreateProgram failed!");
			System.exit(1);
		}
	}
	
	public String getShaderName(){
		return shaderName;
	}

	public void addVertexShader(String text) {
		addProgram(text, GL_VERTEX_SHADER);
	}

	public void addGeometryShader(String text) {
		addProgram(text, GL_GEOMETRY_SHADER);
	}

	public void addFragmentShader(String text) {
		addProgram(text, GL_FRAGMENT_SHADER);
	}

	private void addProgram(String text, int type) {
		int shader = glCreateShader(type);
		if (shader == 0) {
			System.err.println("create shader type :" + type + " error!");
			System.exit(1);
		}

		glShaderSource(shader, text);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		glAttachShader(program, shader);
	}

	public void compileShader() {

		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}

		glValidateProgram(program);

		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}

	public void addUniform(String uniform) {
		int uniformLocation = glGetUniformLocation(program, uniform);
		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println("Error:Could not get uniform :" + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}

		uniforms.put(uniform, uniformLocation);
	}

	public void setUniformi(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUniformf(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, float[] color){
		glUniform4f(uniforms.get(uniformName), color[0], color[1], color[2], color[3]);
	}

	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4(uniforms.get(uniformName), false, Util.createFlippedBuffer(value));
	}

	public void bind() {
		glUseProgram(program);
	}

	public void bindAttribute(String name,int id) {
		glBindAttribLocation(program, id, name);
	}

	public void updateUniforms(Transform transform, Material material) {
		
		material.bind();
		Camera mainCamera = RenderEngine.getInstance().getMainCamera();
		Matrix4f mvp = RenderEngine.getInstance().getMainCamera().getPVMatrix().mul(transform.getFinalMatrix());
		
		// 传递 uniform 给 gpu
		// 1.怎么知道绑定了哪些 uniform?
		// 2.怎么知道要传递哪些数据?
		// 3.自定义的shader怎么处理?
		
		// 如果是自定义的 render,则直接通过 shader传递信息
		// 所以这里只需要处理默认的shader
		if (shaderName.equals(ShaderManager.TEST_SHADER_POS_TEXTURE)) {
			setUniform("transform",mvp);
		}else if (shaderName.equals(ShaderManager.TEST_SHADER_POS_UCOLOR)) {
			setUniform("u_mvpMatrix",mvp);
			setUniform("u_color",material.getColor());
		}else if (shaderName.equals("light")) {
			
			// 先假设全部受到光照
			setUniform("u_pMatrix",mainCamera.getProjMatrix());
			setUniform("u_vMatrix",mainCamera.getViewMatrix());
			setUniform("u_mMatrix",transform.getFinalMatrix());
			
			// 环境光颜色
			setUniform("u_ambient_color",new Vector3f(1, 1, 1));
			setUniformf("u_ambient_strength",material.getIntensity());
			setUniform("u_color",material.getColor());
		}
		else {
			System.out.println("无效的 shader.....");
			new Exception("无效的 shader.....").printStackTrace();
		}
	}

}
