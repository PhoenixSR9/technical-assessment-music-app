package com.example.musicdemo.exception;

public class ResourceExistsException extends RuntimeException
{
	private static final long serialVersionUID = 9062193225639726880L;

	public ResourceExistsException(String message)
	{
		super(message);
	}
}
