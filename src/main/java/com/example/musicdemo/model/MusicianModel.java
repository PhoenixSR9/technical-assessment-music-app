package com.example.musicdemo.model;

import javax.validation.constraints.NotEmpty;

public class MusicianModel
{
	@NotEmpty(message = "must not be empty")
	private String name;
	
	@NotEmpty(message = "must not be empty")
	private String contribution;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getContribution()
	{
		return contribution;
	}
	public void setContribution(String contribution)
	{
		this.contribution = contribution;
	}
}
