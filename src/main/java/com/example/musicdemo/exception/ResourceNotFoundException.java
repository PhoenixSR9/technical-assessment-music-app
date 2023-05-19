package com.example.musicdemo.exception;

public class ResourceNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = -47069239355490940L;

	public ResourceNotFoundException(String message)
	{
		super(message);
	}
}
