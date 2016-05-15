package com.dai.base;

public class Matrix4f {
	private float[][] m;

	public Matrix4f() {
		m = new float[4][4];
	}

	public float[][] getM() {
		return m;
	}

	public void setM(float[][] m) {
		this.m = m;
	}

	public float get(int x, int y) {
		return m[x][y];
	}

	public void set(int x, int y, float value) {
		m[x][y] = value;
	}

	public Matrix4f initIdentity() {
		m[0][0] = 1; m[0][1] = 0 ; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 1 ; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0 ; m[2][2] = 1; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0;  m[3][2] = 0; m[3][3] = 1;
		return this;
	}
	

	public Matrix4f initTranslation(float x,float y,float z) {
		m[0][0] = 1; m[0][1] = 0 ; m[0][2] = 0; m[0][3] = x;
		m[1][0] = 0; m[1][1] = 1 ; m[1][2] = 0; m[1][3] = y;
		m[2][0] = 0; m[2][1] = 0 ; m[2][2] = 1; m[2][3] = z;
		m[3][0] = 0; m[3][1] = 0;  m[3][2] = 0; m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initScale(float x,float y,float z) {
		m[0][0] = x; m[0][1] = 0 ; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = y ; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0 ; m[2][2] = z; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0;  m[3][2] = 0; m[3][3] = 1;
		return this;
	}
	
	// 注意: 设矩阵 M 将 世界空间 转换到  相机空间. 
	//      计相机在 世界空间的 矩阵表示为 V(假设相机不会缩放), 则 V = T * R 
	//      则 M = V的逆矩阵. 而 V^-1 = (T * R)^-1 = R^-1 * T^-1,同时,由于正交矩阵的逆就是其转置,
	//      所以下面这个 其实是计算 R^-1
	public Matrix4f initCamera(Vector3f forward,Vector3f up) {
	
		// 相机在 世界空间中的矩阵 V, 其z轴是 forward,
		// 所以可以计算出其x轴  x = up.cross(forward)
		// 然后计算出y轴  y = z.cross(x)
//		Vector3f f = forward;
//		f.normailze();
//		
//		Vector3f r = up;
//		r.normailze();
//		r = r.cross(f);
//		
//		Vector3f u = f.cross(r);
//		
//		m[0][0] = r.getX(); m[0][1] = r.getY() ; m[0][2] = r.getZ(); m[0][3] = 0;
//		m[1][0] = u.getX(); m[1][1] = u.getY() ; m[1][2] = u.getZ(); m[1][3] = 0;
//		m[2][0] = f.getX(); m[2][1] = f.getY() ; m[2][2] = f.getZ(); m[2][3] = 0;
//		m[3][0] = 0; 		m[3][1] = 0;  		 m[3][2] = 0; 		 m[3][3] = 1;
		
		// 计算相机在世界空间中的矩阵表示 
		// z轴
		Vector3f zaxis = new Vector3f(-forward.getX(), -forward.getY(), -forward.getZ());
		
		// x轴
		Vector3f xaxis = up.cross(zaxis);
		xaxis.normailze();
		
		// y轴
		Vector3f yaxis = zaxis.cross(xaxis);
		yaxis.normailze();
		
		m[0][0] = xaxis.getX(); m[0][1] = xaxis.getY() ; m[0][2] = xaxis.getZ(); m[0][3] = 0;
		m[1][0] = yaxis.getX(); m[1][1] = yaxis.getY() ; m[1][2] = yaxis.getZ(); m[1][3] = 0;
		m[2][0] = zaxis.getX(); m[2][1] = zaxis.getY() ; m[2][2] = zaxis.getZ(); m[2][3] = 0;
		m[3][0] = 0; 		m[3][1] = 0;  		 m[3][2] = 0; 		 m[3][3] = 1;
		
		return this;
	}
	
//	public Matrix4f initProjection(float fov,float width,float height,float near,float far) {
//		float aspectRatio = width / height;
//		return initPerspective(fov, aspectRatio, near, far);
//	}
	
	public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = (float)Math.tan(Math.toRadians(fov) / 2);
		float zRange = zFar - zNear;
		float factor = 1.0f/tanHalfFOV;
		
		m[0][0] = (1.0f / aspectRatio) * factor; m[0][1] = 0;		m[0][2] = 0;					m[0][3] = 0;
		m[1][0] = 0;							 m[1][1] = factor;	m[1][2] = 0;					m[1][3] = 0;
		m[2][0] = 0;							 m[2][1] = 0;		m[2][2] = (-zNear -zFar)/zRange;m[2][3] = -2 * zFar * zNear / zRange;
		m[3][0] = 0;							 m[3][1] = 0;		m[3][2] = -1;					m[3][3] = 0;
		
		return this;
	}
	
	public Matrix4f initRotation(float x,float y,float z) {
		
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		rz.m[0][0] = (float)Math.cos(z);rz.m[0][1] = -(float)Math.sin(z);rz.m[0][2] = 0;				rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z);rz.m[1][1] = (float)Math.cos(z);rz.m[1][2] = 0;					rz.m[1][3] = 0;
		rz.m[2][0] = 0;					rz.m[2][1] = 0;					rz.m[2][2] = 1;					rz.m[2][3] = 0;
		rz.m[3][0] = 0;					rz.m[3][1] = 0;					rz.m[3][2] = 0;					rz.m[3][3] = 1;
		
		rx.m[0][0] = 1;					rx.m[0][1] = 0;					rx.m[0][2] = 0;					rx.m[0][3] = 0;
		rx.m[1][0] = 0;					rx.m[1][1] = (float)Math.cos(x);rx.m[1][2] = -(float)Math.sin(x);rx.m[1][3] = 0;
		rx.m[2][0] = 0;					rx.m[2][1] = (float)Math.sin(x);rx.m[2][2] = (float)Math.cos(x);rx.m[2][3] = 0;
		rx.m[3][0] = 0;					rx.m[3][1] = 0;					rx.m[3][2] = 0;					rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y);ry.m[0][1] = 0;					ry.m[0][2] = -(float)Math.sin(y);ry.m[0][3] = 0;
		ry.m[1][0] = 0;					ry.m[1][1] = 1;					ry.m[1][2] = 0;					ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(y);ry.m[2][1] = 0;					ry.m[2][2] = (float)Math.cos(y);ry.m[2][3] = 0;
		ry.m[3][0] = 0;					ry.m[3][1] = 0;					ry.m[3][2] = 0;					ry.m[3][3] = 1;
		
		m = rz.mul(ry.mul(rx)).getM();
		
		return this;
	}

	public Matrix4f mul(Matrix4f r) {
		Matrix4f res = new Matrix4f();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.set(i, j, 
						m[i][0] * r.m[0][j] + 
						m[i][1] * r.m[1][j] + 
						m[i][2] * r.m[2][j] + 
						m[i][3] * r.m[3][j]);
			}
		}
		return res;
	}
	
	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right)
	{
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		m[0][0] = r.getX();	m[0][1] = r.getY();	m[0][2] = r.getZ();	m[0][3] = 0;
		m[1][0] = u.getX();	m[1][1] = u.getY();	m[1][2] = u.getZ();	m[1][3] = 0;
		m[2][0] = f.getX();	m[2][1] = f.getY();	m[2][2] = f.getZ();	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;

		return this;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(m[0][0]).append("\t");
		sb.append(m[0][1]).append("\t");
		sb.append(m[0][2]).append("\t");
		sb.append(m[0][3]).append("\t");
		sb.append("\n");
		sb.append(m[1][0]).append("\t");
		sb.append(m[1][1]).append("\t");
		sb.append(m[1][2]).append("\t");
		sb.append(m[1][3]).append("\t");
		sb.append("\n");
		sb.append(m[2][0]).append("\t");
		sb.append(m[2][1]).append("\t");
		sb.append(m[2][2]).append("\t");
		sb.append(m[2][3]).append("\t");
		sb.append("\n");
		sb.append(m[3][0]).append("\t");
		sb.append(m[3][1]).append("\t");
		sb.append(m[3][2]).append("\t");
		sb.append(m[3][3]).append("\t");
		sb.append("\n");
		return sb.toString();
	}
	
}
