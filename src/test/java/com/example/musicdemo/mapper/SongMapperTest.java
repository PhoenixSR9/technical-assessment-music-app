package com.example.musicdemo.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.musicdemo.domain.Musician;
import com.example.musicdemo.domain.Song;
import com.example.musicdemo.model.MusicianModel;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;

public class SongMapperTest
{
	private SongMapperImpl sut = new SongMapperImpl();
	
	@Test
	void shouldConvertSongDtoToSong()
	{
		AddSongRequestModel dto = new AddSongRequestModel();
		dto.setArtist("TestArtist");
		dto.setDesignationCode("12345");
		dto.setRegistrantCode("FEA");
		
		MusicianModel musicianDto = new MusicianModel();
		musicianDto.setName("TestName");
		musicianDto.setContribution("TestContribution");
		dto.setMusicianModels(Collections.singletonList(musicianDto));
		
		String isrc = "TESTISRC";
		
		Song song = sut.convertSongDtoToSong(dto, isrc);

		List<Musician> musicians = song.getMusicians();
		
		assertAll(
				() -> assertEquals(dto.getTitle(), song.getTitle()),
				() -> assertEquals(dto.getArtist(), song.getArtist()),
				() -> assertEquals(1, musicians.size()),
				() -> assertEquals(dto.getMusicianModels().size(), musicians.size()),
				() -> assertEquals(musicians.get(0).getName(), dto.getMusicianModels().get(0).getName()),
				() -> assertEquals(musicians.get(0).getContribution(), dto.getMusicianModels().get(0).getContribution()));
	}
	
	@Test
	void shouldConvertSongToSongResponseDto()
	{
		Song song = new Song();
		song.setArtist("TestArtist");
		song.setIsrc("TESTISRC");
		song.setTitle("TestTitle");
		
		Musician musician = new Musician();
		musician.setName("Test-Name");
		musician.setContribution("Test Contribution");
		song.setMusicians(Collections.singletonList(musician));
		
		SongModel response = sut.convertSongToSongResponseDto(song);
		List<MusicianModel> musiciansResponseDto = response.getMusicianModels();
		
		assertAll(
				() -> assertEquals(song.getArtist(), response.getArtist()),
				() -> assertEquals(song.getTitle(), response.getTitle()),
				() -> assertEquals(song.getIsrc(), response.getIsrc()),
				() -> assertEquals(1, musiciansResponseDto.size()),
				() -> assertEquals(song.getMusicians().size(), musiciansResponseDto.size()),
				() -> assertEquals(song.getMusicians().get(0).getName(), musiciansResponseDto.get(0).getName()),
				() -> assertEquals(song.getMusicians().get(0).getContribution(), musiciansResponseDto.get(0).getContribution()));
	}
	
	@Test
	void shouldConvertUpdateSongRequestDtoToSong()
	{
		UpdateSongRequestModel updateSongRequestDto = new UpdateSongRequestModel();
		updateSongRequestDto.setArtist("Test Artist");
		updateSongRequestDto.setTitle("Title");
		
		MusicianModel musicianDto = new MusicianModel();
		musicianDto.setName("Test Name");
		musicianDto.setContribution("Test Contribution");
		
		Song song = new Song();
		
		updateSongRequestDto.setMusicianModels(Collections.singletonList(musicianDto));
		
		sut.convertUpdateSongRequestDtoToSong(updateSongRequestDto, song);
		
		List<Musician> musicians = song.getMusicians();

		assertAll(
				() -> assertEquals(updateSongRequestDto.getArtist(), song.getArtist()),
				() -> assertEquals(updateSongRequestDto.getTitle(), song.getTitle()),
				() -> assertEquals(1, musicians.size()),
				() -> assertEquals(updateSongRequestDto.getMusicianModels().size(), musicians.size()),
				() -> assertEquals(updateSongRequestDto.getMusicianModels().get(0).getName(), musicians.get(0).getName()),
				() -> assertEquals(updateSongRequestDto.getMusicianModels().get(0).getContribution(), musicians.get(0).getContribution()));
	}
}
