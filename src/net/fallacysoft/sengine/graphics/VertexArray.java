package net.fallacysoft.sengine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArray
{
	// ---- Fields
	private int vaoId, indicesCount;
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	
	// ---- Constructor
	public VertexArray(float[] vertexData, float[] texCoords, int[] indicesData)
	{
		createVao();
		storeIndices(indicesData);
		storeData(0, 3, vertexData);
		storeData(1, 2, texCoords);
		unbindVao();
		
		indicesCount = indicesData.length;
	}
	
	// ---- Methods
	private void createVao()
	{
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
	}
	
	private void storeData(int id, int size, float[] data)
	{
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		FloatBuffer buffer = createBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(id, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void storeIndices(int[] data)
	{
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		IntBuffer buffer = createBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private FloatBuffer createBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer createBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void unbindVao()
	{
		GL30.glBindVertexArray(0);
	}
	
	public void dispose()
	{
		GL30.glDeleteVertexArrays(vaoId);
		for (int vboId : vbos)
			GL15.glDeleteBuffers(vboId);
		vbos.clear();
	}
	
	public void render()
	{
		GL30.glBindVertexArray(vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
