It is a Java websocket client for playing game of three.

Steps to run the project.
1. mvn package
2. docker build -t dprashanthrgukt/gameofthree-client:latest
3. If you don't want to build the image on your own you can skip above steps and run below command. I have pushed the image to public repository 

   docker run --network="host" -i dprashanthrgukt/gameofthree-client:latest

