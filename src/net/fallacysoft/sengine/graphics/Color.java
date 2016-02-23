package net.fallacysoft.sengine.graphics;

public class Color
{
	// ---- Constants
	public static final Color White = new Color(255, 255, 255);
	public static final Color Black = new Color(0, 0, 0);
	public static final Color Red = new Color(255, 0, 0);
	public static final Color Green = new Color(0, 255, 0);
	public static final Color Blue = new Color(0, 0, 255);
	
	// ---- Fields
	public float r, g, b, a;
	
	// ---- Constructor
	public Color(int r, int g, int b)
	{
		this(r, g, b, 100);
	}
	
	public Color(int r, int g, int b, int a)
	{
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 100f;
	}
	
	// ---- Get / Set
	public int getR()
	{
		return (int) r * 255;
	}
	
	public void setR(int r)
	{
		this.r = r / 255f;
	}
	
	public int getG()
	{
		return (int) g * 255;
	}
	
	public void setG(int g)
	{
		this.g = g / 255f;
	}
	
	public int getB()
	{
		return (int) b * 255;
	}
	
	public void setB(int b)
	{
		this.b = b / 255f;
	}
	
	public int getA()
	{
		return (int) a * 100;
	}
	
	public void setA(int a)
	{
		this.a = a / 100f;
	}
}
