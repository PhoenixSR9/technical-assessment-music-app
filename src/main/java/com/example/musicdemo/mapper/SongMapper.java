package com.example.musicdemo.mapper;

import com.example.musicdemo.domain.Song;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;

public interface SongMapper
{
	Song convertSongDtoToSong(AddSongRequestModel addSongRequestDto, String isrc);
	
	void convertUpdateSongRequestDtoToSong(UpdateSongRequestModel updateSongRequestDto, Song song);

	SongModel convertSongToSongResponseDto(Song song);
}
