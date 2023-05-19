package com.example.musicdemo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.musicdemo.domain.Musician;
import com.example.musicdemo.domain.Song;
import com.example.musicdemo.model.MusicianModel;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;

@Service
public class SongMapperImpl implements SongMapper
{

	@Override
	public Song convertSongDtoToSong(AddSongRequestModel addSongRequestDto, String isrc)
	{
		Song song = new Song();
		song.setIsrc(isrc);
		song.setArtist(addSongRequestDto.getArtist());
		song.setTitle(addSongRequestDto.getTitle());
		
		List<Musician> musicians = new ArrayList<>();
		
		for (MusicianModel musicianDto : addSongRequestDto.getMusicianModels())
		{
			Musician musician = new Musician();
			musician.setName(musicianDto.getName());
			musician.setContribution(musicianDto.getContribution());
			musicians.add(musician);
		}
		song.setMusicians(musicians);
		
		return song;
	}

	@Override
	public void convertUpdateSongRequestDtoToSong(UpdateSongRequestModel updateSongRequestDto, Song song)
	{
		song.setTitle(updateSongRequestDto.getTitle());
		song.setArtist(updateSongRequestDto.getArtist());
		
		List<Musician> musiciansToUpdate = new ArrayList<>();
		
		for (MusicianModel musicianDto : updateSongRequestDto.getMusicianModels())
		{
			Musician musician = new Musician();
			musician.setName(musicianDto.getName());
			musician.setContribution(musicianDto.getContribution());
			musiciansToUpdate.add(musician);
			
		}
		song.setMusicians(musiciansToUpdate);
	}

	@Override
	public SongModel convertSongToSongResponseDto(Song song)
	{
		SongModel songResponseDto = new SongModel();
		songResponseDto.setIsrc(song.getIsrc());
		songResponseDto.setTitle(song.getTitle());
		songResponseDto.setArtist(song.getArtist());
		
		List<MusicianModel> musicianDtos = new ArrayList<>();
		
		for (Musician musician : song.getMusicians())
		{
			MusicianModel musicianDto = new MusicianModel();
			musicianDto.setName(musician.getName());
			musicianDto.setContribution(musician.getContribution());
			musicianDtos.add(musicianDto);
		}
		
		songResponseDto.setMusicianDtos(musicianDtos);
		return songResponseDto;
	}

}
