package com.dai.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.dai.base.Vector3f;
import com.dai.render.Mesh;


//专门加载obj文件 
public class ObjLoader {
	
	public static Mesh loadObj(String fileName){
		
		// 存储所有顶点
		ArrayList<Float> allVertices = new ArrayList<Float>();
		
		// 存储所有顶点
		ArrayList<Float> allTextures = new ArrayList<Float>();
		
		// 存储所有法线
		ArrayList<Float> allNormals = new ArrayList<Float>();
		
		// 存储所有顶点对应的面法线
		HashMap<Integer, ArrayList<Vector3f>> allMaps = new HashMap<Integer, ArrayList<Vector3f>>();
 		
		// 所有索引,
		ArrayList<Integer> allIndices = new ArrayList<Integer>();
		
		
		Mesh mesh = new Mesh();
		return mesh;
	}
	
	
}
