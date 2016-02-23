package net.fallacysoft.sengine.graphics;

import static org.lwjgl.opengl.GL20.glUniformMatrix4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.fallacysoft.sengine.maths.Matrix4f;
import net.fallacysoft.sengine.maths.Vector3f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader
{
	// ---- Fields
	private int programId, vertexId, fragmentId;
	
	// ---- Constructor
	public Shader(String vertexSource, String fragmentSource) throws Exception
	{
		programId = GL20.glCreateProgram();
		
		if (programId == GL11.GL_FALSE)
			throw new Exception("Sarias Engine Error : Un erreur est survenue lors de la création du Shader Program");
		
		vertexId = createShader(loadShader(vertexSource), GL20.GL_VERTEX_SHADER);
		fragmentId = createShader(loadShader(fragmentSource), GL20.GL_FRAGMENT_SHADER);
		
		GL20.glAttachShader(programId, vertexId);
		GL20.glAttachShader(programId, fragmentId);
		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
	}
	
	// ---- Methods
	private int createShader(String source, int type) throws Exception
	{
		int shader = GL20.glCreateShader(type);
		if (shader == GL11.GL_FALSE)
			throw new Exception("Sarias Engine Error : Un erreur est survenue lors de la création du " + ((type == 35633) ? "Vertex" : "Fragment") + " Shader");
		
		GL20.glShaderSource(shader, source);
		GL20.glCompileShader(shader);
		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
			throw new Exception("Sarias Engine Error : Un erreur est survenue lors de la compilation du " + ((type == 35633) ? "Vertex" : "Fragment") + " Shader");
		
		return shader;
	}
	
	private String loadShader(String path) throws IOException
	{
		String shader = "";
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String buffer = "";
		while ((buffer = reader.readLine()) != null)
			shader += buffer + "\n";
		
		reader.close();
		return shader;
	}
	
	public int getUniform(String name)
	{
		return GL20.glGetUniformLocation(programId, name);
	}
	
	public void setUniform(String name, int value)
	{
		GL20.glUniform1i(getUniform(name), value);
	}
	
	public void setUniform(String name, float value)
	{
		GL20.glUniform1f(getUniform(name), value);
	}
	
	public void setUniform(String name, Vector3f value)
	{
		GL20.glUniform3f(getUniform(name), value.x, value.y, value.z);
	}
	
	public void setUniform(String name, Matrix4f value)
	{
		glUniformMatrix4(getUniform(name), true, value.getBuffer());
	}
	
	public void bind()
	{
		GL20.glUseProgram(programId);
	}
	
	public void unbind()
	{
		GL20.glUseProgram(0);
	}
	
	public void dispose()
	{
		unbind();
		GL20.glDetachShader(programId, vertexId);
		GL20.glDetachShader(programId, fragmentId);
		GL20.glDeleteShader(vertexId);
		GL20.glDeleteShader(fragmentId);
		GL20.glDeleteProgram(programId);
	}
}
