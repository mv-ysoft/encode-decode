# Encode-Decode
Web application to encode/decode morse code.
Instructions use Windows.
## Build and run using Java
### Build
    gradlew build
### Run
    java -jar build\libs\encode-decode-1.0.jar
### Stop
Press Ctrl+C.
## Build and run using Docker (gradle)
### Build
    gradlew docker
### Run
    gradlew dockerRun
### Stop
    gradlew dockerStop
## Usage
    http://localhost:8080
Port can be changed by standard Spring Boot standard means (when using Java) or changing Docker port mapping (`build.gradle` when using the plugin).