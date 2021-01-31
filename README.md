It is a Java websocket client for playing game of three.

Steps to run the project. Run below commands
1. mvn package
2. docker build -t dprashanthrgukt/gameofthree-client:latest
If you don't want to build the image on your own you can skip above steps and run below command. I have pushed the image to my public repository

3. docker run --network="host" dprashanthrgukt/gameofthree-client:latest

