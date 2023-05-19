package com.example.musicdemo.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.musicdemo.model.MusicianModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateSongRequestModel
{
	@NotNull(message = "must not be empty")
	private String title;
	@NotNull(message = "must not be empty")
	private String artist;
	
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
	public List<MusicianModel> getMusicianModels()
	{
		return musicianModels;
	}
	public void setMusicianModels(List<MusicianModel> musicianModels)
	{
		this.musicianModels = musicianModels;
	}
}
