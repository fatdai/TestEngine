package com.dai.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.dai.base.Matrix4f;
import com.dai.base.Vertex;

public class Util {

	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		for (int i = 0; i < vertices.length; i++) {
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
			buffer.put(vertices[i].getTexCoord().getX());
			buffer.put(vertices[i].getTexCoord().getY());
		}
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer createFlippedBuffer(Matrix4f value) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				buffer.put(value.get(j, i));
			}
		}
		buffer.flip();
		return buffer;
	}

	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	public static IntBuffer createFlippedBuffer(int[] indices) {
		IntBuffer buffer = createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		return buffer;
	}

	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			if (!data[i].equals("")) {
				result.add(data[i]);
			}
		}

		String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}

	public static int[] toIntArrat(Integer[] data) {
		int[] result = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			result[i] = data[i].intValue();
		}
		return result;
	}
}
