package com.example.musicdemo.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.example.musicdemo.model.MusicianModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddSongRequestModel
{
	@NotEmpty(message = "must not be empty")
	private String title;
	@NotEmpty(message = "must not be empty")
	private String artist;
	@NotEmpty(message = "must not be empty")
	@Pattern(regexp = "[a-zA-Z\\d]{3}", message = "must be 3 alphanumeric characters")
	private String registrantCode;
	@NotEmpty(message = "must not be empty")
	@Pattern(regexp = "[\\d]{5}", message = "must be between 00000 and 99999")
	private String designationCode;
	
	@NotEmpty(message = "must not be empty")
	@Valid
	@JsonProperty("musicians")
	private List<MusicianModel> musicianModels;
	
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	public String getRegistrantCode()
	{
		return registrantCode;
	}
	public void setRegistrantCode(String registrantCode)
	{
		this.registrantCode = registrantCode;
	}
	public String getDesignationCode()
	{
		return designationCode;
	}
	public void setDesignationCode(String designationCode)
	{
		this.designationCode = designationCode;
	}
	public List<MusicianModel> getMusicianModels()
	{
		return musicianModels;
	}
	public void setMusicianModels(List<MusicianModel> musicianModels)
	{
		this.musicianModels = musicianModels;
	}
}
