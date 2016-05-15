package com.dai.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.dai.base.Vector3f;
import com.dai.base.Vertex;
import com.dai.render.Mesh;
import com.dai.render.Texture;

public class ResourceLoader {

	public static Texture loadTexture(String fileName) {

		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		try {
			return TextureLoader.getTexture(ext,new File("./res/textures/" + fileName));
		} catch (Exception e) {
			System.err.println("Load Texture error! fileName : " + fileName);
			System.exit(1);
		}

		return null;
	}

	public static String loadShader(String filename) {
		StringBuilder shaderSource = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./res/shaders/" + filename)));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return shaderSource.toString();
	}

	public static Mesh loadMesh(String fileName) {
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];

		// 检查后缀
		if (!ext.equals("obj")) {
			System.err.println("Error: Unsupport mesh format." + fileName);
			System.exit(1);
		}

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./res/models/" + fileName)));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float
							.valueOf(tokens[3]))));
				} else if (tokens[0].equals("f")) {
					indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);

					if (tokens.length > 4) {
						indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
					}
				}
			}
			reader.close();

			Mesh mesh = new Mesh();
			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);

			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);

			mesh.addVertices(vertexData, Util.toIntArrat(indexData));
			return mesh;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
