package com.example.musicdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.musicdemo.async.AsyncSongService;
import com.example.musicdemo.domain.Musician;
import com.example.musicdemo.domain.Song;
import com.example.musicdemo.exception.ResourceExistsException;
import com.example.musicdemo.exception.ResourceNotFoundException;
import com.example.musicdemo.mapper.SongMapper;
import com.example.musicdemo.model.MusicianModel;
import com.example.musicdemo.model.SongModel;
import com.example.musicdemo.model.request.AddSongRequestModel;
import com.example.musicdemo.model.request.UpdateSongRequestModel;
import com.example.musicdemo.repository.SongRepository;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest
{
	@InjectMocks
	private SongServiceImpl sut;
	
	@Mock
	private SongRepository songRepository;
	
	@Mock
	private AsyncSongService asyncSongService;
	
	@Mock
	private SongMapper songMapper;
	
	@Captor
	private ArgumentCaptor<String> isrcCaptor;
	
	@Test
	void shouldSuccessfullyGetSong()
	{
		String isrc = "TestIRC";

		Song song = new Song();
		song.setMusicians(new ArrayList<Musician>());
		
		when(songRepository.findById(isrc)).thenReturn(Optional.of(song));
		when(songMapper.convertSongToSongResponseDto(song)).thenReturn(new SongModel());
		
		sut.getSong(isrc);
		
		verify(songMapper).convertSongToSongResponseDto(song);
	}
	
	@Test
	void shouldThrowExceptionWhileGettingSong()
	{
		String isrc = "TestIRC";

		Song song = new Song();
		song.setIsrc(isrc);

		when(songRepository.findById(isrc)).thenReturn(Optional.empty());
		
		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
			sut.getSong(isrc);
		});
		
		assertEquals("Unable to find song with isrc: " + isrc, ex.getMessage());
	}
	
	@Test
	void shouldSuccessfullyCreateSong()
	{
		AddSongRequestModel addSongRequestModel = new AddSongRequestModel();
		addSongRequestModel.setRegistrantCode("WQL");
		addSongRequestModel.setDesignationCode("12345");
		addSongRequestModel.setTitle("TESTTitle");
		addSongRequestModel.setArtist("TestArtist");
		
		Song song = new Song();
		song.setMusicians(new ArrayList<Musician>());
		
		when(songRepository.existsById(anyString())).thenReturn(false);
		when(songMapper.convertSongDtoToSong(any(AddSongRequestModel.class), anyString())).thenReturn(song);
		when(songRepository.save(song)).thenReturn(song);
		
		sut.createSong(addSongRequestModel, Locale.US.getCountry());

		verify(songRepository).existsById(isrcCaptor.capture());
		verify(songMapper).convertSongDtoToSong(addSongRequestModel, isrcCaptor.getValue());
		verify(songRepository).save(Mockito.any(Song.class));
		verify(asyncSongService).saveSongToWebHook(song);
		
	}
	
	@Test
	void shouldThrowExceptionWhileCreatingSong()
	{
		AddSongRequestModel addSongRequestModel = new AddSongRequestModel();
		addSongRequestModel.setArtist("TestArtist");
		addSongRequestModel.setTitle("TestTitle");
		addSongRequestModel.setRegistrantCode("TST");
		addSongRequestModel.setDesignationCode("12345");
		
		Song savedSong = new Song();
		savedSong.setTitle(addSongRequestModel.getTitle());
		savedSong.setArtist(addSongRequestModel.getArtist());
		savedSong.setIsrc(null);
		
		when(songRepository.existsById(anyString())).thenReturn(true);
		
		ResourceExistsException ex = assertThrows(ResourceExistsException.class, () -> {
			sut.createSong(addSongRequestModel, Locale.US.getCountry());
		});
		
		verify(songRepository).existsById(isrcCaptor.capture());
		
		String isrcValue = isrcCaptor.getValue();
		assertTrue(ex.getMessage().contains("Isrc already exists: " + isrcValue));
	}
	
	@Test
	void shouldSuccessfullyUpdateSong()
	{
		String isrc = "Test ISRC";
		
		UpdateSongRequestModel updateSongRequestModel = new UpdateSongRequestModel();
		updateSongRequestModel.setArtist("Test Artist");
		updateSongRequestModel.setTitle("Test Title");
		updateSongRequestModel.setMusicianModels(Collections.singletonList(new MusicianModel()));
		
		Song song = new Song();
		song.setIsrc(isrc);
		song.setMusicians(new ArrayList<Musician>());
		
		when(songRepository.findById(isrc)).thenReturn(Optional.of(song));
		
		sut.updateSong(isrc, updateSongRequestModel);
		
		verify(songMapper).convertUpdateSongRequestDtoToSong(updateSongRequestModel, song);
		verify(songRepository).save(song);
	}
	
	@Test
	void shouldThrowExceptionUpdatingSong()
	{
		String isrc = "Test ISRC";
		
		UpdateSongRequestModel updateSongRequestModel = new UpdateSongRequestModel();
		updateSongRequestModel.setArtist("Test Artist");
		updateSongRequestModel.setTitle("Test Title");
		updateSongRequestModel.setMusicianModels(Collections.singletonList(new MusicianModel()));
		
		when(songRepository.findById(isrc)).thenReturn(Optional.empty());
		
		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
			sut.updateSong(isrc, updateSongRequestModel);
		});
		
		assertEquals("Unable to find song with isrc: " + isrc, ex.getMessage());
	}
	
	@Test
	void shouldDeleteSong()
	{
		String isrc = "TestISRC";
		sut.deleteSong(isrc);
		
		verify(songRepository).deleteById(isrc);
	}
}
