package net.fallacysoft.sengine.maths;

public class Vector3f
{
	// ---- Fields
	public float x, y, z;
	
	// ---- Constructor
	public Vector3f()
	{
		this(0, 0, 0);
	}
	
	public Vector3f(Vector3f vector)
	{
		this(vector.x, vector.y, vector.z);
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
