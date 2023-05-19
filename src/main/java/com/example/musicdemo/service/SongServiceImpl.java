package com.example.musicdemo.service;

import java.util.Arrays;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.musicdemo.async.AsyncSongService;
import com.example.musicdemo.domain.Song;
import com.example.musicdemo.exception.ResourceExistsException;
import com.example.musicdemo.exception.ResourceNotFoundException;
import com.example.musicdemo.mapper.SongMapper;
import com.example.musicdemo.model.IsrcModel;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;
import com.example.musicdemo.repository.SongRepository;
import com.example.musicdemo.utility.IsrcGenerator;

@Service
public class SongServiceImpl implements SongService
{
	private final static Logger log = LoggerFactory.getLogger(SongServiceImpl.class);
	
	private final SongRepository songRepository;
	
	private final AsyncSongService asyncSongService;
	
	private final SongMapper songMapper;
	
	public SongServiceImpl(SongRepository songRepository, AsyncSongService asyncSongService, SongMapper songMapper)
	{
		this.songRepository = songRepository;
		this.asyncSongService = asyncSongService;
		this.songMapper = songMapper;
	}
	
	@Override
	public SongModel getSong(String isrc)
	{
		Song song = songRepository.findById(isrc)
				.orElseThrow(() -> new ResourceNotFoundException("Unable to find song with isrc: " + isrc));
		
		log.debug("Returning song from mongo: {}", song.toString());
		
		SongModel songResponseDto = songMapper.convertSongToSongResponseDto(song);
		return songResponseDto;
	}

	@Override
	public IsrcModel createSong(AddSongRequestModel addSongRequestModel, String language)
	{
		String locale = Arrays.asList(Locale.getISOCountries()).stream()
				.filter(country -> country.equals(language))
				.findFirst()
				.orElse(null);
		
		String isrc = IsrcGenerator.generateIsrc(addSongRequestModel.getRegistrantCode(), addSongRequestModel.getDesignationCode(), locale);

		if (songRepository.existsById(isrc))
			throw new ResourceExistsException("Isrc already exists: " + isrc);

		Song song = songMapper.convertSongDtoToSong(addSongRequestModel, isrc);
		
		log.debug("Creating song in mongo: {}", song.toString());
		
		song = songRepository.save(song);
		asyncSongService.saveSongToWebHook(song);
		
		return new IsrcModel(song.getIsrc());
	}

	@Override
	public void updateSong(String isrc, UpdateSongRequestModel updateSongRequestModel)
	{
		Song song = songRepository.findById(isrc)
				.orElseThrow(() -> new ResourceNotFoundException("Unable to find song with isrc: " + isrc));
		songMapper.convertUpdateSongRequestDtoToSong(updateSongRequestModel, song);
		
		log.debug("Updating song in mongo: {}", song.toString());
		
		songRepository.save(song);
	}

	@Override
	public void deleteSong(String isrc)
	{
		log.debug("Deleting song from mongo: {}", isrc);
		songRepository.deleteById(isrc);
	}
}
