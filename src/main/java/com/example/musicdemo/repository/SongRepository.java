package com.example.musicdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.musicdemo.domain.Song;

public interface SongRepository extends MongoRepository<Song, String>
{

}
