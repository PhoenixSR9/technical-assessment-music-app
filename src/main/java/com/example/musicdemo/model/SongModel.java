package com.example.musicdemo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SongModel
{
	private String isrc;
	private String title;
	private String artist;
	
	@JsonProperty("musicians")
	private List<MusicianModel> musicianModels;

	public String getIsrc()
	{
		return isrc;
	}
	public void setIsrc(String isrc)
	{
		this.isrc = isrc;
	}
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
	public void setMusicianDtos(List<MusicianModel> musicianModels)
	{
		this.musicianModels = musicianModels;
	}
}
