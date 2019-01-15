# Parental Control Service

The task for this assignment is to create a parental control service for a video on demand platform.

## Requires
This project requires JDK 8 and gradle (4.4 or later) for building and testing it. 

### Running the Command Line Client
The CLI can be started by typing at terminal:
 
    java -jar build/libs/dheerajkumarsaini-0.0.1-SNAPSHOT.jar "Sample Movie 12" "18"
    
Output would be displayed at the console. e.g.

    You are allowed to watch this movie or
    You are not allowed to watch this movie or
    Error, the preferred level is not present or
    Sorry, the movie couldn't be found.

    
Sample movies configured as part of MovieServiceMockImpl
- Sample Movie U
- Sample Movie PG
- Sample Movie 12
- Sample Movie 15
- Sample Movie 18
- error (To check TechnicalFailureException)
- Any random name (To check TitleNotFoundException)
   
### Running the test

Application tests can be run using the gradle commands

    ./gradlew clean build