package com.example.musicdemo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.musicdemo.model.IsrcModel;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;
import com.example.musicdemo.service.SongService;

@RestController
@RequestMapping("/songs")
public class SongController
{
	private final SongService songService;
	
	public SongController(SongService songService)
	{
		this.songService = songService;
	}
	
	@GetMapping(value = "/{isrc}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SongModel getSong(@PathVariable("isrc") String isrc)
	{
		return songService.getSong(isrc);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public IsrcModel createSong(@Validated @RequestBody AddSongRequestModel songRequestDto, 
			@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) String language)
	{
		return songService.createSong(songRequestDto, language);
	}
	
	@PutMapping(value = "/{isrc}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateSong(@PathVariable("isrc") String isrc, 
			@Validated @RequestBody UpdateSongRequestModel updateSongRequestDto)
	{
		songService.updateSong(isrc, updateSongRequestDto);
	}
	
	@DeleteMapping("/{isrc}")
	public void deleteSong(@PathVariable("isrc") String isrc)
	{
		songService.deleteSong(isrc);
	}
}
