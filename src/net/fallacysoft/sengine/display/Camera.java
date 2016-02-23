package net.fallacysoft.sengine.display;

import net.fallacysoft.sengine.maths.Matrix4f;
import net.fallacysoft.sengine.maths.Vector3f;

import org.lwjgl.opengl.Display;

public class Camera
{
	// ---- Fields
	private Vector3f position;
	private Matrix4f projectionMatrix;
	
	// ---- Constructor
	public Camera(float fov, float zNear, float zFar)
	{
		position = new Vector3f();
		projectionMatrix = createProjectionMatrix(fov, zNear, zFar);
	}
	
	// ---- Get / Set
	public Vector3f getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return projectionMatrix;
	}
	
	public Matrix4f getViewMatrix()
	{
		Matrix4f pMatrix = new Matrix4f();
		pMatrix.translate(position);
		
		return pMatrix;
	}
	
	// ---- Methods
	private Matrix4f createProjectionMatrix(float fov, float zNear, float zFar)
	{
		Matrix4f projectionMatrix = new Matrix4f();
		float ratio = (float) Display.getWidth() / (float) Display.getHeight();
		float yScale = (float)Math.tan(Math.toRadians(fov / 2));
		float xScale = 1.0f / (yScale * ratio);
		float range = zNear - zFar;
		
		projectionMatrix.setM(0, 0, xScale);
		projectionMatrix.setM(1, 1, 1.0f / yScale);
		projectionMatrix.setM(2, 2, (-zNear - zFar) / range);
		projectionMatrix.setM(2, 3, 2 * zFar * zNear / range);
		projectionMatrix.setM(3, 2, 1);
		projectionMatrix.setM(3, 3, 0);
		return projectionMatrix;
	}
}
