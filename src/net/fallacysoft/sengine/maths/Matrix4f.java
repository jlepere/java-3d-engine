package net.fallacysoft.sengine.maths;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f
{
	// ---- Fields
	private float[][] m;
	
	// ---- Constructor
	public Matrix4f()
	{
		m = new float[4][4];
	}
	
	// ---- Get / Set
	public float[][] getMatrix()
	{
		return m;
	}
	
	public void setM(int x, int y, float value)
	{
		m[x][y] = value;
	}
	
	public FloatBuffer getBuffer()
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
				buffer.put(m[x][y]);
		}
		buffer.flip();
		return buffer;
	}
	
	// ---- Methods
	public Matrix4f identity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		return this;
	}
	
	public Matrix4f translate(Vector3f vector)
	{
		return translate(vector.x, vector.y, vector.z);
	}
	
	public Matrix4f translate(float x, float y, float z)
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		return this;
	}

	public Matrix4f scale(float scale)
	{
		m[0][0] = scale;	m[0][1] = 0;		m[0][2] = 0;		m[0][3] = 0;
		m[1][0] = 0;		m[1][1] = scale;	m[1][2] = 0;		m[1][3] = 0;
		m[2][0] = 0;		m[2][1] = 0;		m[2][2] = scale;	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;
		return this;
	}
	
	public Matrix4f rotation(Vector3f vector)
	{
		return rotation(vector.x, vector.y, vector.z);
	}
	
	public Matrix4f rotation(float x, float y, float z)
	{
		Matrix4f rX = new Matrix4f();
		Matrix4f rY = new Matrix4f();
		Matrix4f rZ = new Matrix4f();
		
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		
		rX.m[0][0] = 1;	rX.m[0][1] = 0;						rX.m[0][2] = 0;						rX.m[0][3] = 0;
		rX.m[1][0] = 0;	rX.m[1][1] = (float) Math.cos(x);	rX.m[1][2] = -(float) Math.sin(x);	rX.m[1][3] = 0;
		rX.m[2][0] = 0;	rX.m[2][1] = (float) Math.sin(x);	rX.m[2][2] = (float) Math.cos(x);	rX.m[2][3] = 0;
		rX.m[3][0] = 0;	rX.m[3][1] = 0;						rX.m[3][2] = 0;						rX.m[3][3] = 1;
		
		rY.m[0][0] = (float) Math.cos(y);	rY.m[0][1] = 0;	rY.m[0][2] = (float) Math.sin(y);	rY.m[0][3] = 0;
		rY.m[1][0] = 0;						rY.m[1][1] = 1;	rY.m[1][2] = 0;						rY.m[1][3] = 0;
		rY.m[2][0] = -(float) Math.sin(y);	rY.m[2][1] = 0;	rY.m[2][2] = (float) Math.cos(y);	rY.m[2][3] = 0;
		rY.m[3][0] = 0;						rY.m[3][1] = 0;	rY.m[3][2] = 0;						rY.m[3][3] = 1;
		
		rZ.m[0][0] = (float) Math.cos(z);	rZ.m[0][1] = -(float) Math.sin(z);	rZ.m[0][2] = 0;	rZ.m[0][3] = 0;
		rZ.m[1][0] = (float) Math.sin(z);	rZ.m[1][1] = (float) Math.cos(z);	rZ.m[1][2] = 0;	rZ.m[1][3] = 0;
		rZ.m[2][0] = 0;						rZ.m[2][1] = 0;						rZ.m[2][2] = 1;	rZ.m[2][3] = 0;
		rZ.m[3][0] = 0;						rZ.m[3][1] = 0;						rZ.m[3][2] = 0;	rZ.m[3][3] = 1;
		
		m = rZ.multiply(rY.multiply(rX)).getMatrix();
		return this;
	}
	
	public Matrix4f multiply(Matrix4f matrix)
	{
		Matrix4f r = new Matrix4f();
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				r.m[x][y] = m[x][0] * matrix.m[0][y] +
							m[x][1] * matrix.m[1][y] +
							m[x][2] * matrix.m[2][y] +
							m[x][3] * matrix.m[3][y];
			}
		}
		return r;
	}
}
