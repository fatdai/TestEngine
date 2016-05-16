package com.dai.render;

import com.dai.entity.GameComponent;

public class MeshRender extends GameComponent {

	private Mesh mesh;
	private Material material;
	private Shader shader;
	
	public MeshRender(Mesh mesh, Material material,Shader shader) {
		this.mesh = mesh;
		this.material = material;
		this.shader = shader;
	}

	@Override
	public void render() {
		shader.bind();
		shader.updateUniforms(getTransform(),material);
		mesh.draw();
	}
}
