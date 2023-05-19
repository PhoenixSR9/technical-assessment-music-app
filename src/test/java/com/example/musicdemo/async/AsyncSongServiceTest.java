package com.example.musicdemo.async;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.musicdemo.domain.Song;

@ExtendWith(MockitoExtension.class)
public class AsyncSongServiceTest
{
	@InjectMocks
	private AsyncSongService sut;
	
	@Mock
	private RestTemplate restTemplate;
	
	@BeforeEach
	public void init()
	{
		setField(sut, "webhookUrl", "http://test");
	}
	
	@Test
	void shouldSuccessfullyCallWebHook()
	{
		ResponseEntity<Void> responseEntity = new ResponseEntity<Void>(HttpStatus.OK);
		when(restTemplate.postForEntity(any(String.class), any(Song.class), eq(Void.class))).thenReturn(responseEntity);
		sut.saveSongToWebHook(new Song());
		
		verify(restTemplate).postForEntity(any(String.class), any(Song.class), eq(Void.class));
	}
}
