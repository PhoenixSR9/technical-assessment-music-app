# Take Home Challenge: Music Info API

## Overview
This exercise is meant to showcase your technical implementation & design abilities. It is expected that you will fork this repository in GitHub and share the link with us when complete. Please be prepared to explain the design. 

For the purposes of the exercise, pretend you're on a team working on a new website that customers can use to look up information about music. Other team members are handling the front end, but you're working on the back-end API.

## Instructions
Using Java8, and SpringBoot create a REST API with the following endpoints using the Controller Service Repository design pattern:

- Add a song
- Update a song
- Get a song
- Delete a song

The data model for the song should look something like this, and be stored in one of [mongo, mysql, H2]. 

```
{
    "isrc": "<string>",
    "title": "<string>",
    "artist": "<string>",
    "musicians": [
        {
            "name": "<string>",
            "contribution": "<string>"
        }
    ]
}
```

Unit tests should be created for the controller and service methods. 

## Bonus Instructions
1. Whenever a song is added, an asynchronous method should post the information to [webhook.site](https://webhook.site/). 
2. Have the application running in a Docker container. 


