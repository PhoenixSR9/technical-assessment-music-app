package com.example.musicdemo.service;

import com.example.musicdemo.model.IsrcModel;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;

public interface SongService
{
	SongModel getSong(String isrc);
	
	IsrcModel createSong(AddSongRequestModel addSongRequestModel, String language);
	
	void updateSong(String isrc, UpdateSongRequestModel updateSongRequestModel);
	
	void deleteSong(String isrc);

}
