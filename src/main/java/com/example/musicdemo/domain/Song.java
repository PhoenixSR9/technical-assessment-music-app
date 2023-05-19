package com.example.musicdemo.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songs")
public class Song
{
	@Id
	private String isrc;
	private String title;
	private String artist;
	
	private List<Musician> musicians;
	
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
	public List<Musician> getMusicians()
	{
		return musicians;
	}
	public void setMusicians(List<Musician> musicians)
	{
		this.musicians = musicians;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Song(Isrc:%s Title:%s,Artist:%s", isrc, title, artist));
		
		for (Musician musician : musicians)
		{
			sb.append(String.format(",Musician(Name:%s, Contribution:%s)", musician.getName(), musician.getContribution()));
		}
		sb.append(")");
		return sb.toString();
	}
}
