*Deprecated in favor of [Nuimo emulator for iOS](https://github.com/getsenic/nuimo-emulator-ios) and [Nuimo emulator for Android](https://github.com/getsenic/nuimo-emulator-android)*

Web application to simulate Nuimo controllers over web sockets

#Requirements
Java 8+ JDK

#To compile and run the server execute
Linux/MacOS:
```
./start.sh
```
Windows:
```
gradlew shadowJar
java -jar build/libs/WebSocketNuimo-3.0.0-fat.jar
```
(Easier to handle start script coming soon)

#Build only
```
./gradlew shadowJar
```

#Run only
```
java -jar build/libs/WebSocketNuimo-3.0.0-fat.jar
```
