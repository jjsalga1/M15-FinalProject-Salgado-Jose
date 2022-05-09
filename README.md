# World Info Tool

This application runs in the command line and takes user input to drive the program.

The user can choose from the following:
- Weather in a city
- ISS location
- Weather in ISS location
- Cryptocurrency price
- Play ASCII video art

Uses the Spring Reactive Web dependency to connect to multiple REST APIs and retrieve JSON responses.

# Tools and technologies

### Created with Spring Initializr
- Spring Reactive Web dependency
### Mono
- For webclient JSON responses
### Image-processing
- Python script using PIL library (https://github.com/jjsalga1/img2ascii)
- FileIO
- Threads to send output to terminal in correct FPS.
### Video-processing
- FFmpeg
- Frame and audio extraction
- Javax Sound
