package com.dai.render;

import com.dai.utils.ResourceLoader;

public class BasicShader extends Shader{

	private static BasicShader basicShader;
	
	public static BasicShader getInstance(){
		if (null == basicShader) {
			basicShader = new BasicShader();
		}
		return basicShader;
	}
	
	private BasicShader(){
		// 加载默认的 shader
		addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		compileShader();
		bindAttribute("position",POSITION);
		bindAttribute("texCoord",TEXCOORD);
		addUniform("transform");
	}
	
}
