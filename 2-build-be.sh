#!/bin/bash

mkdir -p build/back-end
./mvnw clean package

cp back-end/target/*.jar build/back-end/app.jar