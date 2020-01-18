#!/bin/sh

if [ ! -d "build/front-end" ] || [ ! -d "build/back-end" ]; then
  echo "Missing build output"
  exit 1
fi

docker build -t parallel-builds:1.0 -t parallel-builds:latest .