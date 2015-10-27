#!/bin/bash

FILE=./build/libs/nuimo-websocket-simulator-3.0.0-fat.jar

if [ ! -f $FILE ];
then
  ./gradlew shadowJar
fi

java -jar $FILE
