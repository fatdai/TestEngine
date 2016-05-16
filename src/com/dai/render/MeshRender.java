package com.dai.render;

import com.dai.entity.GameComponent;

public class MeshRender extends GameComponent {

	protected Mesh mesh;
	protected Material material;
	protected Shader shader;
	
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
