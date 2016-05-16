package com.dai.render;

import java.util.HashMap;

import com.dai.utils.ResourceLoader;

public class ShaderManager {

	// 加载过的都存储起来,方便以后使用
	private HashMap<String, Shader> _cachedShader = new HashMap<String, Shader>();
	
	// 默认shader的一些名字,先全部定义为测试使用
	public static final String  TEST_SHADER_POS_TEXTURE = "TEST_SHADER_POS_TEXTURE";
	public static final String  TEST_SHADER_POS_UCOLOR = "TEST_SHADER_POS_UCOLOR";

	private static ShaderManager basicShader;

	public static ShaderManager getInstance() {
		if (null == basicShader) {
			basicShader = new ShaderManager();
		}
		return basicShader;
	}

	private ShaderManager() {
		loadDefaultShader();
	}

	public void loadDefaultShader() {
		
		// 加载默认的 shader
		{
			Shader posTexShader = new Shader(TEST_SHADER_POS_TEXTURE);
			posTexShader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
			posTexShader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
			posTexShader.compileShader();
			posTexShader.bindAttribute("position", Shader.POSITION);
			posTexShader.bindAttribute("texCoord", Shader.TEXCOORD);
			posTexShader.addUniform("transform");
			_cachedShader.put(posTexShader.getShaderName(), posTexShader);
		}

		{
			Shader posUColorShader = new Shader(TEST_SHADER_POS_UCOLOR);
			posUColorShader.addVertexShader(ResourceLoader.loadShader("test_triangle.vs"));
			posUColorShader.addFragmentShader(ResourceLoader.loadShader("test_triangle.fs"));
			posUColorShader.compileShader();
			posUColorShader.bindAttribute("position", Shader.POSITION);
			posUColorShader.addUniform("u_mvpMatrix");
			posUColorShader.addUniform("u_color");
			_cachedShader.put(posUColorShader.getShaderName(), posUColorShader);
		}
	}
	
	// 添加自定义的shader
	public void addCustomShader(Shader shader){
		if (getShader(shader.getShaderName()) != null) {
			System.out.println("已经存在!");
			return;
		}
		_cachedShader.put(shader.getShaderName(), shader);
	}

	public static Shader getShader(String name){
		return ShaderManager.getInstance()._cachedShader.get(name);
	}
	
}
