package com.example.musicdemo.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.musicdemo.repository")
@EntityScan(basePackages = "com.example.musicdemo.domain")
@Configuration
public class MongoConfiguration
{

}
