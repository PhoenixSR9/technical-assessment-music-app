package com.example.musicdemo.model;

/*
 * JSON structure based on RFC7807 spec. 
 * Reference: https://www.rfc-editor.org/rfc/rfc7807#section-3.1
 */

public class ErrorModel
{
	public String title;
	private int status;
	private String detail;
	
	private ErrorModel(ErrorDtoBuilder builder)
	{
		this.title = builder.title;
		this.status = builder.status;
		this.detail = builder.detail;
	}
	
	
	public String getTitle()
	{
		return title;
	}
	public int getStatus()
	{
		return status;
	}
	public String getMessage()
	{
		return detail;
	}
	
	public static class ErrorDtoBuilder
	{
		private String title;
		private int status;
		private String detail;
	
		public ErrorDtoBuilder withTitle(String title)
		{
			this.title = title;
			return this;
		}
		
		public ErrorDtoBuilder withStatus(int status)
		{
			this.status = status;
			return this;
		}
		
		public ErrorDtoBuilder withDetail(String detail)
		{
			this.detail = detail;
			return this;
		}
		
		public ErrorModel build()
		{
			return new ErrorModel(this);
		}
	}
}
