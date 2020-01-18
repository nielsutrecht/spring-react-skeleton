#!/bin/bash

mkdir -p build
cd front-end
npm install && yarn build && mv build ../build/front-end