# Music Demo Application

## Overview
This is a Spring Boot application that is responsible for the service management of songs stored in a database.

## Prerequisites
- JDK8
- Latest version of Spring 2.7.x
- Docker
- Docker Compose
- Gradle (Optional)

## Building the project
This project uses Gradle as a build tool. You can use any local gradle installation and run the following command to build it.

```
gradle build
```

If you don't have a local gradle installation, you can execute the following command to build the project.

### Windows
```
# gradlew.bat build
```

### MacOS/Linux
```
# ./gradlew build
```

## Setting webhook environment variable.
This application sends requests to the webhook.site URL. In order to setup the correct URL to test the asyncronous call, perform the following steps:
 1. In a browser, navigate to the following URL: https://webhook.site
 2. There will be a unique URL the site mentions to copy, click on the link to "Copy to Clipboard". Leave the browser tab open to verify POST endpoint later.
 3. In your environment, set the variable webhook_url=<URL copied>

Example:

```
# set webhook_url=https://webhook.site/a06abf62-866b-4a47-bd63-195119554b83
```

## Building the docker image.
The docker image can be built by executing the following command:

```
# docker build --build-arg JAR_FILE=build/libs/*.jar -t musicdemo .
```

## Using docker compose
There is a docker-compose.yml file located at the root level that will load the images for mongodb as well as the built docker image for the application and will start it up in the current shell.
Once both have started up, any request to the API will be live. 

```
# docker-compose up
```

## API Endpoints

### GET Endpoint
HTTP GET http://localhost:8080/music/songs/{isrc}

This endpoint will load an existing resource based on the ISRC code as the unique identifier.

#### Response Structure
```
{
	"isrc": <string>,
	"title": <string>,
	"artist": <string>,
	"musicians": 
	[
		{
			"name": <string>,
			"contribution": <string>
		}
	]
}
```
### POST Endpoint
HTTP POST http://localhost:8080/music/songs

This endpoint will save a new song as a record in the database and will return the unique generated ISRC code that can be used as a resource identifier for the other endpoints. Minimally the three alphanumeric character registrant code and the five digit designation code fields are needed to generated the correct ISRC code. It will also send a POST request to the webhook endpoint referenced earlier in this document. The Accept-Header will determine the country code origin with format of ISO 3166-1 alpha-2. If one is not present it will default to US.

#### Request Body
```
{
    "title": <string>,
    "artist": <string>,
    "registrantCode": <string>,
    "designationCode": <string>,

    "musicians":
    [
        {
            "name": <string>,
            "contribution": <string>
        }
    ]
}
```

#### Response Structure
```
{
	"isrc": <string>
}
```

### PUT Endpoint
HTTP PUT http://localhost:8080/music/songs/{isrc}

This endpoint will do a complete update of the resource identifier provided by the ISRC.

#### Request Body
```
{
    "title": <string>,
    "artist": <string>,

    "musicians":
    [
        {
            "name": <string>,
            "contribution": <string>
        }
    ]
}
```

#### Response
```
HTTP 200/OK
```

### Delete Endpoint
HTTP DELETE http://localhost:8080/music/songs/{isrc}

#### Response
```
HTTP 200/OK
```

## Verifying MongoDB Data
You can inspect the table data that is being stored in mongo by going into the mongodb container. You can query the data by doing the following:

```

# docker exec -it mongodb bash    // Exec into the mongo shell
# mongosh                         // Enter mongo CLI
# use music                       // Switch to music db
# db.songs.find()                 // Show all records in the song collection.
```
