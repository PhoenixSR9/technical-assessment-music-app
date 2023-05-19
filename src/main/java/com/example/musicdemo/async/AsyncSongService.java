package com.example.musicdemo.async;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.musicdemo.domain.Song;


@Component
public class AsyncSongService
{
	private final static Logger log = LoggerFactory.getLogger(AsyncSongService.class);
	
	@Value("${webhook_url}")
	private String webhookUrl;
	
	private final RestTemplate restTemplate;
	
	public AsyncSongService(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}
	
	@PostConstruct
	public void init()
	{
		log.info("Using webhhook url: {}", webhookUrl);
	}
	
	@Async
	public void saveSongToWebHook(Song song)
	{
		ResponseEntity<Void> response = restTemplate.postForEntity(webhookUrl, song, Void.class);
		
		log.info("Received response status from webhook: {}", response.getStatusCode().value());
	}
}
