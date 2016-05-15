package com.dai.render;

import com.dai.entity.GameComponent;

public class MeshRender extends GameComponent {

	private Mesh mesh;
	private Material material;

	public MeshRender(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void render(Shader shader) {
		shader.bind();
		shader.updateUniforms(getTransform(),material);
		mesh.draw();
	}
}
